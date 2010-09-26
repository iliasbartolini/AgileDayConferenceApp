package it.agileday.ui.twitter;

import it.agileday.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TweetViewGroup extends LinearLayout {

	public TweetViewGroup(Context context) {
		super(context);
	}

	public TweetViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setText(String text) {
		TextView tv = (TextView) findViewById(R.id.text);
		tv.setText(text);
	}

	public void setImageDrawable(Drawable drawable) {
		ImageView tv = (ImageView) findViewById(R.id.image);
		tv.setImageDrawable(drawable);
	}
}
