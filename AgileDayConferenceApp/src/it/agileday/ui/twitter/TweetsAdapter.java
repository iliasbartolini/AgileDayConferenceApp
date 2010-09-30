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

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
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
		View resultView = (convertView == null ? getLayoutInflater().inflate(R.layout.item_twitter, parent, false) : convertView);
		setText(resultView, tweet.text);
		setImageDrawable(resultView, tweet.profileImage);
		return resultView;
	}

	public void setText(View view, String text) {
		TextView tv = (TextView) view.findViewById(R.id.text);
		tv.setText(text);
	}

	public void setImageDrawable(View view, Bitmap bitmap) {
		ImageView tv = (ImageView) view.findViewById(R.id.image);
		tv.setImageBitmap(bitmap);
	}
}