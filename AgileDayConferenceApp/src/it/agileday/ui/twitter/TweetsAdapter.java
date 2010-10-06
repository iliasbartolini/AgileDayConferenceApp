/*
   Copyright 2010 Gian Marco Gherardi

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

package it.agileday.ui.twitter;

import it.agileday.R;
import it.aglieday.data.Tweet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class TweetsAdapter extends BaseAdapter {
	private final List<Tweet> tweets;
	private final Context context;

	public TweetsAdapter(Context context) {
		this.context = context;
		this.tweets = new ArrayList<Tweet>();
		this.tweets.add(null);
	}

	public void addTweets(Collection<Tweet> tweets) {
		this.tweets.addAll(this.tweets.size() - 1, tweets);
		// this.tweets.addAll(tweets);
	}

	public void addLoading() {
		this.tweets.add(null);
	}

	private LayoutInflater getLayoutInflater() {
		return (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return tweets.size();
	}

	@Override
	public Tweet getItem(int position) {
		return tweets.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Tweet tweet = getItem(position);
		return tweet != null ? getTweetView(tweet, convertView, parent) : getLoadingView(convertView, parent);
	}

	private View getLoadingView(View convertView, ViewGroup parent) {
		View ret = convertView;
		if (ret == null || ret.getId() != R.id.items_loading_twitter) {
			ret = getLayoutInflater().inflate(R.layout.twitter_item_loading, parent, false);
		}
		return ret;
	}

	private View getTweetView(Tweet tweet, View convertView, ViewGroup parent) {
		View ret = convertView;
		if (ret == null || ret.getId() != R.id.item_twitter) {
			ret = getLayoutInflater().inflate(R.layout.twitter_item, parent, false);
		}
		TextView text = (TextView) ret.findViewById(R.id.text);
		text.setText(tweet.text);
		// TODO use Html.fromHtml()
		// TODO use http://developer.android.com/reference/android/text/util/Linkify.html
		ImageView image = (ImageView) ret.findViewById(R.id.image);
		image.setImageBitmap(tweet.profileImage);
		return ret;
	}
}