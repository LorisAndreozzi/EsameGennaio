package connection;

import java.net.HttpURLConnection;
import java.net.URL;

abstract public class ConnesioneGenerica {

	private URL url;
	private String bearer = "";
	private String apiKey = "";
	private String apiKeySecret = "";
	
	public ConnesioneGenerica(URL url)
	{
		Autentification bearer = new Autentification("bearerAutentifacationFile.txt"); 
		this.bearer = bearer.getBearer();
		this.apiKey = bearer.getAPIKey();
		this.apiKeySecret = bearer.getAPIKeySecret();
		
		System.out.println("Bearer :  "+this.bearer);
		System.out.println("apiKey :  "+this.apiKey);
		System.out.println("apiKeySecret :  "+this.apiKeySecret);
		
		this.url = url;
		
	}
	
	abstract public HttpURLConnection effettuaConnesione();
	
	public URL getUrl() {
		return url;
	}

	protected void setUrl(URL url) {
		this.url = url;
	}

	public String getBearer() {
		return bearer;
	}

	protected void setBearer(String bearer) {
		this.bearer = bearer;
	}
}
