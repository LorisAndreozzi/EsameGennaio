package urls;

import java.net.URL;

public class RechentTweetSearchUrl extends Url{

	private String querieSearch  = "";
	private String count = "";
	private String result_type = "";
	private String include_entities = "";
	private String lang = "";
	
	// Costruttore usa la parte iniziale del url specifica a questo tipo di chiamata
	public RechentTweetSearchUrl()
	{
		super.setUrlBase("https://wd4hfxnxxa.execute-api.us-east-2.amazonaws.com/dev/api/1.1/search/tweets.json?");
	}

	// genero un url sommando alla stringa di base i parametri che questo tipo di chiamata può avere
	public URL generaUrl()
	{
		if(querieSearch != "")
		{
			try
			{
				URL urlGenerato = new URL(super.getUrlBase() + querieSearch + count + result_type + include_entities + lang);
				return urlGenerato;
				
			}catch(Exception errore)
			{
				System.out.println(errore);
			}
		}
		return null;
	}
	
	// prendo la stringa del parametro "qTypeSearch" se non è vuota la sostituisco con quella del parametro
	public void setQuerie(String querieSearch) 
	{
		if(querieSearch != "")
			this.querieSearch =( "q=" + querieSearch + "&" );
	}
	
	// prendo la stringa del parametro "count" se non è vuota la sostituisco con quella del parametro
	public void setCount(String count) 
	{
		if(count != "")
			this.count =( "count=" + count + "&" );
	}
	
	// prendo la stringa del parametro "result_type" se non è vuota la sostituisco con quella del parametro
	public void setResult_type(String result_type) 
	{
		if(result_type != "")
			this.result_type =( "result_type=" + result_type + "&");
	}
	
	// prendo la stringa del parametro "include_entities" se non è vuota la sostituisco con quella del parametro
	public void setInclude_entities(String include_entities) 
	{
		if(include_entities != "")
			this.include_entities =( "include_entities=" + include_entities + "&");
	}
	
	// prendo la stringa del parametro "lang" se non è vuota la sostituisco con quella del parametro
	public void setLang(String lang) 
	{
		if(lang != "")
			this.lang =( "lang=" + lang + "&");
	}
}
