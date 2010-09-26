package it.aglieday.data;

import org.json.JSONException;
import org.json.JSONObject;

public class Tweet {
	public static Tweet fromJson(JSONObject json) {
		Tweet ret = new Tweet();
		try {
			ret.id = json.getLong("id");
			ret.text = json.getString("text");
			ret.fromUser = json.getString("from_user");
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
		return ret;
	}

	public long id;
	public String text;
	public String fromUser;

}
