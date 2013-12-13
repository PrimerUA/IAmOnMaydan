package com.primerworldapps.iamonmaydan.fragments;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.primerworldapps.iamonmaydan.MainHolderActivity;
import com.primerworldapps.iamonmaydan.R;
import com.primerworldapps.iamonmaydan.utils.Coordinates;

public class LocationFragment extends SherlockFragment {

	private ImageButton locationButton;

	private static final long LOCATION_REFRESH_TIME = 0;
	private static final float LOCATION_REFRESH_DISTANCE = 0;

	private LocationManager mLocationManager;

	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.location_fragment, container, false);
		initFragment();
		return view;
	}

	private void initFragment() {
		locationButton = (ImageButton) view.findViewById(R.id.locationButton);
		locationButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				updateLocation();
			}
		});
	}

	protected void updateLocation() {
		mLocationManager = (LocationManager) getActivity().getSystemService(Service.LOCATION_SERVICE);
		Location location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if (location == null) {
			location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		}
		if (location == null) {
			if (mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
				mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_REFRESH_TIME,
						LOCATION_REFRESH_DISTANCE, new android.location.LocationListener() {

							@Override
							public void onStatusChanged(String provider, int status, Bundle extras) {

							}

							@Override
							public void onProviderEnabled(String provider) {

							}

							@Override
							public void onProviderDisabled(String provider) {

							}

							@Override
							public void onLocationChanged(Location location) {
								checkCoordinates(location);
							}
						});
			} else {
				Intent wirelessSettingsIntent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
				startActivity(wirelessSettingsIntent);
			}
		} else {
			checkCoordinates(location);
		}
	}

	private void checkCoordinates(Location location) {
		double currentX = location.getLatitude();
		double currentY = location.getLongitude();
//		if (currentX < Coordinates.TOP_LEFT_X && currentX > Coordinates.BOTTOM_RIGHT_X
//				&& currentY < Coordinates.BOTTOM_RIGHT_Y && currentY > Coordinates.TOP_LEFT_Y) {
			getSherlockActivity().invalidateOptionsMenu();
			((MainHolderActivity) getActivity()).showFragment(1, true);
//		} else {
//			Toast.makeText(getActivity(), getString(R.string.sorry_message), Toast.LENGTH_SHORT).show();
//		}
	}
}
