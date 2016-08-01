package com.example.arvin.myhttpweb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.arvin.myhttpweb.R;


public class MyAdapter extends BaseAdapter {
	private String[] list;
	private Context context;
	private RequestQueue queue;
	private ImageLoader imageLoader;

	public MyAdapter(Context context, String[] list) {
		this.context = context;
		this.list = list;
		queue = Volley.newRequestQueue(context);
		imageLoader = new ImageLoader(queue, new BitmapCache());
	}

	@Override
	public int getCount() {
		return list.length;
	}

	@Override
	public Object getItem(int position) {
		return list[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.list_item2, null);
			holder.img = (NetworkImageView) convertView
					.findViewById(R.id.userimage);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final String imgUrl = list[position];

		if (imgUrl != null && !imgUrl.equals("")) {
			holder.img.setDefaultImageResId(R.drawable.daqiao);
			holder.img.setErrorImageResId(R.drawable.daqiao);
			holder.img.setImageUrl(imgUrl, imageLoader);
		}

		return convertView;
	}

	class ViewHolder {
		NetworkImageView img;
	}
}
