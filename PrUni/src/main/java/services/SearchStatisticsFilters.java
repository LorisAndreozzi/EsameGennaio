package services;

import java.util.ArrayList;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

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
			for(Tweet elemento : contenutoSearch)
			{
				temp.add(elemento.objToJsonObj());
			}
			JSONObject jObjRitorno = new JSONObject();

			System.out.println("array finale");
			jObjRitorno.put("arrayInfo", temp);
			return jObjRitorno;

		}

		return null;
	}

	// filtro per metrica e quantita 
	public JSONObject filterQuantity(String operator,Long quantity,String nameParam)
	{
		int numElem = 0,numMag = 0,numMin = 0;

		JSONArray JArrayTemp = (JSONArray) vectorToJson().get("arrayInfo");
		JSONArray arrayFiltrato = new JSONArray();

		// prende gli elementi che non rispettano le condizioni e li elimina dal JSONarray
		for(Object elemento: JArrayTemp )
		{					
			numElem++;
			System.out.println("numero elementi : "+numElem);
			System.out.println(elemento);

			if(operator.equals("moreThan"))
			{
				if((Long)(((JSONObject)(((JSONObject)elemento).get("metriche"))).get(nameParam)) >= quantity )
				{
					arrayFiltrato.add(elemento);
					numMag++;
					System.out.println((Long)(((JSONObject)(((JSONObject)elemento).get("metriche"))).get(nameParam)));
					System.out.println("numero numMag : "+numMag);
				}						
			}

			else if(operator.equals("lessThan"))
			{
				if((Long)(((JSONObject)(((JSONObject)elemento).get("metriche"))).get(nameParam)) <= quantity )
				{
					arrayFiltrato.add(elemento);
					numMin++;

					System.out.println("numero numMin : "+numMag);
				}
			}
		}

		JSONObject jObjRitorno = new JSONObject();
		JSONObject statistics = new JSONObject();

		System.out.println("array finale");
		System.out.println("numero elementi : "+numElem +" numero numMag : "+numMag+" numero numMin : "+numMag);
		jObjRitorno.put("arrayInfo", arrayFiltrato);
		if(numMag != 0 && numElem != numMag )
		{
			double percentuale = ( numMag /(double)numElem )*100 ;
			statistics.put("filtrPercent", percentuale);
			statistics.put("description", percentuale + "% of tweets respect the filters");
		}
		else if(numMin != 0 && numElem != numMin)
		{
			double percentuale = ( numMin /(double)numElem )*100 ;
			statistics.put("filtrPercent", percentuale);
			statistics.put("description", percentuale + "% of tweets respect the filters");
		}	
		else if(numMin == numElem || numMag == numElem )
		{
			statistics.put("filtrPercent", 100);
			statistics.put("description", 100 + "% of tweets respect the filters");
		}
		else if(numMin == 0 && numMag == 0 )
		{
			statistics.put("filtrPercent", 0);
			statistics.put("description", 0 + "% of tweets respect the filters");
		}

		jObjRitorno.put("statistics", statistics);

		return jObjRitorno;

		//}


	}

	public JSONObject findMinMax(String minMax,String nameParam)
	{
		JSONArray JArrayTemp = (JSONArray) vectorToJson().get("arrayInfo");
				
		// cerca l'elemento maggiore o minore a seconda della richiesta
		JSONObject elemM = (JSONObject) JArrayTemp.get(0);
		System.out.println("ELEMENTO elemM :  "+elemM);
		
		for(int i = 0; i < JArrayTemp.size() ; i++ )
		{
			if(minMax.equals("min"))
			{
				if((Long)(((JSONObject)(((JSONObject) JArrayTemp.get(i)).get("metriche"))).get(nameParam))<=(Long)(((JSONObject)(elemM.get("metriche"))).get(nameParam)))
					{elemM = (JSONObject)JArrayTemp.get(i);
					System.out.println("ELEMENTO elemM :  "+elemM);}
			}

			else
			{
				if((Long)(((JSONObject)(((JSONObject) JArrayTemp.get(i)).get("metriche"))).get(nameParam))>=(Long)(((JSONObject)(elemM.get("metriche"))).get(nameParam)))
					{elemM = (JSONObject)JArrayTemp.get(i);
					System.out.println("ELEMENTO elemM :  "+elemM);}
			}
		}

		//

		return elemM;


	}

}
