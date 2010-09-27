package it.agileday.ui.twitter;

import it.agileday.R;
import it.agileday.util.Task;
import it.agileday.util.TaskRunner;
import it.aglieday.data.Tweet;
import it.aglieday.data.TweetRepository;

import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;

public class TwitterActivity extends ListActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_twitter);
		TaskRunner.run(new Task<List<Tweet>>() {
			@Override
			public List<Tweet> run() {
				return new TweetRepository(getResources().getString(R.string.hash_tag)).getTweets();
			}

			@Override
			public void success(List<Tweet> ret) {
				TweetsAdapter adapter = new TweetsAdapter(getContext(), ret);
				setListAdapter(adapter);
			}

			@Override
			public void failure(Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	private Context getContext() {
		return this;
	}
}
