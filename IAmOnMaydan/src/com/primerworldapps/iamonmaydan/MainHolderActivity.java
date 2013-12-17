package com.primerworldapps.iamonmaydan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.PlusClient.OnAccessRevokedListener;
import com.primerworldapps.iamonmaydan.entity.User;
import com.primerworldapps.iamonmaydan.fragments.SocialStreamFragment;
import com.primerworldapps.iamonmaydan.fragments.NewMessageFragment;
import com.primerworldapps.iamonmaydan.utils.PreferencesController;

public class MainHolderActivity extends SherlockFragmentActivity implements ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {

	private final int STEPS = 2;
	private Fragment[] fragments = new Fragment[STEPS];

	private PlusClient plusClient;
	private int currentFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_holder_activity);

<<<<<<< HEAD
		// TODO:for debug
		// new OperationExecutor().getPostList(1, 5);
		// new OperationExecutor().register("Igor Filakhtov",
		// "igorfilakhtov@mail.com");

=======
		//TODO:for debug
		new OperationExecutor().getPostList(1, 5);
		new OperationExecutor().register("Igor Filakhtov", "igorfilakhtov@mail.com");
		
		//я сам офигел, когда вот так получилось, но создание надо писать так
		OperationExecutor op = new OperationExecutor();
		op.createPost(op.new NewPost(User.getInstance().getId(), User.getInstance().getToken(), "yeaaaahhhh2", 50.1234, 30.4567));
		
>>>>>>> 7a3d17ab065ee8f21d3d65935591178af4dbb039
		PreferencesController.getInstance().init(this);
		if (!User.getInstance().isLoggedIn()) {
			startActivity(new Intent(this, LoginActivity.class));
		}

		FragmentManager fm = getSupportFragmentManager();
		SocialStreamFragment startFragment = (SocialStreamFragment) fm.findFragmentById(R.id.locationFragment);
		fragments[0] = startFragment;
		fragments[1] = (NewMessageFragment) fm.findFragmentById(R.id.shareFragment);

		FragmentTransaction transaction = fm.beginTransaction();
		for (int i = 0; i < fragments.length; i++) {
			transaction.hide(fragments[i]);
		}
		transaction.commit();
		getSupportActionBar().setTitle(getString(R.string.app_name));
		showFragment(currentFragment = 0, true);

		fm.addOnBackStackChangedListener(new OnBackStackChangedListener() {

			@Override
			public void onBackStackChanged() {
				if (getSupportFragmentManager().getBackStackEntryCount() == 0)
					finish();
			}
		});
	}

	public void showFragment(int fragmentIndex, boolean addToBackStack) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		for (int i = 0; i < fragments.length; i++) {
			if (i == fragmentIndex) {
				transaction.show(fragments[i]);
			} else {
				transaction.hide(fragments[i]);
			}
		}

		if (addToBackStack) {
			transaction.addToBackStack(null);
		}
		// if (fragmentIndex == 0) {
		// getSupportActionBar().setTitle(getString(R.string.app_name));
		// } else if (fragmentIndex == 1) {
		// getSupportActionBar().setTitle(getString(R.string.welcome_maydan));
		// }
		currentFragment = fragmentIndex;
		supportInvalidateOptionsMenu();
		transaction.commit();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_light: {
			startActivity(new Intent(MainHolderActivity.this, LightActivity.class));
			break;
		}
		case R.id.action_hymn: {
			startActivity(new Intent(MainHolderActivity.this, HymnActivity.class));
			break;
		}
		case R.id.action_logout: {
			logout();
			break;
		}
		}
		return super.onOptionsItemSelected(item);
	}

	private void logout() {
		plusClient = new PlusClient.Builder(this, this, this).setVisibleActivities("http://schemas.google.com/AddActivity", "http://schemas.google.com/BuyActivity").build();
		plusClient.connect();
		PreferencesController.getInstance().clear();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu);
		if (currentFragment == 0) {
			menu.findItem(R.id.action_hymn).setVisible(false);
			menu.findItem(R.id.action_light).setVisible(false);
			menu.findItem(R.id.action_refresh).setVisible(true);
		} else {
			menu.findItem(R.id.action_hymn).setVisible(true);
			menu.findItem(R.id.action_light).setVisible(true);
			menu.findItem(R.id.action_refresh).setVisible(false);
		}
		return true;
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		Toast.makeText(this, " Error code: " + result.getErrorCode(), Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onConnected(Bundle arg0) {
		if (plusClient.isConnected()) {
			plusClient.clearDefaultAccount();
			plusClient.revokeAccessAndDisconnect(new OnAccessRevokedListener() {
				@Override
				public void onAccessRevoked(ConnectionResult status) {

				}
			});
			plusClient.disconnect();
		}
		startActivity(new Intent(MainHolderActivity.this, LoginActivity.class));
	}

	@Override
	public void onDisconnected() {
		Toast.makeText(this, getString(R.string.google_disconnected), Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onResume() {
		super.onResume();
		getSupportActionBar().setTitle(R.string.app_name);
	}

	@Override
	public void onBackPressed() {
		if (currentFragment == 0) {
			finish();
		} else {
			showFragment(0, false);
		}
	}

}
