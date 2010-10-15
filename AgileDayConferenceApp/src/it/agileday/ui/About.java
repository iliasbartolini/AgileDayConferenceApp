package it.agileday.ui;

import it.agileday.R;
import android.app.Activity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.TextView;


public class About extends Activity {
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.about);
	        
	        TextView  licenceText = (TextView) findViewById(R.id.about_license);
			Linkify.addLinks(licenceText, Linkify.WEB_URLS);

			TextView  contentText = (TextView) findViewById(R.id.about_content);
			Linkify.addLinks(contentText, Linkify.WEB_URLS);
	    }
}
