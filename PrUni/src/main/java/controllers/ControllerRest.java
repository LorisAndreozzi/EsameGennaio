package controllers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import connection.ConnesioneAutorizzata;
import connection.ConnesioneBearer;
import mineExceptions.JsonNullException;
import parsing.ParseJsonToJsonObj;
import services.SearchStatisticsFilters;
import urls.PublicMetricsTweetUrl;
import urls.RechentTweetSearchUrl;

@RestController
public class ControllerRest {
	
		// variabili d'appoggio(una volta preso dal server del twitter un JSON "grezzo",esso viene salvato all'interno di queste variabili,per essere utilizzato successivamente)
	JSONObject tweetMetrics;
	JSONObject tweetSearch;
	
		// costruttore usato esclusivamente per i test (viene letto un JSON "grezzo" per poi essere elaborato)
	public ControllerRest() {};//questo costruttore viene usato allavio del programma
	public ControllerRest(String json){ try{JSONObject jasonObj = (JSONObject) JSONValue.parseWithException(json);this.tweetSearch=jasonObj;}catch(ParseException e) {e.printStackTrace();}} // questo per i test
	
		// CHIAMATE
	
		// una chiamata specifica che utilizza un id(tweet) per richiedre delle metriche publiche di quest'ultimo direttamente dai server del twitter
	@GetMapping("/tweetMetrics")
	public JSONObject tweetMetrics(@RequestParam(name = "id" ,defaultValue = "440322224407314432") String id,
			@RequestParam(name = "tweet.fields" ,defaultValue = "public_metrics") String tweetFields,
			@RequestParam(name = "expansions" ,defaultValue = "author_id") String expansions) 
	{
		String data = "";

			// definisco la connesione:
			// fornisco elementi per la creazione dell'url
			PublicMetricsTweetUrl urlMetriche1 = new PublicMetricsTweetUrl(); 
			urlMetriche1.setIdTweet(id);
			urlMetriche1.setTweetFields(tweetFields);
			urlMetriche1.setExpansions(expansions);
			
			// effettuo una connesione tramite il Bearer Token e l'url generato usando gli elementi precedenti 
			ConnesioneBearer connessioneMetriche1 = new ConnesioneBearer(urlMetriche1.generaUrl());// utilizzo un metodo del "PublicMetricsTweetUrl" che genera l'url definitivo che poi viene usato nella connessione
			HttpURLConnection connesioneMetriche1Effettuata = connessioneMetriche1.effettuaConnesione();

			// effettuo parsing dalla connessione http
			ParseJsonToJsonObj parsing = new ParseJsonToJsonObj();
			JSONObject tweetMetrics = parsing.fromHttp(connesioneMetriche1Effettuata);
			
		return tweetMetrics;
	}
	
	
	@GetMapping("/tweetsSearch")
	public JSONObject tweetsSearch(@RequestParam(name = "q" ,defaultValue = "calcio") String q,@RequestParam(name = "count" ,defaultValue = "10") String count,@RequestParam(name = "result_type" ,defaultValue = "popular") String result_type,
			@RequestParam(name = "lang" ,defaultValue = "") String lang,@RequestParam(name = "include_entities" ,defaultValue = "false") String include_entities) 
	{

			
				// definisco la connesione
				// fornisco elementi per la creazione dell'url
			RechentTweetSearchUrl urlRicerca1 = new RechentTweetSearchUrl(); 
			urlRicerca1.setQuerie(q);
			urlRicerca1.setCount(count);
			urlRicerca1.setResult_type(result_type);
			urlRicerca1.setLang(lang);
			urlRicerca1.setInclude_entities(include_entities);
			
				// effettuo una connesione (già autentificata) tramite l'url generato usando gli elementi precedenti 
			ConnesioneAutorizzata connessioneRicerca1 = new ConnesioneAutorizzata(urlRicerca1.generaUrl());
			HttpURLConnection connesioneRicerca1Effettuata = connessioneRicerca1.effettuaConnesione();
			
				// effettuo parsing dalla connessione http
			ParseJsonToJsonObj parsing = new ParseJsonToJsonObj();
			tweetSearch = parsing.fromHttp(connesioneRicerca1Effettuata);
		
			return tweetSearch;

	}
	
