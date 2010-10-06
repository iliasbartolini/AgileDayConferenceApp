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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

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
		if (loadMore) {
			loadTweets();
		}
	}

	private void loadTweets() {
		synchronized (this) {
			if (task == null) {
				task = new GetTweetsTask();
				task.execute();
			}
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	}

	private class GetTweetsTask extends AsyncTask<Void, Void, List<Tweet>> {
		@Override
		protected List<Tweet> doInBackground(Void... params) {
			Log.i(TAG, "Loading next tweet page");
			return repository.getNextPage();
		}

		@Override
		protected void onPostExecute(List<Tweet> result) {
			adapter.addTweets(result);
			adapter.notifyDataSetChanged();
			task = null;
		}
	}
}
