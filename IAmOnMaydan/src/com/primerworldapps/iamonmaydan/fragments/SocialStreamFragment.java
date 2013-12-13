package com.primerworldapps.iamonmaydan.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.primerworldapps.iamonmaydan.MainHolderActivity;
import com.primerworldapps.iamonmaydan.R;
import com.primerworldapps.iamonmaydan.entity.Post;
import com.primerworldapps.iamonmaydan.entity.list.PostsList;
import com.primerworldapps.iamonmaydan.utils.Coordinates;
import com.primerworldapps.iamonmaydan.utils.StreamAdapter;

public class SocialStreamFragment extends SherlockFragment {

	private ImageView locationButton;

	private static final long LOCATION_REFRESH_TIME = 0;
	private static final float LOCATION_REFRESH_DISTANCE = 0;

	private LocationManager mLocationManager;

	private View view;
	private ListView streamList;

	private ArrayList<String> titlesList;
	private ArrayList<String> messagesList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.social_stream_fragment, container,
				false);
		initFragment();
		loadPostsOnScreen();
		return view;
	}

	private void loadPostsOnScreen() {
		ArrayList<Post> postsList = PostsList.getInstance().getPostsList();
		for (int i = 0; i < postsList.size(); i++) {
			titlesList.add(postsList.get(i).getUserName());
			messagesList.add(postsList.get(i).getMessage());
		}
		// подгрузка информации с сервера
		streamList.setAdapter(new StreamAdapter(getActivity(), new String[] {
				"title1", "title2", "title3", "title4", "title5", "title6",
				"title7", "title8", "title9", "title10", "title11", "title12",
				"title13", "title14", "title15", "title16", "title17",
				"title18" },
				new String[] { "message1", "message2", "message3", "message4",
						"message5", "message6", "message7", "message8",
						"message9", "message10", "message11", "message12",
						"message13", "message14", "message15", "message16",
						"message17", "message18" }));
	}

	private void initFragment() {

		locationButton = (ImageView) view.findViewById(R.id.locationButton);
		locationButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				updateLocation();
			}
		});

		streamList = (ListView) view.findViewById(R.id.socialList);
	}

	protected void updateLocation() {
		mLocationManager = (LocationManager) getActivity().getSystemService(
				Service.LOCATION_SERVICE);
		Location location = mLocationManager
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if (location == null) {
			location = mLocationManager
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		}
		if (location == null) {
			if (mLocationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
				mLocationManager.requestLocationUpdates(
						LocationManager.NETWORK_PROVIDER,
						LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE,
						new android.location.LocationListener() {

							@Override
							public void onStatusChanged(String provider,
									int status, Bundle extras) {

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
				Intent wirelessSettingsIntent = new Intent(
						android.provider.Settings.ACTION_WIRELESS_SETTINGS);
				startActivity(wirelessSettingsIntent);
			}
		} else {
			checkCoordinates(location);
		}
	}

	private void checkCoordinates(Location location) {
		double currentX = location.getLatitude();
		double currentY = location.getLongitude();
		if (currentX < Coordinates.TOP_LEFT_X
				&& currentX > Coordinates.BOTTOM_RIGHT_X
				&& currentY < Coordinates.BOTTOM_RIGHT_Y
				&& currentY > Coordinates.TOP_LEFT_Y) {
			((MainHolderActivity) getActivity()).showFragment(1, true);
		} else {
			Toast.makeText(getActivity(), getString(R.string.sorry_message),
					Toast.LENGTH_SHORT).show();
		}
	}
}
