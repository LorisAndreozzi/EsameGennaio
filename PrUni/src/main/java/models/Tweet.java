package models;

import org.json.simple.JSONObject;

public class Tweet {

	private String tweetId;
	private String tweetText;
	private PublicMetrics metriche;
	private String autorId;
		
	public Tweet(String tweetId, String tweetText, PublicMetrics metriche, String autorId) {
		super();
		this.tweetId = tweetId;
		this.tweetText = tweetText;
		this.metriche = metriche;
		this.autorId = autorId;
	}
	
	
	public String getTweetId() {
		return tweetId;
	}
	public void setTweetId(String tweetId) {
		this.tweetId = tweetId;
	}
	public String getTweetText() {
		return tweetText;
	}
	public void setTweetText(String tweetText) {
		this.tweetText = tweetText;
	}
	public PublicMetrics getMetriche() {
		return metriche;
	}
	public void setMetriche(PublicMetrics metriche) {
		this.metriche = metriche;
	}
	public String getAutorId() {
		return autorId;
	}
	public void setAutorId(String autorId) {
		this.autorId = autorId;
	} 
	
    public JSONObject objToJsonObj() 
    {
    	JSONObject obj = new JSONObject();
    	
        obj.put("tweetId", this.tweetId );
        obj.put("tweetText", this.tweetText);
        obj.put("metriche", this.metriche );
        obj.put("autorId", this.autorId);
    	
    	return obj;
    }
}
