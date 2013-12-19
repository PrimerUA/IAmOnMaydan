package com.primerworldapps.iamonmaydan.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.primerworldapps.iamonmaydan.R;
import com.primerworldapps.iamonmaydan.entity.Post;

public class StreamAdapter extends BaseAdapter {

	private List<Post> postsList;
	private static LayoutInflater inflater = null;

	public StreamAdapter(Context context, List<Post> posts) {
		this.postsList = (ArrayList<Post>) posts;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		if (postsList != null)
			return postsList.size();
		else
			return 0;
	}

	@Override
	public Object getItem(int position) {
		return postsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, final View convertView, ViewGroup parent) {
		View vi = convertView;
		if (vi == null) {
			vi = inflater.inflate(R.layout.stream_row, null);
		}
		TextView titleView = (TextView) vi.findViewById(R.id.rowHeader);
		titleView.setText(postsList.get(position).getUser());
		TextView messgageView = (TextView) vi.findViewById(R.id.rowMessage);
		messgageView.setText(postsList.get(position).getText());
		TextView dateView = (TextView) vi.findViewById(R.id.rowDate);
		dateView.setText(postsList.get(position).getDate().toLocaleString());
		TextView linkView = (TextView) vi.findViewById(R.id.rowLink);
		linkView.setText(postsList.get(position).getPostUrl());
		linkView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UrlVisitor.visitURL(convertView.getContext(), postsList.get(position).getPostUrl());
			}
		});
		return vi;
	}

}
