package models;

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
	
	
}
