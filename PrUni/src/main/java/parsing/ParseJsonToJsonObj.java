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
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ParseJsonToJsonObj implements Parse{

	// parsing da un file (è una lettura identica a quella dal HTTP connection con differenza che lo stream proviene da un file)
	public JSONObject fromFile(String nomeFIle)
	{
		String jsonString = "";

		try 
		{
			InputStream in = new FileInputStream(nomeFIle);

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
	public JSONObject fromHttp(HttpURLConnection connessione)
	{
		InputStream in;
		try {
			
			if(!connessione.getResponseMessage().equals("OK"))
			{throw new ResponseStatusException(HttpStatus.BAD_REQUEST);}
			
				in = connessione.getInputStream();// presa in input  direttamente una connesione HTTP apro uno streaming 

			String contenuto = "";
			String riga = "";
			try {
				InputStreamReader inR = new InputStreamReader( in );// leggo lo streaming
				BufferedReader buf = new BufferedReader( inR );// bufferizzo la lettua per ottimizzare la prestazione 

				while ( ( riga = buf.readLine() ) != null ) { // leggo bufferizzando fin quando le informazioni non si esauriscono
					contenuto = riga + contenuto; // salvo tutto su una stringa
				}
			} finally {
				in.close();
			}
			//System.out.println( contenuto + "sono qui");
			JSONObject jasonObj = (JSONObject) JSONValue.parseWithException(contenuto);	 //parse(analizzo) JSON Object from contenuto(stringa) per poi fare il cast esplicito in un JSONObject
			System.out.println( "OK" );
			saveOnFile(jasonObj);// salvo su un file in caso di necessita

			return jasonObj;// restituisco un oggetto JSON

			// gestione eccezzioni possibili  				http.getResponseCode() + " " + http.getResponseMessage()

		} catch(ResponseStatusException e) {
			JSONObject eccezzione = new JSONObject();
			try {
			eccezzione.put("Codice Errore",connessione.getResponseCode());
			eccezzione.put("Messaggio Errore",connessione.getResponseMessage());
			}catch(Exception eInterna)
			{System.out.println(eInterna);}
			return eccezzione;
			
		} catch ( IOException| ParseException e) {
			e.printStackTrace();			
		} catch ( Exception e) {
			System.out.println("sono in eccezione");
			e.printStackTrace();
		}

		return null;
	}

	public void saveOnFile(JSONObject jasonObj)
	{
		try (FileWriter file = new FileWriter("fileD'appoggio.json")) {
			file.write(jasonObj.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
