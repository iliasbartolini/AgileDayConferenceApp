/*
	Copyright 2010 Italian Agile Day

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

package it.agileday.utils;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.util.Log;

public class BitmapCache {
	private static final String TAG = BitmapCache.class.getName();

	private final Map<String, Bitmap> cache = new HashMap<String, Bitmap>();

	public Bitmap get(String url) {
		if (!cache.containsKey(url)) {
			cache.put(url, HttpRestUtil.httpGetBitmap(url));
			Log.i(TAG, "Bitmap loaded: " + url);
		}
		return cache.get(url);
	}
}
