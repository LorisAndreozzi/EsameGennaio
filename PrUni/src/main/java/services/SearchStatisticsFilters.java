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

	// COSTRUTTORE
	public SearchStatisticsFilters(JSONObject jsonSearch)
	{
		// si prende in input un JSONObject grezzo,viene istanziato un vettore di tweet
		contenutoSearch = new Vector<Tweet>();
		System.out.println("Inizio costruttore Statistiche");
		// dal json viene estratto l'array contenente i tweet
		JSONArray contenutoJson = (JSONArray) jsonSearch.get("statuses");
		System.out.println("Json array estratto");

		for( Object  elemento : contenutoJson )
		{
			if ( elemento instanceof JSONObject ) {// per utilizzare un forEach,gli elementi dell'array vengono visti come oggetti generici quindi è necessario usare un instanceof
				JSONObject elementoJ = (JSONObject)elemento;

				// promemoria personali sul terminale
				System.out.println("num like :  "+(Long)elementoJ.get("favorite_count"));
				System.out.println("num retweet :  "+(Long)elementoJ.get("retweet_count"));
				System.out.println("id :  "+""+elementoJ.get("id"));
				System.out.println("text :  "+elementoJ.get("text"));
				System.out.println("id autore :  "+((JSONObject)elementoJ.get("user")).get("id"));

				// qundi si crea l'oggetto tweet tramite il costruttore(il tweet contiene al proprio interno un oggetto metriche publiche)
				PublicMetrics metrichePub = new PublicMetrics((Long)elementoJ.get("favorite_count"),(Long)elementoJ.get("retweet_count"));  		    	
				Tweet tweet = new Tweet(""+elementoJ.get("id"),""+elementoJ.get("text"),metrichePub,""+((JSONObject)elementoJ.get("user")).get("id"));

				// promemoria personali sul terminale
				System.out.println(tweet.objToJsonObj());
				System.out.println("Prima dell'inserimento nel vettore");

				// il tweet viene inserito nel vettore
				contenutoSearch.add(tweet);
			}


		}


	}

	// grazie al vettore ottenuto si costruisce un array con delle informazioni che ci interessano, "eliminando tutto il resto"
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

	// filtro per metrica e quantità(la quale può essere maggiore o minore di una certa quantità)  
	public JSONObject filterQuantity(String operator,Long quantity,String nameParam)
	{
		int numElem = 0,numMag = 0,numMin = 0;

			// dal vettore che abbiamo a nostra disposizione creaiamo un JSONArray con il quale è piu comodo lavorare()  
		JSONArray JArrayTemp = (JSONArray) vectorToJson().get("arrayInfo");
		JSONArray arrayFiltrato = new JSONArray();

			// prende gli elementi che rispettano il filtro e li mette in un'altro array 
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

		// Calcolo efficenza filtro
		System.out.println("array finale");
		System.out.println("numero elementi : "+numElem +" numero numMag : "+numMag+" numero numMin : "+numMin);
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

		return jObjRitorno; // il JSONObject restituito contiene l'array filtrato + un JSONObject contenente la percentuale dell'efficenza del filtro

		//}


	}
	// cerca l'elemento maggiore o minore a seconda della richiesta del parametro(like o retweet in questo caso,le altre publiche metriche non sono contenute all'interno di tweetSearch)
	public JSONObject findMinMax(String minMax,String nameParam)
	{
		JSONArray JArrayTemp = (JSONArray) vectorToJson().get("arrayInfo");
				
		// prende l'elemento 1 dell'array e lo compara con gli altri elementi,se rispetta la condizione,il nuovo elemento prende il posto del vecchio
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

		return elemM; // viene restituito JSONObject contenete l'elemento desiderato


	}

}
