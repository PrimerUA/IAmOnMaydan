package com.primerworldapps.iamonmaydan;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.primerworldapps.iamonmaydan.utils.UrlVisitor;

public class LightActivity extends SherlockActivity {

	private ImageView lightsButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.light_activity);

		screenInit();
	}

	private void screenInit() {
		getSupportActionBar().setTitle(R.string.light_title);
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_more: {
			UrlVisitor.visitURL(this, getString(R.string.my_apps_url));
			break;
		}
		case R.id.action_rate: {
			UrlVisitor.visitURL(this, getString(R.string.app_short_url));
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
