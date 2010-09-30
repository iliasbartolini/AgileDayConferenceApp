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

import it.agileday.utils.HttpRestUtil;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TweetsRepository {

	private final String hashTag;

	public TweetsRepository(String hashTag) {
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
