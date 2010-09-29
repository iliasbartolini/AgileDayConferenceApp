package it.aglieday.data;

import it.agileday.utils.HttpRestUtil;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TweetRepository {

	private final String hashTag;

	public TweetRepository(String hashTag) {
		this.hashTag = hashTag;
	}

	public List<Tweet> getTweets() {
		String uri = String.format("http://search.twitter.com/search.json?result_type=recent&rpp=20&q=%s", URLEncoder.encode(hashTag));
		JSONObject json = HttpRestUtil.httpGetJsonObject(uri);
		JSONArray results;
		try {
			results = json.getJSONArray("results");
			return fromJson(results);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private List<Tweet> fromJson(JSONArray ary) throws Exception {
		List<Tweet> tweets = new ArrayList<Tweet>(ary.length());
		for (int i = 0; i < ary.length(); i++) {
			tweets.add(buildTweet(ary.getJSONObject(i)));
		}
		return tweets;
	}

	public static Tweet buildTweet(JSONObject json) {
		Tweet ret = new Tweet();
		try {
			ret.id = json.getLong("id");
			ret.text = json.getString("text");
			ret.fromUser = json.getString("from_user");
			ret.profileImageUrl = json.getString("profile_image_url");
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
		return ret;
	}
}
