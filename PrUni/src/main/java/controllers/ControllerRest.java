package controllers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerRest {

	String url = "https://wd4hfxnxxa.execute-api.us-east-2.amazonaws.com/dev/api/1.1/search/tweets.json?q=calcio&count=2";
	
	@GetMapping("/hello")
	
	public String helloWorld(@RequestParam(name = "par" ,defaultValue = "ciao") String par) 
	{
		return "Ciao Mondo " + par;
	}
	
	
	@GetMapping("/tweet")
	public String cercaTweet() 
	{
		String data = "";
		try
		{
			URLConnection openConnection = new URL(url).openConnection();
			InputStream in = openConnection.getInputStream();
			
			
			String line = "";
			 
			try {
			   InputStreamReader inR = new InputStreamReader( in );
			   BufferedReader buf = new BufferedReader( inR );
			  
			   while ( ( line = buf.readLine() ) != null ) {
				   data+= line;
			   }
			} finally {
			  in.close();
			}
		
		}catch(Exception errore)
		{
			errore.printStackTrace();
		}
		
		

		return data;
	}
	
	@GetMapping("/tweetSp")
	public String Tweet() 
	{
		String data = "";
		try {
			URL url = new URL("https://api.twitter.com/2/tweets/440322224407314432?expansions=author_id,attachments.media_keys&tweet.fields=public_metrics");
			HttpURLConnection http = (HttpURLConnection)url.openConnection();
			http.setRequestProperty("Accept", "application/json");
			http.setRequestProperty("Authorization", "Bearer AAAAAAAAAAAAAAAAAAAAAJ77WwEAAAAA%2BLJvL%2B8F2%2Fihh40n8YXPnVW7KuY%3DQEkV6ZKmP5OMWYvBBlzyc5U5hAqOBXPKhEKOk4nioQqC7J65Pf");

			InputStream in = http.getInputStream();
			
			String line = "";

		
	
			InputStreamReader inR = new InputStreamReader( in );
			BufferedReader buf = new BufferedReader( inR );
				  
			while ( ( line = buf.readLine() ) != null ) {
				 data+= line;
				 }
		
			System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
			http.disconnect();
			
		}catch(Exception errore)
		{
			errore.printStackTrace();
		}

		return data;
	}
	
}
