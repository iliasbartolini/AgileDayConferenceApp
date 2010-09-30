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

package it.agileday.ui.sessions;

import it.agileday.R;
import it.aglieday.data.Session;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SessionAdapter extends BaseAdapter {
	private final Context context;
	private final List<Session> sessions;

	public SessionAdapter(Context context, List<Session> sessions) {
		this.context = context;
		this.sessions = sessions;
	}

	@Override
	public int getCount() {
		return sessions.size();
	}

	@Override
	public Session getItem(int position) {
		return sessions.get(position);
	}

	@Override
	public long getItemId(int position) {
		return sessions.get(position).id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Session session = getItem(position);
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View ret = (convertView == null ? layoutInflater.inflate(
				R.layout.item_session, parent, false) : convertView);

		TextView tv = (TextView) ret.findViewById(R.id.title);
		tv.setText(session.title);
		return ret;
	}
}
