AgileDay Conference Android Application 
=======================================

This is a conference client created for Italian Agile Day conference.
The aim is to create a conference client application that will help the 
conference visitors by giving uptodate information about sessions, 
and facilitate communication with other conference participants.


### Build process:

* android update project --path AgileDayConferenceApp
* ant -buildfile AgileDayConferenceApp\build.xml release

### Release build instructions to build signed .apk package for Android Market:

* Run tests and check that they are all green ...run the test suite manually into eclipse :(
* Copy italianagileday.keystore in AgileDayConferenceApp/ (you should have it)
* Update build.properties file
  * uncommenting "key.store" and "key.alias"
  * adding target=android-8 (note: tests and build must run successfully also on the minimum 
supported target-4) 
* Update AndroidManifest.xml
  * Increase android:versionCode
  * Increase android:versionName
  * Remove android:debuggable
  * Add android:installLocation="auto" in "manifest" element (http://developer.android.com/guide/appendix/install-location.html#Compatiblity)
* android update project --path AgileDayConferenceApp
* ant -buildfile AgileDayConferenceApp\build.xml release
  * will ask you for a password (and you should have it)
* smoke test the application on your mobile
* upload the .apk file on the market

### After Release build steps

* Revert build.properties file
* In AndroidManifest.xml, REMOVE android:installLocation="auto" in "manifest" element
* Commit updated AndroidManifest.xml (the only changes should be in android:versionCode and android:versionName)
