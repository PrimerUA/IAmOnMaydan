package com.primerworldapps.iamonmaydan.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.primerworldapps.iamonmaydan.R;

public class ShareFragment extends SherlockFragment {

	private ImageButton checkinButton;
	private EditText messageEdit;

	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.share_fragment, container, false);
		initFragment();
		return view;
	}

	private void initFragment() {
		checkinButton = (ImageButton) view.findViewById(R.id.checkinButton);
		messageEdit = (EditText) view.findViewById(R.id.messageText);

		checkinButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (messageEdit.getText().toString().equals("")) {
					Toast.makeText(getActivity(), getString(R.string.fill_message), Toast.LENGTH_SHORT).show();
				} else {
					startSharing();
				}
			}
		});
	}

	private void startSharing() {
		String shareBody = getString(R.string.app_name) + " Повідомляю: ''" + messageEdit.getText().toString() + "'' " + getString(R.string.app_short_url_text);
		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
		startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_via)));
	}


}
