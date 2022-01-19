package connection;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

public class Autentification {

	private String bearer = "";
	private String apiKey = "";
	private String apiKeySecret = "";
	
	// Costruttore (avviene attraverso la lettura di un file json contenente informazioni riguardanti le chiavi di accesso)
	public Autentification(String nomeFile) 
	{
		String jsonAutentification = "";
		
		try 
		{
			InputStream in = new FileInputStream(nomeFile);
			
			try 
			{
				InputStreamReader letturaFile = new InputStreamReader(in);
				BufferedReader letturaBufferizzata = new BufferedReader( letturaFile );
			
				String riga = "";
			
				while ( ( riga = letturaBufferizzata.readLine() ) != null ) 
				{
					jsonAutentification += riga;
				}
				
				JSONObject jasonObjAutentification = (JSONObject) JSONValue.parseWithException(jsonAutentification);
								
			this.bearer = ""+jasonObjAutentification.get("BearerToken");
			this.apiKey = ""+jasonObjAutentification.get("APIKey");
			this.apiKeySecret = ""+jasonObjAutentification.get("APIKeySecret");
			
			}finally
			{
				in.close();
			}
						
		}catch(IOException | ParseException errore) 
		{
			System.out.println(errore);
			System.out.println("Errore di autentificazione.");
		}catch(Exception e) 
		{
			System.out.println(e);
		}
		
	}

	public String getAPIKey() {
		return apiKey;
	}

	public String getAPIKeySecret() {
		return apiKeySecret;
	}

	public String getBearer() {
		return bearer;
	}	
	
}
