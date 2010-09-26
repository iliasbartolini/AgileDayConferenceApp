package it.agileday.ui;

import it.agileday.R;
import it.aglieday.data.Tweet;
import it.aglieday.data.TweetRepository;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class TwitterActivity extends ListActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_twitter);
		fillData();
	}

	private void fillData() {
		List<Tweet> tweets = new TweetRepository(getResources().getString(R.string.hash_tag)).getTweets();

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_twitter, R.id.text);
		for (Tweet tweet : tweets) {
			adapter.add(formatTweetText(tweet));
		}

		setListAdapter(adapter);
	}

	private String formatTweetText(Tweet tweet) {
		return String.format("%s: %s", tweet.fromUser, tweet.text);
	}
}
