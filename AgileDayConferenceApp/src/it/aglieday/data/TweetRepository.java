package it.aglieday.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.drawable.Drawable;

public class TweetRepository {

	private final String hashTag;

	public TweetRepository(String hashTag) {
		this.hashTag = hashTag;
	}

	public List<Tweet> getTweets() {
		String uri = String.format("http://search.twitter.com/search.json?result_type=recent&rpp=20&q=%s", URLEncoder.encode(hashTag));
		JSONObject json = httpGetJsonObject(uri);
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
			ret.profileImage = httpGetDrawable(json.getString("profile_image_url"));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
		return ret;
	}

	public static Drawable httpGetDrawable(String uri) {
		try {
			HttpGet request = new HttpGet(uri);
			HttpResponse response = new DefaultHttpClient().execute(request);
			InputStream stream = response.getEntity().getContent();
			return Drawable.createFromStream(stream, "src");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static JSONObject httpGetJsonObject(String uri) {
		try {
			HttpGet httpGetRequest = new HttpGet(uri);
			httpGetRequest.setHeader("Accept", "application/json");
			httpGetRequest.setHeader("Accept-Encoding", "gzip");

			HttpResponse response = new DefaultHttpClient().execute(httpGetRequest);
			HttpEntity entity = response.getEntity();
			JSONObject ret = null;
			if (entity != null) {
				InputStream stream = entity.getContent();
				try {
					if (isGzipEncoded(response)) {
						stream = new GZIPInputStream(stream);
					}
					String str = convertStreamToString(stream);
					ret = new JSONObject(str);
				} finally {
					stream.close();
				}
			}
			return ret;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static boolean isGzipEncoded(HttpResponse response) {
		Header contentEncoding = response.getFirstHeader("Content-Encoding");
		return (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip"));
	}

	private static String convertStreamToString(InputStream is) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		return sb.toString();
	}
}
