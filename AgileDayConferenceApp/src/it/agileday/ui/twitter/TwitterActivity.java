package it.agileday.ui.twitter;

import it.agileday.R;
import it.aglieday.data.Tweet;
import it.aglieday.data.TweetRepository;

import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

public class TwitterActivity extends ListActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_twitter);
		new GetTweetsTask(this).execute(new TweetRepository(getResources().getString(R.string.hash_tag)));
	}

	private class GetTweetsTask extends AsyncTask<TweetRepository, Integer, List<Tweet>> {
		private final Context context;

		public GetTweetsTask(Context context) {
			super();
			this.context = context;
		}

		@Override
		protected void onPostExecute(List<Tweet> result) {
			TweetsAdapter adapter = new TweetsAdapter(context, result);
			setListAdapter(adapter);
		}

		@Override
		protected List<Tweet> doInBackground(TweetRepository... params) {
			return params[0].getTweets();
		}
	}
}
