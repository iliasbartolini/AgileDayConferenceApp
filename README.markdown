AgileDay Conference Android Application 
=======================================

This is a conference client created for Italian Agile Day conference.
The aim is to create a conference client application that will help the 
conference visitors by giving uptodate information about sessions, 
and facilitate communication with other conference participants.

### Release build instructions to build .apk package for Android Market:

* copy italianagileday.keystore in AgileDayConferenceApp/ (you should have it)
* android update project --path AgileDayConferenceApp

### Build process:

* Run tests (and check that they are all green :P)
* Update AndroidManifest.xml
  * Increase android:versionCode
  * Increase android:versionName
  * Remove android:debuggable
  * Add android:installLocation="auto" in "manifest" element (http://developer.android.com/guide/appendix/install-location.html#Compatiblity)
* Update build target build.properties adding target=android-8 (note: tests and build must run successfully also on the default supported target-4) 
* ant -buildfile AgileDayConferenceApp\build.xml release
* enter passwords (you should have it)

### After build steps

* In AndroidManifest.xml, REMOVE android:installLocation="auto" in "manifest" element
* Revert build.properties file
* Commit updated AndroidManifest.xml
