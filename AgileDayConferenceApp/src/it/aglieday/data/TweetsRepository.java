/*
   Copyright 2010 Ilias Bartolini, Andrea Chiarini, Luigi Bozzo

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package it.aglieday.data;

import it.agileday.utils.BitmapCache;
import it.agileday.utils.HttpRestUtil;

import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TweetsRepository {
	private static final String URL = "http://search.twitter.com/search.json";

	private String nextPageUrl;
	private final BitmapCache bitmapCache;

	public TweetsRepository(String hashTag, BitmapCache bitmapCache) {
		this.nextPageUrl = String.format("%s?q=%s&result_type=recent&rpp=10", URL, URLEncoder.encode(hashTag));
		this.bitmapCache = bitmapCache;
	}

	public TweetList getNextPage() {

		if (!hasNextPage())
			return TweetList.Empty();

		JSONObject json;
		try {
			json = HttpRestUtil.httpGetJsonObject(nextPageUrl);
		} catch (Exception e) {
			throw new DataException(e);
		}

		if (!json.has("next_page")) {
			nextPageUrl = null;
		} else {
			nextPageUrl = URL + json.optString("next_page");
		}

		try {
			return fromJson(json.getJSONArray("results"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean hasNextPage() {
		return nextPageUrl != null;
	}

	private TweetList fromJson(JSONArray ary) throws Exception {
		TweetList tweets = new TweetList(ary.length());
		for (int i = 0; i < ary.length(); i++) {
			tweets.add(buildTweet(ary.getJSONObject(i)));
		}
		return tweets;
	}

	public Tweet buildTweet(JSONObject json) {
		Tweet ret = new Tweet();
		try {
			ret.id = json.getLong("id");
			ret.text = json.getString("text");
			ret.fromUser = json.getString("from_user");
			ret.profileImage = bitmapCache.get(json.getString("profile_image_url"));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
		return ret;
	}

}
