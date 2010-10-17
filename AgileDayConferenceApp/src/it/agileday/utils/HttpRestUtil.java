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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class HttpRestUtil {
	private static final String TAG = HttpRestUtil.class.getName();

	public static JSONObject httpGetJsonObject(String uri) {
		try {
			HttpGet httpGetRequest = new HttpGet(uri);
			httpGetRequest.setHeader("Accept", "application/json");
			httpGetRequest.setHeader("Accept-Encoding", "gzip");

			HttpResponse response = new DefaultHttpClient().execute(httpGetRequest);
			HttpEntity entity = response.getEntity();
			JSONObject ret = null;
			if (entity != null) {
				InputStream stream = entity.getContent();
				try {
					if (HttpRestUtil.isGzipEncoded(response)) {
						stream = new GZIPInputStream(stream);
					}
					String str = HttpRestUtil.convertStreamToString(stream);
					ret = new JSONObject(str);
				} finally {
					stream.close();
				}
			}
			return ret;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean isGzipEncoded(HttpResponse response) {
		Header contentEncoding = response.getFirstHeader("Content-Encoding");
		return (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip"));
	}

	public static Bitmap httpGetBitmap(String url) {
		final HttpClient client = new DefaultHttpClient();
		final HttpGet getRequest = new HttpGet(url);

		try {
			HttpResponse response = client.execute(getRequest);
			final int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				Log.w(TAG, "Error " + statusCode + " while retrieving bitmap from " + url);
				return null;
			}

			final HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream inputStream = null;
				try {
					inputStream = entity.getContent();
					final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
					return bitmap;
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
					entity.consumeContent();
				}
			}
		} catch (Exception e) {
			getRequest.abort();
			Log.w(TAG, "Error while retrieving bitmap from " + url, e);
		}
		return null;
	}

	public static String convertStreamToString(InputStream is) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		return sb.toString();
	}

}
