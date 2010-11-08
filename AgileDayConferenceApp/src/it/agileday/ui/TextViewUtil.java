package it.agileday.ui;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class TextViewUtil 
{
	public static void setHtmlText(String htmlString, TextView textView) {
		textView.setMovementMethod(LinkMovementMethod.getInstance()); // http://code.google.com/p/android/issues/detail?id=2219
		textView.setClickable(true);
		textView.setFocusable(false);
		textView.setFocusableInTouchMode(false);
		textView.setLongClickable(false);
		textView.setText(Html.fromHtml(htmlString));
	}
}
