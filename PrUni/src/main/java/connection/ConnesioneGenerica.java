package connection;

import java.net.HttpURLConnection;
import java.net.URL;

abstract public class ConnesioneGenerica {

	private URL url;
	private String bearer = "";
	
	public ConnesioneGenerica(URL url)
	{
		Autentification bearer = new Autentification("bearerAutentifacationFile.txt"); 
		this.bearer = bearer.getBearer();
		
		this.url = url;
		
	}
	
	abstract public HttpURLConnection effettuaConnesione();

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public String getBearer() {
		return bearer;
	}

	public void setBearer(String bearer) {
		this.bearer = bearer;
	}
}
