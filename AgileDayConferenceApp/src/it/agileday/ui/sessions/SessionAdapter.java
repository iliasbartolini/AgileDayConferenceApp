package it.agileday.ui.sessions;

import it.agileday.R;
import it.agileday.utils.Dates;
import it.aglieday.data.Session;
import it.aglieday.data.Track;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SessionAdapter extends BaseAdapter {
	private static final String TIME_FORMAT = "H:mm";
	private static final float DIP_PER_MINUTE = 1.0f;

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
		View ret = buildView(convertView, parent);
		setViewHeight(ret, session);
		setText(ret, R.id.title, session.title);
		setText(ret, R.id.start, Dates.toString(session.getStart(), TIME_FORMAT));
		setText(ret, R.id.end, Dates.toString(session.getEnd(), TIME_FORMAT));
		return ret;
	}

	private void setViewHeight(View view, Session session) {
		float scale = context.getResources().getDisplayMetrics().density;
		int height = (int) (Dates.differenceMinutes(session.getStart(), session.getEnd()) * scale * DIP_PER_MINUTE);
		view.getLayoutParams().height = height;
	}

	private void setText(View view, int id, String text) {
		TextView txt = (TextView) view.findViewById(id);
		txt.setText(text);
	}

	private View buildView(View convertView, ViewGroup parent) {
		if (convertView != null) {
			return convertView;
		}
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		return layoutInflater.inflate(R.layout.sessions_item, parent, false);
	}
}