package it.agileday.ui.twitter;

import it.agileday.R;
import it.aglieday.data.Tweet;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

class TweetsAdapter extends BaseAdapter {
	private final List<Tweet> tweets;
	private final Context context;

	public TweetsAdapter(Context context, List<Tweet> tweets) {
		this.context = context;
		this.tweets = tweets;
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
		TweetViewGroup ret = (TweetViewGroup) (convertView == null ? getLayoutInflater().inflate(R.layout.item_twitter, parent, false) : convertView);
		ret.setText(tweet.text);
		ret.setImageDrawable(tweet.profileImage);
		return ret;
	}

}