/*
   Copyright 2010 Ilias Bartolini

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

package it.agileday.ui.sessions;

import java.util.LinkedList;
import java.util.List;

import it.agileday.R;
import android.app.ListActivity;
import android.os.Bundle;

public class SessionActivity extends ListActivity {
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.sessions);
	        
	        List<Session> mySessions = new LinkedList<Session>();
	        Session session1 = new Session();
	        session1.id = 0;
	        session1.title = "Geppo";
	        mySessions.add(session1);
	        Session session2 = new Session();
	        session2.id = 1;
	        session2.title = "Bart";
	        mySessions.add(session2);
	        SessionAdapter adapter = new SessionAdapter(this, mySessions);
			setListAdapter(adapter);
       }
	        

}
