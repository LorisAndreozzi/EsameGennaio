package urls;

import java.net.URL;

public abstract class Url {

	private String urlBase;
	
	public String getUrlBase() {
		return urlBase;
	}

	protected void setUrlBase(String urlBase) {
		this.urlBase = urlBase;
	}
	// il metodo astratto che genererà un URL
	public abstract URL generaUrl();
}
