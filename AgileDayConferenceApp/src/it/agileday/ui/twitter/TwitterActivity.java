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

public class TwitterActivity extends ListActivity {
	private TweetsRepository repository;
	private TweetsAdapter adapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_twitter);
		repository = new TweetsRepository(getResources().getString(R.string.hash_tag), new BitmapCache());
		adapter = new TweetsAdapter(this);
		setListAdapter(adapter);

		new GetTweetsTask().execute();
	}

	private class GetTweetsTask extends AsyncTask<Void, Void, List<Tweet>> {
		@Override
		protected List<Tweet> doInBackground(Void... params) {
			return repository.getTweets();
		}

		@Override
		protected void onPostExecute(List<Tweet> result) {
			adapter.addTweets(result);
			adapter.notifyDataSetChanged();
		}
	}
}
