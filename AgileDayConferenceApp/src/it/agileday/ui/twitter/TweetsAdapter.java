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

package it.agileday.ui.twitter;

import it.agileday2011.R;
import it.agileday.data.Tweet;
import it.agileday.utils.Dates;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.text.util.Linkify;
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
	}

	public void addTweets(Collection<Tweet> tweets) {
		this.tweets.addAll(tweets);
	}

	public void addLoadingRow() {
		this.tweets.add(null);
	}

	public void removeLoadingRow() {
		tweets.remove(null);
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
		if (tweet != null) {
			return getTweetView(tweet, convertView, parent);
		} else {
			return getLoadingView(convertView, parent);
		}
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
		if (ret == null || ret.getId() != R.id.twitter_item) {
			ret = getLayoutInflater().inflate(R.layout.twitter_item, parent, false);
		}
		Date now = Calendar.getInstance().getTime();
		TextView dateText = (TextView) ret.findViewById(R.id.tweet_date);
		dateText.setText(Dates.differenceSmart(now, tweet.date));

		SpannableString ss = new SpannableString(String.format("%s: %s", tweet.fromUser, tweet.text));
		ss.setSpan(new TextAppearanceSpan(context, R.style.TwitterUser), 0, tweet.fromUser.length() + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		TextView text = (TextView) ret.findViewById(R.id.tweet_text);
		text.setText(ss);

		Linkify.addLinks(text, Linkify.WEB_URLS);
		ImageView image = (ImageView) ret.findViewById(R.id.tweet_image);
		image.setImageBitmap(tweet.profileImage);
		return ret;
	}

}