	// una chiamata che semplifica il "json" grezzo di partenza ottenuto dalla prima chiamata search,limitando il contenuto del json alle mettriche publiche che ci interessano
	// a causa dell'incompletezza delle informazioni nel json search si può usufruire ,in questo caso, soltanto dei "like" e dei "retweet"
	
	@GetMapping("/prova")
	public JSONObject prova()
	{
		try 
		{
			if(tweetSearch == null || (JSONArray)(tweetSearch.get("statuses"))==null)
			{throw new JsonNullException("Prima di effettuare delle operazioni di filtraggio,bisogna lanciare la chiamata tweetsSearch,fornendo al programma le informazioni necessarie sulle quali lavorare");}
		}catch(JsonNullException exception)	
		{
			JSONObject obj = new JSONObject();
			obj.put("Eccezione",exception.getMessage());
			return obj;
		}
			
		//System.out.println(tweetSearch);
		SearchStatisticsFilters statistiche = new SearchStatisticsFilters(tweetSearch);
		return statistiche.vectorToJson();
	}
	
	// filtro per parametro voluto (moreThan/lessThan)
	@GetMapping("/filtraSearch")
	public JSONObject filtroSerachPer(@RequestParam(name = "operator" ,defaultValue = "moreThan") String operator,@RequestParam(name = "quantity" ,defaultValue = "100") Long quantity,@RequestParam(name = "nameParam" ,defaultValue = "like") String nameParam)
	{
		try // controllo fatto restituisce un JSONObject con il messaggio dell'eccezione riscontrata a causa di un dato inserito non valido oppure a causa di un JSONObject nullo o inadatto al filtraggio
		{
			if(tweetSearch == null || (JSONArray)(tweetSearch.get("statuses"))==null)
			{throw new JsonNullException("Prima di effettuare delle operazioni di filtraggio,bisogna lanciare la chiamata tweetsSearch,fornendo al programma le informazioni necessarie sulle quali lavorare");}
			if((!operator.equals("lessThan") && !operator.equals("moreThan"))||quantity<0||(!nameParam.equals("like") && !nameParam.equals("retweet")))
			{throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Use the only known parameters");}
		
		}catch(JsonNullException|ResponseStatusException exception)	
		{
			JSONObject obj = new JSONObject();
			obj.put("Eccezione",exception.getMessage());
			return obj;
		}
			
		SearchStatisticsFilters statistiche = new SearchStatisticsFilters(tweetSearch);
		return statistiche.filterQuantity( operator, quantity, nameParam);
	}
	
	// cerco maxMin in base all'input,nel json ottenuto da tweetsSearch
	@GetMapping("/trovaMinMax")
	public JSONObject trovaMinMaxFromSerach(@RequestParam(name = "min_Max" ,defaultValue = "Max") String minMax,@RequestParam(name = "nameParam" ,defaultValue = "like") String nameParam)
	{
		try // controllo fatto restituisce un JSONObject con il messaggio dell'eccezione riscontrata a causa di un dato inserito non valido oppure a causa di un JSONObject nullo o inadatto al filtraggio
		{
			if(tweetSearch == null || (JSONArray)(tweetSearch.get("statuses"))==null)
			{throw new JsonNullException("Prima di effettuare delle operazioni di filtraggio,bisogna lanciare la chiamata tweetsSearch,fornendo al programma le informazioni necessarie sulle quali lavorare");}
			if((!minMax.equals("Max") && !minMax.equals("min"))||(!nameParam.equals("like") && !nameParam.equals("retweet")))
			{throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Use the only known parameters");}
		
		}catch(JsonNullException|ResponseStatusException exception)	
		{
			JSONObject obj = new JSONObject();
			obj.put("Eccezione",exception.getMessage());
			return obj;
		}	
			
		SearchStatisticsFilters statistiche = new SearchStatisticsFilters(tweetSearch);
		return statistiche.findMinMax( minMax, nameParam);
	}
	
}
