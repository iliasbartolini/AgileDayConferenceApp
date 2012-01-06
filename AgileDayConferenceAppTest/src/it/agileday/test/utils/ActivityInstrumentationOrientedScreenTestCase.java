/*
	Copyright 2010 
	
	Author: Ilias Bartolini

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */

package it.agileday.test.utils;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.test.ActivityInstrumentationTestCase2;

public class ActivityInstrumentationOrientedScreenTestCase<T extends Activity> extends ActivityInstrumentationTestCase2<T> {

	private Configuration oldResourcesConfiguration;
	private final int configurationOrientation;

	public ActivityInstrumentationOrientedScreenTestCase(String name, Class<T> type, int configurationOrientation) {
		super(name, type);
		this.configurationOrientation = configurationOrientation;
	}

	@Override
	protected void setUp() throws Exception {
		setupOrientation();
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		restoreOrientation();
	}

	private void setupOrientation() {
		Resources res = getInstrumentation().getTargetContext().getResources();
		oldResourcesConfiguration = res.getConfiguration();
		Configuration newConfiguration = new Configuration(oldResourcesConfiguration);
		newConfiguration.orientation = configurationOrientation;
		res.updateConfiguration(newConfiguration, res.getDisplayMetrics());
	}

	private void restoreOrientation() {
		Resources res = getInstrumentation().getTargetContext().getResources();
		res.updateConfiguration(oldResourcesConfiguration, res.getDisplayMetrics());
	}

}
