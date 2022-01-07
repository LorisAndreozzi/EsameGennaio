package parsing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

//import java.text.ParseException;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

public class ParseJsonToJsonObj implements Parse{
	
	// parsing da un file
	public JSONObject fromFile(String nomeFIle)
	{
		return null;
	}
	
	// parsing direttamanete dalla connesione
	public JSONObject fromHttp(HttpURLConnection connesione)
	{
		try {
			
			InputStream in = connesione.getInputStream();
			
			 String contenuto = "";
			 String riga = "";
			 try {
			   InputStreamReader inR = new InputStreamReader( in );
			   BufferedReader buf = new BufferedReader( inR );
			  
			   while ( ( riga = buf.readLine() ) != null ) {
				   contenuto = riga + contenuto;
			   }
			 } finally {
				   in.close();
			 }
			System.out.println( contenuto + "sono qui");
			JSONObject jasonObj = (JSONObject) JSONValue.parseWithException(contenuto);	 //parse JSON Object
			((JSONObject)(jasonObj.get("data"))).get("id");

			System.out.println( "OK" );
			return jasonObj;
			
		} catch ( IOException| ParseException e) {
			e.printStackTrace();
		} catch ( Exception e) {
			e.printStackTrace();
		}
	
		return null;
	}
	
	public void saveOnFile()
	{
		
	}
	
}
