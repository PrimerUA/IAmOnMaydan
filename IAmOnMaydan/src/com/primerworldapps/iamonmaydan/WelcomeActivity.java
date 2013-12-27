package com.primerworldapps.iamonmaydan;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockActivity;

public class WelcomeActivity extends SherlockActivity implements OnClickListener {

	private Button startButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_screen);
		initScreen();
	}

	private void initScreen() {
		getSherlock().getActionBar().setTitle(R.string.action_info);
		
		startButton = (Button) findViewById(R.id.startButton);
		startButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		finish();
	}
}
