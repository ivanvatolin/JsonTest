package com.van.jsonapp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import com.van.utils.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.van.model.Articles;
import com.van.utils.DownloadTask;
import com.van.utils.ImageLoader;

public class MainActivity extends Activity {

	ArticlesAdapter adapter;
	ArrayList<Articles> articlesList;
	static final int DIALOG_ERROR_CONNECTION = 1;
	private static final int ID_LOAD = 1;
	private static final int ID_PLAY = 3;
	private static final int ID_DELETE = 2;
	private static final int ID_FAVORITE = 4;
	private static final int ID_ORIGINAL = 5;
	Articles seriesSearchResult;
	ArticlesAsyncTask aat;
	public ImageLoader imageLoader;
	private ArrayList imageViews = new ArrayList();
	Articles articles;
	// Context context = this;
	private static final String TAG = MainActivity.class.getSimpleName();
	String articlesFilePath = Constants.ANDROID_DATA_PATH + "contents.json";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionItem nextItem = new ActionItem(ID_LOAD, "Load", getResources()
				.getDrawable(R.drawable.menu_down_arrow));
		ActionItem prevItem = new ActionItem(ID_DELETE, "Delete",
				getResources().getDrawable(R.drawable.menu_up_arrow));
		ActionItem searchItem = new ActionItem(ID_PLAY, "Play", getResources()
				.getDrawable(R.drawable.menu_search));
		ActionItem infoItem = new ActionItem(ID_FAVORITE, "Favorite",
				getResources().getDrawable(R.drawable.menu_info));
		ActionItem eraseItem = new ActionItem(ID_ORIGINAL, "Original",
				getResources().getDrawable(R.drawable.menu_eraser));

		// use setSticky(true) to disable QuickAction dialog being dismissed
		// after an item is clicked
		prevItem.setSticky(true);
		nextItem.setSticky(true);

		// create QuickAction. Use QuickAction.VERTICAL or
		// QuickAction.HORIZONTAL param to define layout
		// orientation
		final QuickAction quickAction = new QuickAction(this,
				QuickAction.VERTICAL);

