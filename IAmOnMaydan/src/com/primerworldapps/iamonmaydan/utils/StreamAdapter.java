package com.primerworldapps.iamonmaydan.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.primerworldapps.iamonmaydan.R;

public class StreamAdapter extends BaseAdapter {

	Context context;
	String[] titles;
	String[] messages;
	private static LayoutInflater inflater = null;

	public StreamAdapter(Context context, String[] titles, String[] messages) {
		this.context = context;
		this.titles = titles;
		this.messages = messages;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return messages.length;
	}

	@Override
	public Object getItem(int position) {
		return messages[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (vi == null) {
			vi = inflater.inflate(R.layout.stream_row, null);
		}
		TextView titleView = (TextView) vi.findViewById(R.id.rowHeader);
		titleView.setText(titles[position]);
		TextView messgageView = (TextView) vi.findViewById(R.id.rowMessage);
		messgageView.setText(messages[position]);
		return vi;
	}

}
