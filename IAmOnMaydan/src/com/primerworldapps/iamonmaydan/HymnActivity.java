package com.primerworldapps.iamonmaydan;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class HymnActivity extends SherlockActivity {

	private Button playButton;
	private Button pauseButton;

	private MediaPlayer song;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hymn_activity);

		screenInit();
	}

	private void screenInit() {
		getSupportActionBar().setTitle(R.string.hymn_title);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		playButton = (Button) findViewById(R.id.playButton);
		pauseButton = (Button) findViewById(R.id.pauseButton);
		pauseButton.setEnabled(false);

		song = MediaPlayer.create(this, R.raw.hymn_ua);
		song.setOnCompletionListener(new OnCompletionListener() {

			public void onCompletion(MediaPlayer mp) {
				playButton.setText(getString(R.string.play));
			}
		});

		playButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!song.isPlaying()) {
					song.start();
					playButton.setText(getString(R.string.stop));
					playButton.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_lock_silent_mode, 0, 0, 0);
					pauseButton.setEnabled(true);
				} else {
					pauseButton.setEnabled(false);
					playButton.setText(getString(R.string.play));
					playButton.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_media_play, 0, 0, 0);
					pauseButton.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_media_pause, 0, 0, 0);
					pauseButton.setText(getString(R.string.pause));
					song.stop();
					song.reset();
					song = MediaPlayer.create(HymnActivity.this, R.raw.hymn_ua);
				}
			}
		});

		pauseButton.setOnClickListener(new OnClickListener() {
			int position;

			@Override
			public void onClick(View v) {
				if (song.isPlaying()) {
					song.pause();
					position = song.getCurrentPosition();
					playButton.setText(getString(R.string.resume));
					playButton.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_media_play, 0, 0, 0);
					pauseButton.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_media_play, 0, 0, 0);
					pauseButton.setText(getString(R.string.resume));
				} else {
					song.seekTo(position);
					song.start();
					pauseButton.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_media_pause, 0, 0, 0);
					pauseButton.setText(getString(R.string.pause));
				}
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (song.isPlaying()) {
			pauseButton.setEnabled(false);
			playButton.setText(getString(R.string.play));
			playButton.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_media_play, 0, 0, 0);
			pauseButton.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_media_pause, 0, 0, 0);
			pauseButton.setText(getString(R.string.pause));
			song.stop();
			song = MediaPlayer.create(HymnActivity.this, R.raw.hymn_ua);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		song.release();
	}

	private void visitURL(String url) {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		startActivity(browserIntent);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_more: {
			visitURL(getString(R.string.my_apps_url));
			break;
		}
		case R.id.action_rate: {
			visitURL(getString(R.string.app_short_url));
			break;
		}
		default: {
			finish();
		}
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.other, menu);
		return true;
	}
}
