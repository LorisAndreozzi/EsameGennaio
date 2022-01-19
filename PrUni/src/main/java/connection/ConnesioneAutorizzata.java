package connection;

import java.net.HttpURLConnection;
import java.net.URL;

	// Questo tipo di connessione al server del twiter non necessita di alcuna chiave o tocken di accesso in quanto riconosciuta direttamente dalla struttura del url

public class ConnesioneAutorizzata extends ConnesioneGenerica {

	public ConnesioneAutorizzata(URL url)
	{
		super(url);		
	}
		
	// richiesta tramite header al server del twitter
	public HttpURLConnection effettuaConnesione()
	{
		//String data = "";
		try 
		{
			// in questo caso nel header viene usato esclusivamente setRequestProperty il quale garantisce una risposta sotto forma json(questa richiesta non è neccessaria nel nostro caso in quanto twitter restituisce di base il JSONObject,su altri siti potrebbe non essere cosi ) 
			HttpURLConnection http = (HttpURLConnection)(super.getUrl()).openConnection();
			http.setRequestProperty("Accept", "application/json");
			//http.setRequestProperty("Authorization", super.getBearer());
						
			System.out.println(http.getResponseCode() + " " + http.getResponseMessage());// controllo personale
			return http; // restituisco una connessione HTTP (verra successivamente usata per aprire lo streaming in lettura)
			
		}catch(Exception errore)
		{
			System.out.println("Errore di connesione dopo autentificazione");// promemoria personale
			errore.printStackTrace();
		}
		
		return null;
	}
}
