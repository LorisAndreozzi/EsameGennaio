package connection;

import java.net.HttpURLConnection;
import java.net.URL;

// una classe astratta che rappresenta le tipologie di connesioni al server twitter e contiene tutte le chiavi di autorizzazione e l'url della richiesta

abstract public class ConnesioneGenerica {

	private URL url;
	private String bearer = "";
	private String apiKey = "";
	private String apiKeySecret = "";
	
	public ConnesioneGenerica(URL url)
	{
		// l'utentificazione avviene in magnera "automatica" direttatamente nel costruttore l'importante è,avere un file di nome "AutentificationFile.json" contenente le chiavi
		Autentification bearer = new Autentification("AutentificationFile.json"); 
		this.bearer = bearer.getBearer();
		this.apiKey = bearer.getAPIKey();
		this.apiKeySecret = bearer.getAPIKeySecret();
		
		// promemoria personali/controlli
		System.out.println("Bearer :  "+this.bearer);		
		System.out.println("apiKey :  "+this.apiKey);
		System.out.println("apiKeySecret :  "+this.apiKeySecret);
		
		this.url = url;
		
	}
	
	// i vari getters and setters 
	
	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getApiKeySecret() {
		return apiKeySecret;
	}

	public void setApiKeySecret(String apiKeySecret) {
		this.apiKeySecret = apiKeySecret;
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
