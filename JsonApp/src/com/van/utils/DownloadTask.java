package com.van.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import com.van.jsonapp.MainActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class DownloadTask extends AsyncTask<String, Integer, Void> {

	// private static final String PEFERENCE_FILE = "preference";
	// private static final String ISDOWNLOADED = "isdownloaded";
//	SharedPreferences settings;
//	SharedPreferences.Editor editor;
	Context context;
	static String TAG = DownloadTask.class.getSimpleName();

	public DownloadTask(Context context) {
		// TODO Auto-generated constructor stub
	}

	ProgressDialog dialog;

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog = new ProgressDialog(context);
		dialog.setMessage("Loading Images, please wait");
		dialog.setTitle("Connecting server");
		dialog.show();
		dialog.setCancelable(false);
	}

	@Override
	protected Void doInBackground(String... aurl) {
		int count;

		try {

			URL url = new URL(aurl[0]);
			URLConnection conection = url.openConnection();
			conection.connect();

			boolean path_check = createDirIfNotExists("/JsonApp/data/images");
			if (path_check) {
				int lenghtOfFile = conection.getContentLength();
				Log.d(TAG, "Lenght of file: " + lenghtOfFile);
				String path = Environment.getExternalStorageDirectory()
						+ "/JsonApp/data/images/"
						+ aurl[0].substring(aurl[0].lastIndexOf('/') + 1);
				Log.d(TAG, path);
				// wait(5000);
				InputStream input = new BufferedInputStream(url.openStream());
				Log.d(TAG, input.toString());
				// FileOutputStream output = new FileOutputStream(path);
				OutputStream output = new FileOutputStream(path);
				byte data[] = new byte[2048];

				long total = 0;

				while ((count = input.read(data)) != -1) {
					total += count;
					// publishProgress(""+(int)((total*100)/lenghtOfFile));
					Log.d("%Percentage%", ""
							+ (int) ((total * 100) / lenghtOfFile));
					onProgressUpdate((int) ((total * 100) / lenghtOfFile));
					output.write(data, 0, count);
				}

				output.flush();
				output.close();
				input.close();
			} else {
				Log.d(TAG, "Error");
			}
		} catch (Exception e) {
			Log.d("%Percentage%", e.getMessage().toString());
		}

		return null;
	}

	protected void onPostExecute(Boolean result) {
		// super.onPostExecute(result);
		dialog.cancel();
	}

	public static boolean createDirIfNotExists(String path) {
		boolean ret = true;

		File file = new File(Environment.getExternalStorageDirectory(), path);
		if (!file.exists()) {
			if (!file.mkdirs()) {
				Log.d(TAG, "Problem creating Image folder");
				ret = false;
			}
		}
		return ret;
	}
}
