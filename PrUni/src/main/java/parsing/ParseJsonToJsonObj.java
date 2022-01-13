package parsing;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
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
		String jsonString = "";
		
		try 
		{
			InputStream in = new FileInputStream("AutentificationFile.txt");
			
			try 
			{
				InputStreamReader letturaFile = new InputStreamReader(in);
				BufferedReader letturaBufferizzata = new BufferedReader( letturaFile );
			
				String riga = "";
			
				while ( ( riga = letturaBufferizzata.readLine() ) != null ) 
				{
					jsonString += riga;
				}
									
			}finally
			{
				in.close();
			}
			
			JSONObject jasonObj = (JSONObject) JSONValue.parseWithException(jsonString);
			return jasonObj;
						
		}catch(IOException | ParseException errore) 
		{
			System.out.println(errore);
			System.out.println("Errore di autentificazione.");
		}catch(Exception e) 
		{
			System.out.println(e);
		}
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
			JSONObject jasonObj = (JSONObject) JSONValue.parseWithException(contenuto);	 //parse JSON Object from contenuto
			System.out.println( "OK" );
			saveOnFile(jasonObj);
			
			return jasonObj;
			
		} catch ( IOException| ParseException e) {
			e.printStackTrace();
		} catch ( Exception e) {
			e.printStackTrace();
		}
	
		return null;
	}
	
	public void saveOnFile(JSONObject jasonObj)
	{
        try (FileWriter file = new FileWriter("fileD'appoggio.txt")) {
            file.write(jasonObj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
}
