package it.agileday.ui.sessions;

import it.agileday.ui.speakers.SpeakersActivity;
import android.test.ActivityInstrumentationTestCase2;

public class SpeakersActivityTest extends ActivityInstrumentationTestCase2<SpeakersActivity> {

	private SpeakersActivity speakersActivity;
	
	public SpeakersActivityTest() {
		super("it.agileday.ui.speakers", SpeakersActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		speakersActivity = this.getActivity();
	}

	public void test_a_oncreate_activity() {
		assertNotNull(speakersActivity);
	}

}
