package it.aglieday.data;

import org.json.JSONException;
import org.json.JSONObject;

public class Tweet {
	public static Tweet fromJson(JSONObject json) {
		Tweet ret = new Tweet();
		try {
			ret.text = json.getString("text");
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
		return ret;
	}

	public String text;
}
