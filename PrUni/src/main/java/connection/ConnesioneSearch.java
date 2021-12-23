package connection;

import java.net.HttpURLConnection;
import java.net.URL;

public class ConnesioneSearch extends ConnesioneGenerica{

	public ConnesioneSearch(URL url)
	{
		super(url);
		
	}
	
	public HttpURLConnection effettuaConnesione()
	{
		String data = "";
		try 
		{
			HttpURLConnection http = (HttpURLConnection)(super.getUrl()).openConnection();
			http.setRequestProperty("Accept", "application/json");
			//http.setRequestProperty("Authorization", super.getBearer());
						
			System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
			return http;
			
		}catch(Exception errore)
		{
			System.out.println("Errore di connesione dopo autentificazione");
			errore.printStackTrace();
		}
		
		return null;
	}
}
