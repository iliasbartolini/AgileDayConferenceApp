/*
	Copyright 2010 
	
	Author: Gian Marco Gherardi
	Author: Ilias Bartolini
	Author: Luigi Bozzo

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

package it.agileday.data;

import it.agileday.utils.BitmapCache;
import it.agileday.utils.Dates;
import it.agileday.utils.HttpRestUtil;

import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TweetsRepository {
	@SuppressWarnings("unused")
	private static final String TAG = TweetsRepository.class.getName();
	private static final String URL = "http://search.twitter.com/search.json";

	private String nextPageQueryString;
	private final BitmapCache bitmapCache;

	public TweetsRepository(String hashTag, BitmapCache bitmapCache) {
		this.nextPageQueryString = "result_type=recent&rpp=10&q=" + URLEncoder.encode(hashTag);
		this.bitmapCache = bitmapCache;
	}

	public TweetList getNextPage() {

		if (!hasNextPage())
			return TweetList.Empty();

		JSONObject json = HttpRestUtil.httpGetJsonObject(String.format("%s?%s", URL, nextPageQueryString));

		nextPageQueryString = json.optString("next_page", null);

		try {
			return fromJson(json.getJSONArray("results"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean hasNextPage() {
		return nextPageQueryString != null;
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
			ret.date = Dates.fromTweeterString(json.getString("created_at"));
			ret.profileImage = bitmapCache.get(json.getString("profile_image_url"));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
		return ret;
	}

}
