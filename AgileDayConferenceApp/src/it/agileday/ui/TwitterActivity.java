package it.agileday.ui;

import it.agileday.R;
import it.aglieday.data.Tweet;
import it.aglieday.data.TweetRepository;
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
		Iterable<Tweet> tweets = new TweetRepository().getTweets();
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_twitter);
		for (Tweet tweet : tweets) {
			adapter.add(tweet.text);
		}

		setListAdapter(adapter);
	}

}
