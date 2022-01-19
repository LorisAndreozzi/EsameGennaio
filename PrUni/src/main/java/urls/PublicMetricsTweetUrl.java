package urls;

import java.net.URL;

public class PublicMetricsTweetUrl extends Url{
 
	private String idTweet = ""; 
	private String tweetFields = "";
	private String expansions = "";
	
	// Costruttore usa la parte iniziale del tweet specifica a questo tipo di chiamata
	public PublicMetricsTweetUrl() 
	{
		super.setUrlBase("https://api.twitter.com/2/tweets/");
	}
	
	// genero un url sommando alla stringa di base i parametri che questo tipo di chiamata può avere
	public URL generaUrl()
	{
		if(idTweet != "")
		{
			try
			{
				URL urlGenerato = new URL(super.getUrlBase() + idTweet + tweetFields + expansions);
				return urlGenerato;
				
			}catch(Exception errore)
			{
				System.out.println(errore);
			}
		}
		return null;
	}

	// prendo la stringa del parametro "ID" se non è vuota la sostituisco con quella del parametro
	public void setIdTweet(String idTweet) {
		
		if(idTweet != "")
			this.idTweet = idTweet + "?";
	}
	
	// prendo la stringa del parametro "Campo metriche" se non è vuota la sostituisco con quella del parametro
	public void setTweetFields(String tweetFields) {
		
		if(tweetFields != "")
			this.tweetFields = "tweet.fields=" + tweetFields + "&";
	}
	
	// prendo la stringa del parametro "espansioni" se non è vuota la sostituisco con quella del parametro
	public void setExpansions(String expansions) {
		
		if(expansions != "")
			this.expansions = "expansions=" + expansions + "&";
	}
	
}
