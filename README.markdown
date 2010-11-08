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
* ant -buildfile AgileDayConferenceApp\build.xml release
* enter passwords (you should have it)