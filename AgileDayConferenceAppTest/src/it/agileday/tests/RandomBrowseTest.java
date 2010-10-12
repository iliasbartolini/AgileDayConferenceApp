package it.agileday.tests;


/*
 * This is an example test project created in Eclipse to test NotePad which is a sample 
 * project located in AndroidSDK/platforms/android-2.1/samples/NotePad
 * Just click on File --> New --> Project --> Android Project --> Create Project from existing source
 * 
 * Then you can run these test cases either on the emulator or on device. You right click
 * the test project and select Run As --> Run As Android JUnit Test
 * 
 * @author Renas Reda, renas.reda@jayway.com
 * 
 */


import it.agileday.ui.ConferenceApp;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Smoke;
import android.widget.Button;

import com.jayway.android.robotium.solo.Solo;


public class RandomBrowseTest extends ActivityInstrumentationTestCase2<ConferenceApp>{
	
	private Solo solo;

	public RandomBrowseTest() {
		super("it.agileday.ui", ConferenceApp.class);
		
	}
	
	 public void setUp() throws Exception {
		 solo = new Solo(getInstrumentation(), getActivity());
		  }
	 
	 @Smoke
	 public void testClickOnSessions() throws Exception {
		 Button button = solo.getButton(0);
		 assertEquals(button.getId(), it.agileday.R.id.button_sessions);
		 
		 // ora qui bisogna provare a cliccare e fare un po' di assert
		 
		 
		 
//		 solo.clickOnMenuItem("Add note");
//		 solo.assertCurrentActivity("Expected NoteEditor activity", "NoteEditor"); //Assert that NoteEditor activity is opened
//		 solo.enterText(0, "Note 1"); //In text field 0, add Note 1
//		 solo.goBack(); //Go back 
//		 solo.clickOnMenuItem("Add note"); //Clicks on menu item 
//		 solo.enterText(0, "Note 2"); //In text field 0, add Note 2
//		 solo.goBackToActivity("NotesList"); //Go back to first activity named "NotesList"
//		 boolean expected = true;
//		 boolean actual = solo.searchText("Note 1") && solo.searchText("Note 2");
//		 assertEquals("Note 1 and/or Note 2 are not found", expected, actual);
		
	 }
	
	@Smoke 
	public void testNoteChange() throws Exception {
//		solo.clickInList(2); // Clicks on a list line
//		solo.setActivityOrientation(Solo.LANDSCAPE); // Change orientation of activity
//		solo.pressMenuItem(2); // Change title
//		solo.enterText(0, " test"); //In text field 0, add test
//		solo.goBack();
//		solo.goBack();
//		boolean expected = true;
//		boolean actual = solo.searchText("(?i).*?note 1 test"); // (Regexp) case insensitive												// insensitive
//		assertEquals("Note 1 test is not found", expected, actual);

	}
	

	@Smoke
	 public void testNoteRemove() throws Exception {
//		 solo.clickOnText("(?i).*?test.*");   //(Regexp) case insensitive/text that contains "test"
//		 solo.pressMenuItem(1);   //Delete Note 1 test
//		 boolean expected = false;   //Note 1 test & Note 2 should not be found
//		 boolean actual = solo.searchText("Note 1 test");
//		 assertEquals("Note 1 Test is found", expected, actual);  //Assert that Note 1 test is not found
//		 solo.clickLongOnText("Note 2");
//		 solo.clickOnText("(?i).*?Delete.*");  //Clicks on Delete in the context menu
//		 actual = solo.searchText("Note 2");
//		 assertEquals("Note 2 is found", expected, actual);  //Assert that Note 2 is not found
	 }

	@Override
	public void tearDown() throws Exception {
		try {
			solo.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		getActivity().finish();
		super.tearDown();
	} 
}
