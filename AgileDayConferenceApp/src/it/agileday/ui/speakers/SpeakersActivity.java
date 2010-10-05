package it.agileday.ui.speakers;

import it.agileday.R;
import it.aglieday.data.DatabaseHelper;
import it.aglieday.data.Speaker;
import it.aglieday.data.SpeakerRepository;

import java.util.List;

import org.xml.sax.XMLReader;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Html.TagHandler;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewAnimator;

public class SpeakersActivity extends Activity {
	private static final String TAG = SpeakersActivity.class.getName();
	private ViewAnimator viewAnimator;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.speakers);
		viewAnimator = (ViewAnimator) findViewById(R.id.flipper);

		SQLiteDatabase database = new DatabaseHelper(this).getReadableDatabase();
		SpeakerRepository repo = new SpeakerRepository(database);
		List<Speaker> speakers = repo.getAll();
		for (Speaker speaker : speakers) {
			View view = getLayoutInflater().inflate(R.layout.speakers_item, viewAnimator, false);
			TextView name = (TextView) view.findViewById(R.id.name);
			name.setText(speaker.name);
			TextView bio = (TextView) view.findViewById(R.id.bio);
			setHtmlText(speaker, bio);

			viewAnimator.addView(view);
		}
		database.close();
		viewAnimator.setDisplayedChild(2);
	}

	private void setHtmlText(Speaker speaker, TextView bio) {
		bio.setMovementMethod(LinkMovementMethod.getInstance()); // http://code.google.com/p/android/issues/detail?id=2219
		bio.setClickable(false);
		bio.setFocusable(false);
		bio.setFocusableInTouchMode(false);
		bio.setLongClickable(false);

		bio.setText(Html.fromHtml(speaker.bio, new ImageGetter() {
			@Override
			public Drawable getDrawable(String source) {
				int resourceId = getResources().getIdentifier(source, "drawable", "it.agileday");
				Drawable drawable = getResources().getDrawable(resourceId);
				drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
				return drawable;
			}
		}, null));

//		Resources resources = context.getResources(); 
//		Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + 
//		resources.getResourcePackageName(resId) + '/' + 
//		resources.getResourceTypeName(resId) + '/' + 
//		resources.getResourceEntryName(resId) ); 
	}
}
