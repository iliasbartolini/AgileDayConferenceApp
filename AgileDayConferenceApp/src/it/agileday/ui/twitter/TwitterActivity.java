package it.agileday.ui.twitter;

import it.agileday.R;
import it.aglieday.data.Tweet;
import it.aglieday.data.TweetRepository;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;

public class TwitterActivity extends ListActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_twitter);
		fillData();
	}

	private void fillData() {
		List<Tweet> tweets = new TweetRepository(getResources().getString(R.string.hash_tag)).getTweets();
		TweetsAdapter adapter = new TweetsAdapter(this, tweets);
		setListAdapter(adapter);
	}
}
