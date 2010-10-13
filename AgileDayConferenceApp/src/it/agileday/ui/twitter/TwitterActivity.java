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
import it.agileday.utils.BitmapCache;
import it.aglieday.data.Tweet;
import it.aglieday.data.TweetsRepository;

import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Toast;

public class TwitterActivity extends ListActivity implements OnScrollListener {
	private static final String TAG = TwitterActivity.class.getName();
	private TweetsRepository repository;
	private TweetsAdapter adapter;
	private GetTweetsTask task;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.twitter);
		repository = new TweetsRepository(getResources().getString(R.string.hash_tag), new BitmapCache());
		adapter = new TweetsAdapter(this);
		setListAdapter(adapter);
		getListView().setOnScrollListener(this);

		loadTweets();
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		Log.i(TAG, "onScroll(firstVisibleItem=" + firstVisibleItem + ", visibleItemCount=" + visibleItemCount + ", totalItemCount=" + totalItemCount + ")");
		boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount;
		if (loadMore && repository.hasNextPage()) {
			loadTweets();
		}
	}

	private void loadTweets() {
		Log.i(TAG, "loadTweets");
		synchronized (this) {
			if (task == null) {
				task = new GetTweetsTask();
				adapter.addLoadingRow();
				task.execute();
			}
		}
	}

	private Context getContext() {
		return this;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	}

	private class GetTweetsTask extends AsyncTask<Void, Void, List<Tweet>> {
		private Throwable exception = null;

		@Override
		protected List<Tweet> doInBackground(Void... params) {
			try {
				return repository.getNextPage();
			} catch (Exception e) {
				exception = e;
				return null;
			}
		}

		@Override
		protected void onPostExecute(List<Tweet> result) {
			adapter.removeLoadingRow();
			if (exception != null) {
				Log.w(TAG, exception);
				Toast.makeText(getContext(), "Problemi di connessione", Toast.LENGTH_SHORT).show();
				finish();
			} else {
				adapter.addTweets(result);
			}
			task = null;
		}

	}
}
