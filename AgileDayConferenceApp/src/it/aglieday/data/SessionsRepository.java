/*
   Copyright 2010 Ilias Bartolini, Andrea Chiarini, Luigi Bozzo

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

package it.aglieday.data;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;

public class SessionsRepository {

	private final String spreadsheetURL;

	public SessionsRepository(String gdocsURL) {
		this.spreadsheetURL = gdocsURL;
	}

	public List<Session> getSessions() {
		//return realSessionsListThatDoesNotWorkYet()
		return dummySessionsList();
	}

	
	private List<Session> realSessionsListThatDoesNotWorkYet() 
	{
		List<Session> mySessions = new ArrayList<Session>();
		HttpURLConnection connection = null;
		
		try {
			if (Thread.interrupted())
				throw new InterruptedException();
	
			//String encodedOriginalText = URLEncoder.encode(originalText, "UTF-8");
			
			URL url = new URL(spreadsheetURL);
			
			connection = (HttpURLConnection) url.openConnection(); 
			connection.setReadTimeout(10000);
			connection.setConnectTimeout(15000);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Authorization", "GoogleLogin auth=COXmrYYB");
			connection.setDoInput(true);
			connection.connect();
			
			if (Thread.interrupted())
				throw new InterruptedException();
			
			InputStream inputStream = connection.getInputStream();
			
			// Read results from the query
			BufferedReader reader = new BufferedReader(	new InputStreamReader(inputStream, "UTF-8" ));
	
	
			String payload = reader.readLine();
			Log.d("SpreadsheetFeed",payload);
		}
		catch(Exception e)
		{
			Log.d("SpreadsheetFeedError",e.toString());
		}
		return mySessions;
	}
	
	private List<Session> dummySessionsList() {
		List<Session> mySessions = new ArrayList<Session>();
		Session session1 = new Session();
		session1.id = 0;
		session1.title = "Gepddddpo";
		mySessions.add(session1);
		Session session2 = new Session();
		session2.id = 1;
		session2.title = "Bart";
		mySessions.add(session2);
		return mySessions;
	}

}
