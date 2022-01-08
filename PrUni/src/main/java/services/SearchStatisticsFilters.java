package services;

import java.util.ArrayList;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import models.PublicMetrics;
import models.Tweet;

public class SearchStatisticsFilters {

	private Vector<Tweet> contenutoSearch;
	
	public SearchStatisticsFilters(JSONObject jsonSearch)
	{
		contenutoSearch = new Vector<Tweet>();
		System.out.println("Inizio costruttore Statistiche");
		JSONArray contenutoJson = (JSONArray) jsonSearch.get("statuses");
		System.out.println("Json array estratto");
		
		for( Object  elemento : contenutoJson )
		{
		    if ( elemento instanceof JSONObject ) {
		    	JSONObject elementoJ = (JSONObject)elemento;
			    
		    	System.out.println("num like :  "+(Long)elementoJ.get("favorite_count"));
		    	System.out.println("num retweet :  "+(Long)elementoJ.get("retweet_count"));
		    	System.out.println("id :  "+""+elementoJ.get("id"));
		    	System.out.println("text :  "+elementoJ.get("text"));
		    	System.out.println("id autore :  "+((JSONObject)elementoJ.get("user")).get("id"));
	
		    	
		    	PublicMetrics metrichePub = new PublicMetrics((Long)elementoJ.get("favorite_count"),(Long)elementoJ.get("retweet_count"));  		    	
		    	Tweet tweet = new Tweet(""+elementoJ.get("id"),""+elementoJ.get("text"),metrichePub,""+((JSONObject)elementoJ.get("user")).get("id"));
		    	
		    	System.out.println(tweet.objToJsonObj());
		    	System.out.println("Prima dell'inserimento nel vettore");
		    	
		    	contenutoSearch.add(tweet);
		    }
		    

		}
		
		
	}
	
	public JSONObject vectorToJson()
	{
		if(!contenutoSearch.isEmpty())
		{
			JSONArray temp = new JSONArray();
			for(Tweet elem : contenutoSearch)
			{
				temp.add(elem);
			}
			JSONObject jObjRitorno = new JSONObject();
			
			System.out.println("array finale");
			jObjRitorno.put("arrayInfo", temp);
			return jObjRitorno;
			
		}
		
		return null;
	}
	
}
