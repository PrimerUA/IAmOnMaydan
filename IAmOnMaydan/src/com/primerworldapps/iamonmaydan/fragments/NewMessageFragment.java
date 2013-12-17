package com.primerworldapps.iamonmaydan.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.primerworldapps.iamonmaydan.R;
import com.primerworldapps.iamonmaydan.executors.OperationExecutor;
import com.primerworldapps.iamonmaydan.utils.StreamAdapter;

public class NewMessageFragment extends SherlockFragment {

	private ImageView checkinButton;
	private EditText messageEdit;

	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.new_message__fragment, container, false);
		initFragment();
		return view;
	}

	private void initFragment() {
		checkinButton = (ImageView) view.findViewById(R.id.checkinButton);
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
		final ProgressDialog myProgressDialog = ProgressDialog.show(getActivity(), getString(R.string.connection),
				getString(R.string.connection_wait), true);
		new Thread() {
			public void run() {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						new OperationExecutor().createPost(new OperationExecutor().new NewPost(messageEdit.getText().toString(), 50.1234, 30.4567));
					}
				});
				myProgressDialog.dismiss();
			}
		}.start();

//		String shareBody = getString(R.string.app_name) + " Повідомляю: ''" + messageEdit.getText().toString() + "'' " + getString(R.string.app_short_url_text);
//		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//		sharingIntent.setType("text/plain");
//		sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
//		startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_via)));
	}


}
