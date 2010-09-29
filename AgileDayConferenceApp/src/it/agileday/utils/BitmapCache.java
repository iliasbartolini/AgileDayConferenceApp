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
