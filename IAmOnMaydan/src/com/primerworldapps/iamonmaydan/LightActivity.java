package com.primerworldapps.iamonmaydan;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class LightActivity extends SherlockActivity {

	private ImageView lightsButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.light_activity);

		screenInit();
	}

	private void screenInit() {
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		lightsButton = (ImageView) findViewById(R.id.lightsToggle);
		if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
			try {
				lightsButton.setOnClickListener(new OnClickListener() {
					boolean state = false;
					Camera cam;
					@Override
					public void onClick(View v) {
						if (!state) {
							cam = Camera.open();
							Parameters p = cam.getParameters();
							p.setFlashMode(Parameters.FLASH_MODE_TORCH);
							cam.setParameters(p);
							cam.startPreview();
							state = true;
							Toast.makeText(getBaseContext(), getString(R.string.light_on), Toast.LENGTH_SHORT).show();
						} else {
							cam.stopPreview();
							cam.release();
							state = false;
							Toast.makeText(getBaseContext(), getString(R.string.light_off), Toast.LENGTH_SHORT).show();
						}
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(getBaseContext(), "Exception flashLightOn()", Toast.LENGTH_SHORT).show();
			}
		} else {
			lightsButton.setVisibility(View.GONE);
		}
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