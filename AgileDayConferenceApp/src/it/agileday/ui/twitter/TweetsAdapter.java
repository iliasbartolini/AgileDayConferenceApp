package it.agileday.ui.twitter;

import it.agileday.R;
import it.aglieday.data.Tweet;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
		View ret = (convertView == null ? getLayoutInflater().inflate(R.layout.item_twitter, parent, false) : convertView);
		setText(ret, tweet.text);
		setImageDrawable(ret, tweet.profileImage);
		return ret;
	}

	public void setText(View view, String text) {
		TextView tv = (TextView) view.findViewById(R.id.text);
		tv.setText(text);
	}

	public void setImageDrawable(View view, Drawable drawable) {
		ImageView tv = (ImageView) view.findViewById(R.id.image);
		tv.setImageDrawable(drawable);
	}
}