		// add action items into QuickAction
		quickAction.addActionItem(nextItem);
		quickAction.addActionItem(prevItem);
		quickAction.addActionItem(searchItem);
		quickAction.addActionItem(infoItem);
		quickAction.addActionItem(eraseItem);
		// Set listener for action item clicked
		quickAction
				.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
					@Override
					public void onItemClick(QuickAction source, int pos,
							int actionId) {
						ActionItem actionItem = quickAction.getActionItem(pos);

						// here we can filter which action item was clicked with
						// pos or actionId parameter
						if (actionId == ID_LOAD) {
							Toast.makeText(getApplicationContext(),
									"Let's do some load action",
									Toast.LENGTH_SHORT).show();
						} else if (actionId == ID_PLAY) {
							Toast.makeText(getApplicationContext(),
									"I have play time", Toast.LENGTH_SHORT)
									.show();
						} else {
							Toast.makeText(getApplicationContext(),
									actionItem.getActionId() 
									+ " selected",
									Toast.LENGTH_SHORT).show();
						}
					}
				});

		// set listnener for on dismiss event, this listener will be called only
		// if QuickAction dialog was dismissed
		// by clicking the area outside the dialog.
		quickAction.setOnDismissListener(new QuickAction.OnDismissListener() {
			@Override
			public void onDismiss() {
				Toast.makeText(getApplicationContext(), "Dismissed",
						Toast.LENGTH_SHORT).show();
			}
		});

		setContentView(R.layout.activity_main);
		articlesList = new ArrayList<Articles>();
		// if (isInternetConnectionActive(getBaseContext())) {

		// showDialog(DIALOG_ERROR_CONNECTION);
		aat = (ArticlesAsyncTask) new ArticlesAsyncTask()
				.execute(Constants.PATH_TO_SERVER);

		// if (!isOnline(this)) {
		// toast(MainActivity.this,"No internet connection.");
		// } else {
		//
		// }
		// } else {
		// Toast.makeText(MainActivity.this, "Интернет отсутствует",
		// Toast.LENGTH_SHORT).show();
		// }
		// new
		// ArticlesAsyncTask().execute("http://microblogging.wingnity.com/JSONParsingTutorial/jsonActors");

		ListView list = (ListView) findViewById(R.id.list);
		adapter = new ArticlesAdapter(getApplicationContext(), R.layout.item,
				articlesList);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				quickAction.show(view);
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(),
						articlesList.get(position).getArticlesTitle(),
						Toast.LENGTH_LONG).show();

			}
		});

	}

	public class ArticlesAsyncTask extends AsyncTask<String, Void, Boolean> {

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// if (!checkExistsFile(Constants.ANDROID_DATA_PATH +
			// "contents.json")) {
			dialog = new ProgressDialog(MainActivity.this);
			dialog.setMessage("Loading, please wait");
			dialog.setTitle("Connecting server");
			dialog.show();
			dialog.setCancelable(false);
			// }
		}

		@Override
		protected Boolean doInBackground(String... urls) {

//			Log.d(TAG, "contents: "
//					+ Constants.ANDROID_DATA_PATH
//					+ "contents.json - "
//					+ checkExistsFile(Constants.ANDROID_DATA_PATH
//							+ "contents.json"));
			if (!checkExistsFile(Constants.ANDROID_DATA_PATH + "contents.json")) {
				downloadFile(urls[0]);
			}
			try {
				// HttpGet post = new HttpGet(urls[0]);
				// HttpClient client = new DefaultHttpClient();
				// HttpResponse response = client.execute(post);
				// int status = response.getStatusLine().getStatusCode();

				String data = read(articlesFilePath);

				// if (status == 200) {
				// HttpEntity entity = response.getEntity();
				// String data = EntityUtils.toString(entity);
				// Log.d(TAG, "Android json: " + data);

				JSONObject jsonObject = new JSONObject(data);
				JSONArray jsonArray = jsonObject.getJSONArray("articles");

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonRealObject = jsonArray.getJSONObject(i);
					Articles articles = new Articles();

					articles.setArticlesTitle(jsonRealObject.getString("title"));
					articles.setArticlesLength(jsonRealObject
							.getString("length"));
					articles.setArticlesDateIssue(jsonRealObject
							.getString("date"));
					String imageUrlPath = jsonRealObject.getString("image");
					String imageFileName = Uri.parse(imageUrlPath)
							.getLastPathSegment();
					// String imageFileName =
					// imageUrlPath.substring(imageUrlPath
					// .lastIndexOf('/') + 1);
					String imageFilePath = Constants.ANDROID_DATA_PATH_MEDIA_IMAGES
							+ imageFileName;
					//
					// Log.d(TAG, "Android imagePath: " + imageUrlPath);
					// Log.d(TAG, "Android imageFileName: " + imageFileName);
					// Log.d(TAG, "Android pathImageStore: " + imageStorePath);

					Log.d(TAG, "file: " + imageFilePath + " - "
							+ checkExistsFile(imageFilePath));
					if (!checkExistsFile(imageFilePath)) {
						try {
							Bitmap image = getImageByUrl(imageUrlPath,
									Constants.ANDROID_DATA_PATH_MEDIA_IMAGES);
						} catch (IOException e) {
							Toast.makeText(MainActivity.this,
									R.string.unavailable_download,
									Toast.LENGTH_SHORT).show();
						}
					}
					articles.setArticlesImageUrl(BitmapFactory
							.decodeFile(imageFilePath));

					articlesList.add(articles);
				}

				return true;

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// } catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return false;

		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			dialog.cancel();
			adapter.notifyDataSetChanged();
			if (result == false) {
				Toast.makeText(getApplicationContext(),
						"Unable to fetch data from server", Toast.LENGTH_LONG)
						.show();

			}
		}

		private Bitmap getImageByUrl(String url, String fileName)
				throws IOException, MalformedURLException {
			// Вот так можно получить изображение по url
			Bitmap image = BitmapFactory
					.decodeStream((InputStream) new URL(url).getContent());
			File directory = new File(fileName);
			// Log.d(TAG, directory.toString());
			directory.mkdirs();
			File file = new File(directory,
					url.substring(url.lastIndexOf('/') + 1));
			// Log.d(TAG, file.toString());
			FileOutputStream fOut;
			try {
				fOut = new FileOutputStream(file);

				image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
				fOut.flush();
				fOut.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return image;
		}

	}

	public static boolean createDirIfNotExists(String path) {
		boolean ret = true;

		File file = new File(Environment.getExternalStorageDirectory()
				+ "/JsonApp/data/images/", path);
		if (!file.exists()) {
			if (!file.mkdirs()) {
				Log.d(TAG, "Problem creating Image folder");
				ret = false;
			}
		}
		return ret;
	}

	public static boolean checkExistsFile(String filePath) {
		boolean ret = false;

		File file = new File(filePath);
		if (file.exists()) {
			ret = true;
		}
		return ret;
	}

	private boolean isInternetConnectionActive(Context context) {
		NetworkInfo networkInfo = ((ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE))
				.getActiveNetworkInfo();

		if (networkInfo == null || !networkInfo.isConnected()) {
			return false;
		}
		return true;
	}

	public static String read(String fileName) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new FileReader(new File(
					fileName).getAbsoluteFile()));
			String s;
			while ((s = in.readLine()) != null) {
				sb.append(s);
				sb.append("\n");
			}
			in.close();
		} catch (IOException e) {
		}
		return sb.toString();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch (id) {
		case DIALOG_ERROR_CONNECTION:
			AlertDialog.Builder errorDialog = new AlertDialog.Builder(this);
			errorDialog.setTitle("Error");
			errorDialog.setMessage("No internet connection.");
			errorDialog.setNeutralButton("OK",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int id) {
							dialog.dismiss();
						}
					});

			AlertDialog errorAlert = errorDialog.create();
			return errorAlert;

		default:
			break;
		}
		return dialog;
	}

	public boolean isOnline(Context c) {
		ConnectivityManager cm = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();

		if (ni != null && ni.isConnected())
			return true;
		else
			return false;
	}

	public void toast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	public void downloadFile(String urls) {
		int count;
		try {
			URL url = new URL(urls);
			URLConnection conexion = url.openConnection();
			conexion.connect();
			InputStream input = new BufferedInputStream(url.openStream());
			OutputStream output = new FileOutputStream(articlesFilePath);
			byte data[] = new byte[2048];

			long total = 0;

			while ((count = input.read(data)) != -1) {
				total += count;
				output.write(data, 0, count);
			}

			output.flush();
			output.close();
			input.close();
		} catch (Exception e) {
			Log.d(TAG, e.getMessage().toString());
			Toast.makeText(MainActivity.this, "Не возможно загрузить файл",
					Toast.LENGTH_SHORT).show();
		}
		// Bitmap file_contents = getImageByUrl(urls[0],
		// "/jsonapp/data/");
	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this).setTitle("Выйти из приложения?")
				.setMessage("Вы действительно хотите выйти?")
				.setNegativeButton(android.R.string.no, null)
				.setPositiveButton(android.R.string.yes, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						MainActivity.super.onBackPressed();
					}
				}).create().show();
	}
}
