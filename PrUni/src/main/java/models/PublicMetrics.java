package models;

public class PublicMetrics {

    private Integer retweet;
    private Integer reply; 
    private Integer like;
    private Integer quote;
    
	public PublicMetrics(Integer retweet, Integer reply, Integer like, Integer quote) {
		super();
		this.retweet = retweet;
		this.reply = reply;
		this.like = like;
		this.quote = quote;
	}

	public PublicMetrics(Integer retweet, Integer like) {
		super();
		this.retweet = retweet;
		this.like = like;
	}


	public Integer getRetweet() {
		return retweet;
	}
	public void setRetweet(Integer retweet) {
		this.retweet = retweet;
	}
	public Integer getReply() {
		return reply;
	}
	public void setReply(Integer reply) {
		this.reply = reply;
	}
	public Integer getLike() {
		return like;
	}
	public void setLike(Integer like) {
		this.like = like;
	}
	public Integer getQuote() {
		return quote;
	}
	public void setQuote(Integer quote) {
		this.quote = quote;
	}
    
    
}
