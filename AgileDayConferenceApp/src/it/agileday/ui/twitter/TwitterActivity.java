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

public class TwitterActivity extends ListActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_twitter);
		new GetTweetsTask(this, new BitmapCache()).execute(new TweetsRepository(getResources().getString(R.string.hash_tag), new BitmapCache()));
	}

	private class GetTweetsTask extends AsyncTask<TweetsRepository, Integer, List<Tweet>> {
		private final Context context;

		public GetTweetsTask(Context context, BitmapCache bitmapCache) {
			this.context = context;
		}

		@Override
		protected void onPostExecute(List<Tweet> result) {
			TweetsAdapter adapter = new TweetsAdapter(context, result);
			setListAdapter(adapter);
		}

		@Override
		protected List<Tweet> doInBackground(TweetsRepository... params) {
			return params[0].getTweets();
		}
	}
}
