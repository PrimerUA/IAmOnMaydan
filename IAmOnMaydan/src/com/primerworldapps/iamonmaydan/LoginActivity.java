package com.primerworldapps.iamonmaydan;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.plus.PlusClient;
import com.primerworldapps.iamonmaydan.entity.User;
import com.primerworldapps.iamonmaydan.executors.OperationExecutor;
import com.primerworldapps.iamonmaydan.utils.PreferencesController;

public class LoginActivity extends SherlockActivity implements GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {

	private SignInButton loginButton;
	private PlusClient plusClient;
	
	public final int REQUEST_CODE_RESOLVE_ERR = 9000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);

		plusClient = new PlusClient.Builder(this, this, this).setVisibleActivities("http://schemas.google.com/AddActivity",
				"http://schemas.google.com/BuyActivity").build();
		plusClient.connect();

		screenInit();
	}

	private void screenInit() {
		getSupportActionBar().setTitle(R.string.login);
		loginButton = (SignInButton) findViewById(R.id.googleSignInButton);
		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!plusClient.isConnected()) {
					plusClient.connect();
				}
				finish();
			}
		});
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (result.hasResolution()) {
			try {
				result.startResolutionForResult(this, REQUEST_CODE_RESOLVE_ERR);
			} catch (IntentSender.SendIntentException e) {
				plusClient.connect();
			}
		} else {
			Toast.makeText(this, " Error code: " + result.getErrorCode(), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onConnected(Bundle arg0) {
		if (!User.getInstance().isLoggedIn()) {
			PreferencesController.getInstance().saveUserInfo();
			new OperationExecutor().register(plusClient.getCurrentPerson().getDisplayName(), plusClient.getAccountName());
		}
		Toast.makeText(this, getString(R.string.google_connected), Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDisconnected() {
		Toast.makeText(this, getString(R.string.google_disconnected), Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_RESOLVE_ERR && resultCode == RESULT_OK) {
			plusClient.connect();
		}
	}

	@Override
	public void onBackPressed() {
		Intent backtoHome = new Intent(Intent.ACTION_MAIN);
		backtoHome.addCategory(Intent.CATEGORY_HOME);
		backtoHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(backtoHome);
	}

}
