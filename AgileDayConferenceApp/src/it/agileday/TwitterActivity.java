package it.agileday;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class TwitterActivity extends ListActivity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_twitter);
		fillData();
	}

	private void fillData() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_twitter);
		adapter.add("Tweet 1");
		adapter.add("Tweet 2");
		setListAdapter(adapter);
	}
}
