package com.van.jsonapp;

import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ActorsAdapter extends ArrayAdapter<Actors> {

	ArrayList<Actors> actorList;
	Context context;
	static int resource;
	LayoutInflater inflater;

	public ActorsAdapter(Context context, int textViewResourceId,
			ArrayList<Actors> objects) {
		super(context, resource, objects);
		actorList = objects;
		this.context = context;
		resource = resource;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (holder == null) {
			convertView = inflater.inflate(resource, null);
			holder = new ViewHolder();
			holder.imageView = (ImageView) convertView
					.findViewById(R.id.ivImage);
			holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			holder.tvDescription = (TextView) convertView
					.findViewById(R.id.tvDescriptionn);
			holder.tvDOB = (TextView) convertView
					.findViewById(R.id.tvDateOfBirth);
			holder.tvCountry = (TextView) convertView
					.findViewById(R.id.tvCountry);
			holder.tvHeight = (TextView) convertView
					.findViewById(R.id.tvHeight);
			holder.tvSpouse = (TextView) convertView
					.findViewById(R.id.tvSpouse);
			holder.tvChildren = (TextView) convertView
					.findViewById(R.id.tvChildren);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		new DownloadImageTask(holder.imageView).execute(actorList.get(position).getImage());
			holder.tvName.setText(actorList.get(position).getName());
			holder.tvName.setText(actorList.get(position).getName());
			holder.tvDescription.setText(actorList.get(position)
					.getDescription());
			holder.tvDOB.setText("B'day: " + actorList.get(position).getDob());
			holder.tvCountry.setText(actorList.get(position).getCountry());
			holder.tvHeight.setText("Height: "
					+ actorList.get(position).getHeight());
			holder.tvSpouse.setText("Spouse: "
					+ actorList.get(position).getSpouse());
			holder.tvChildren.setText("Children: "
					+ actorList.get(position).getChildren());
		

		return convertView;
	}

	static class ViewHolder {
		public ImageView imageView;
		public TextView tvName;
		public TextView tvDescription;
		public TextView tvDOB;
		public TextView tvCountry;
		public TextView tvHeight;
		public TextView tvSpouse;
		public TextView tvChildren;

	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon;
		}
		@Override
		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
		}
	}
}
