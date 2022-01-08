package models;

import org.json.simple.JSONObject;

public class PublicMetrics {

    private Long like;
    private Long retweet;
    private Long reply; 
    private Long quote;
    
    // costruttore
	public PublicMetrics(Long retweet, Long reply, Long like, Long quote) {
		super();
		this.retweet = retweet;
		this.reply = reply;
		this.like = like;
		this.quote = quote;
	}
	// costruttore secondario,usato dal momento in cui nel search tweet mancano alcune informazioni dei parametri
	public PublicMetrics(Long retweet, Long like) {
		super();
		this.retweet = retweet;
		this.like = like;
	}


	public Long getRetweet() {
		return retweet;
	}
	public void setRetweet(Long retweet) {
		this.retweet = retweet;
	}
	public Long getReply() {
		return reply;
	}
	public void setReply(Long reply) {
		this.reply = reply;
	}
	public Long getLike() {
		return like;
	}
	public void setLike(Long like) {
		this.like = like;
	}
	public Long getQuote() {
		return quote;
	}
	public void setQuote(Long quote) {
		this.quote = quote;
	}
    
    public JSONObject objToJsonObj() 
    {
    	JSONObject obj = new JSONObject();
    	
        obj.put("like", this.like );
        obj.put("retweet", this.retweet);
        obj.put("reply", this.reply );
        obj.put("quote", this.quote);
    	
    	return obj;
    }
}
