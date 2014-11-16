package com.van.jsonapp;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import com.van.model.Articles;
import com.van.utils.DownloadTask;
import com.van.utils.ImageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ArticlesAdapter extends ArrayAdapter<Articles> {

	ArrayList<Articles> articlesList;
	LayoutInflater vi;
	int Resource;
	ViewHolder holder;
	ImageLoader imageLoader;
	static String TAG = ArrayAdapter.class.getSimpleName();

	public ArticlesAdapter(Context context, int resource,
			ArrayList<Articles> objects) {
		super(context, resource, objects);
		vi = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Resource = resource;
		articlesList = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// convert view = design
		View v = convertView;
		if (v == null) {
			holder = new ViewHolder();
			v = vi.inflate(Resource, null);
			holder.ivImage = (ImageView) v.findViewById(R.id.ivUrlImage);
			holder.tvTitle = (TextView) v.findViewById(R.id.tvTitle);
			holder.tvDateIssue = (TextView) v.findViewById(R.id.tvDateIssue);
			holder.tvLength = (TextView) v.findViewById(R.id.tvLength);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		holder.ivImage.setImageResource(R.drawable.ic_launcher_pod);
		// imageLoader.DisplayImage(articlesList.get(position).getArticlesImageUrl(),
		// holder.ivImage);
//		new DownloadImageTask(holder.ivImage).execute(articlesList
//				.get(position).getArticlesImageUrl());
		// new
		// DownloadTask().execute(articlesList.get(position).getArticlesImageUrl());
		holder.tvTitle.setText(articlesList.get(position).getArticlesTitle());
		holder.tvLength.setText(articlesList.get(position).getArticlesLength());
		holder.tvDateIssue.setText(articlesList.get(position)
				.getArticlesDateIssue());
		holder.ivImage.setImageBitmap(articlesList.get(position)
				.getArticlesImageUrl());

		return v;

	}

	static class ViewHolder {
		public ImageView ivImage;
		public TextView tvTitle;
		public TextView tvDateIssue;
		public TextView tvLength;

	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			// String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			// try {
			// InputStream in = new java.net.URL(urldisplay).openStream();
			// mIcon11 = BitmapFactory.decodeStream(in);
			// } catch (Exception e) {
			// Log.e("Error", e.getMessage());
			// e.printStackTrace();
			// }
			// return mIcon11;
			int count;

			try {

				URL url = new URL(urls[0]);
				URLConnection conection = url.openConnection();
				conection.connect();

				boolean path_check = createDirIfNotExists("/JsonApp/data/images");
				if (path_check) {
					int lenghtOfFile = conection.getContentLength();
					Log.d(TAG, "Lenght of file: " + lenghtOfFile);
					String path = Environment.getExternalStorageDirectory()
							.getAbsolutePath()
							+ "/JsonApp/data/images/"
							+ urls[0].substring(urls[0].lastIndexOf('/') + 1);
					Log.d(TAG, path);
					// wait(1000);
					InputStream input = new BufferedInputStream(
							url.openStream());
					// Log.d(TAG, input.toString());
					// FileOutputStream output = new FileOutputStream(path);
					OutputStream output = new FileOutputStream(path);
					byte data[] = new byte[2048];

					long total = 0;

					while ((count = input.read(data)) != -1) {
						total += count;
						// publishProgress(""+(int)((total*100)/lenghtOfFile));
						Log.d("%Percentage%", ""
								+ (int) ((total * 100) / lenghtOfFile));
						output.write(data, 0, count);
					}
					if (total == lenghtOfFile) {
						Log.d(TAG,
								"File "
										+ urls[0].substring(urls[0]
												.lastIndexOf('/') + 1)
										+ " created");
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
			Bitmap image=null;
			try {
				image = BitmapFactory.decodeStream((InputStream)new URL(urls[0]).getContent());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return image;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
		}

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