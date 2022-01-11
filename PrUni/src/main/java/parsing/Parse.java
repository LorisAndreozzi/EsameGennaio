package parsing;

import java.net.HttpURLConnection;

import org.json.simple.JSONObject;

public interface Parse {

	public JSONObject fromFile(String nomeFIle);
	public JSONObject fromHttp(/*HttpURLConnection connesione*/);
	public void saveOnFile(JSONObject jObj);
	
}
