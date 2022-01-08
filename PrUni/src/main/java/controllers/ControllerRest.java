package controllers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import connection.ConnesioneAutorizzata;
import connection.ConnesioneBearer;
import parsing.ParseJsonToJsonObj;
import services.SearchStatisticsFilters;
import urls.PublicMetricsTweetUrl;
import urls.RechentTweetSearchUrl;

@RestController
public class ControllerRest {
	
	// variabili d'appoggio
	JSONObject tweetMetrics;
	JSONObject tweetSearch;
	
	// chiamate
	@GetMapping("/hello")
	
	public String helloWorld(@RequestParam(name = "par" ,defaultValue = "ciao") String par) 
	{
		return "Ciao Mondo " + par;
	}
	
	
	@GetMapping("/tweetMetrics")
	public JSONObject tweetMetrics(@RequestParam(name = "id" ,defaultValue = "440322224407314432") String id,
			@RequestParam(name = "tweet.fields" ,defaultValue = "") String tweetFields,
			@RequestParam(name = "expansions" ,defaultValue = "") String expansions) 
	{
		String data = "";

			// definisco la connesione
			// fornisco elementi per la creazione dell'url
			PublicMetricsTweetUrl urlMetriche1 = new PublicMetricsTweetUrl(); 
			urlMetriche1.setIdTweet(id);
			urlMetriche1.setTweetFields(tweetFields);
			urlMetriche1.setExpansions(expansions);
			
			// effettuo una connesione tramite il Bearer Token e l'url generato usando gli elementi precedenti 
			ConnesioneBearer connessioneMetriche1 = new ConnesioneBearer(urlMetriche1.generaUrl());
			HttpURLConnection connesioneMetriche1Effettuata = connessioneMetriche1.effettuaConnesione();

			// effettuo parsing dalla connessione http
			ParseJsonToJsonObj parsing = new ParseJsonToJsonObj();
			JSONObject tweetMetrics = parsing.fromHttp(connesioneMetriche1Effettuata);
			
		return tweetMetrics;
	}
	
	@GetMapping("/tweetsSearch")
	public JSONObject tweetsSearch(@RequestParam(name = "q" ,defaultValue = "calcio") String q,@RequestParam(name = "count" ,defaultValue = "5") String count,@RequestParam(name = "result_type" ,defaultValue = "popular") String result_type,
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
			
			// effettuo una connesione già autentificata tramite l'url generato usando gli elementi precedenti 
			ConnesioneAutorizzata connessioneRicerca1 = new ConnesioneAutorizzata(urlRicerca1.generaUrl());
			HttpURLConnection connesioneRicerca1Effettuata = connessioneRicerca1.effettuaConnesione();
			
			// effettuo parsing dalla connessione http
			ParseJsonToJsonObj parsing = new ParseJsonToJsonObj();
			tweetSearch = parsing.fromHttp(connesioneRicerca1Effettuata);
		
			return tweetSearch;

	}
	
	@GetMapping("/prova")
	public JSONObject prova()
	{
		System.out.println(tweetSearch);
		SearchStatisticsFilters statistiche = new SearchStatisticsFilters(tweetSearch);
		return statistiche.vectorToJson();
	}
	
	
}
