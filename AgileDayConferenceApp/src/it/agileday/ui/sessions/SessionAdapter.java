package it.agileday.ui.sessions;

import it.agileday.R;
import it.aglieday.data.Session;
import it.aglieday.data.Track;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SessionAdapter extends BaseAdapter {
	private final Context context;
	private final Track track;

	public SessionAdapter(Context context, Track track) {
		super();
		this.context = context;
		this.track = track;
	}

	@Override
	public int getCount() {
		return track.getSessions().size();
	}

	@Override
	public Session getItem(int position) {
		return track.getSessions().get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Session session = getItem(position);
		View ret = getLayoutInflater().inflate(R.layout.sessions_item, parent, false);
		TextView title = (TextView) ret.findViewById(R.id.title);
		title.setText(session.title);
		return ret;
	}

	private LayoutInflater getLayoutInflater() {
		return (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
}