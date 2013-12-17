package com.primerworldapps.iamonmaydan.executors;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class AsyncStream extends AsyncTask<String, Integer, byte[]> {

	@Override
	protected byte[] doInBackground(String... urls) {
		HttpGet request = new HttpGet(urls[0]);

		try {
			HttpResponse response = new DefaultHttpClient().execute(request);
			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode != HttpStatus.SC_OK) {
				Log.w(getClass().getSimpleName(), "Error " + statusCode
						+ " for URL " + urls[0]);
				// return null;//check if neccessary
			}

			return IOUtils.toByteArray(response.getEntity().getContent());
		} catch (Exception e) {
			request.abort();
			Log.w(getClass().getSimpleName(), "Error for URL " + urls[0], e);
		}

		return null;

	}

}
