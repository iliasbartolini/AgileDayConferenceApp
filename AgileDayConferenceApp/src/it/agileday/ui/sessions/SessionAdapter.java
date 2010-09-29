package it.agileday.ui.sessions;

import it.agileday.R;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SessionAdapter extends BaseAdapter 
{ 
	private final Context context;
	private final List<Session> sessions;
	
	public SessionAdapter(Context context, List<Session> sessions) 
	{
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
		LayoutInflater layoutInflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View ret = (convertView == null ? layoutInflater.inflate(R.layout.item_session, parent, false) : convertView);
		
		TextView tv = (TextView) ret.findViewById(R.id.title);
		tv.setText(session.title);
		return ret;
	}
}
