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
import urls.PublicMetricsTweetUrl;
import urls.RechentTweetSearchUrl;

@RestController
public class ControllerRest {

	String url = "https://wd4hfxnxxa.execute-api.us-east-2.amazonaws.com/dev/api/1.1/search/tweets.json?q=calcio&count=2";
	
	@GetMapping("/hello")
	
	public String helloWorld(@RequestParam(name = "par" ,defaultValue = "ciao") String par) 
	{
		return "Ciao Mondo " + par;
	}
	
	
	@GetMapping("/tweetMetrics")
	public JSONObject cercaTweet(@RequestParam(name = "id" ,defaultValue = "440322224407314432") String id,
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

			ParseJsonToJsonObj parsing = new ParseJsonToJsonObj();
			//parsing.fromHttp(connesioneMetriche1Effettuata);
			JSONObject a = parsing.fromHttp(connesioneMetriche1Effettuata);
			
		return a;
	}
	
	@GetMapping("/tweetsSearch")
	public String Tweet(@RequestParam(name = "q" ,defaultValue = "calcio") String q,@RequestParam(name = "count" ,defaultValue = "5") String count,@RequestParam(name = "result_type" ,defaultValue = "popular") String result_type,
			@RequestParam(name = "lang" ,defaultValue = "") String lang,@RequestParam(name = "include_entities" ,defaultValue = "false") String include_entities) 
	{
		String data = "";
		try
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
			
			// apro uno stream dalla connesione
			InputStream in = connesioneRicerca1Effettuata.getInputStream();			
			String line = "";
	
			// bufferizzo lo stream derivante dalla connesione
			InputStreamReader inR = new InputStreamReader( in );
			BufferedReader buf = new BufferedReader( inR );
				  
			// leggo lo stream riga per riga
			while ( ( line = buf.readLine() ) != null ) {
				 data+= line;
				 }
		
			System.out.println(connesioneRicerca1Effettuata.getResponseCode() + " " + connesioneRicerca1Effettuata.getResponseMessage());
			connesioneRicerca1Effettuata.disconnect();
			
		}catch(Exception errore)
		{
			errore.printStackTrace();
		}

		return data;

	}
	
}
