package connection;

import java.net.HttpURLConnection;
import java.net.URL;

//Questo tipo di connessione al server del twiter necessita di un tocken di accesso (BEARER)

public class ConnesioneBearer extends ConnesioneGenerica{

	public ConnesioneBearer(URL url)
	{
		super(url);	
	}
	// richiesta tramite header al server del twitter
	public HttpURLConnection effettuaConnesione()
	{
		//String data = "";
		try 
		{
			// in questo caso le header oltre a specificare il tipo di risposta del server viene mandata una chiave/token di accesso(nel nostro caso presa dal file di autentificazione) 
			HttpURLConnection http = (HttpURLConnection)(super.getUrl()).openConnection();
			http.setRequestProperty("Accept", "application/json");
			http.setRequestProperty("Authorization","Bearer " + super.getBearer()); // prendo il BEARER dalla classe astratta Padre(in questo caso contiene tutte le chiavi)
			

			System.out.println(http.getResponseCode() + " " + http.getResponseMessage());// promemoria personale
			System.out.println("connesione bearer dopo");//promemoria
			return http; // restitusce una http connection
	
		}catch(Exception errore)
		{
			System.out.println("Errore di connesione dopo autentificazione");// promemoria personale
			errore.printStackTrace();
		}
		
		return null;
	}
	
}
