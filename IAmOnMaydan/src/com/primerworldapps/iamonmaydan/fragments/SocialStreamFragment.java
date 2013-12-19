package com.primerworldapps.iamonmaydan.fragments;

import java.util.List;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.primerworldapps.iamonmaydan.MainHolderActivity;
import com.primerworldapps.iamonmaydan.R;
import com.primerworldapps.iamonmaydan.entity.Post;
import com.primerworldapps.iamonmaydan.executors.OperationExecutor;
import com.primerworldapps.iamonmaydan.utils.Coordinates;
import com.primerworldapps.iamonmaydan.utils.StreamAdapter;

public class SocialStreamFragment extends SherlockFragment {

	private ImageView locationButton;

	private static final long LOCATION_REFRESH_TIME = 0;
	private static final float LOCATION_REFRESH_DISTANCE = 0;

	private LocationManager mLocationManager;

	private View view;
	private ListView streamList;
	private List<Post> posts;

	private int postsQuantity = 0;
	private boolean isEnd = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.social_stream_fragment, container, false);
		initFragment();
		loadPostsOnScreen();
		return view;
	}

	public void loadPostsOnScreen() {
		isEnd = false;
		final ProgressDialog myProgressDialog = ProgressDialog.show(getActivity(), getString(R.string.connection), getString(R.string.connection_wait), true);
		new Thread() {
			public void run() {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						posts = new OperationExecutor().getPostList(0, 6);
						streamList.setAdapter(new StreamAdapter(getActivity(), posts));
						postsQuantity = 10;
					}
				});
				myProgressDialog.dismiss();
			}
		}.start();
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
		streamList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				String shareBody = "Новина з Майдану: ''" + posts.get(arg2).getText() + "''\nПряме посилання: " + posts.get(arg2).getPostUrl();
				Intent sharingIntent = new Intent(Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
				startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_via)));
			}
		});
		streamList.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if (!isEnd) {
					if (visibleItemCount != 0 && ((firstVisibleItem + visibleItemCount) >= (totalItemCount))) {
						List<Post> newPosts = new OperationExecutor().getPostList(postsQuantity, 3);
						if (newPosts.size() == 0) {
							isEnd = true;
						} else {
							posts.addAll(newPosts);
							((StreamAdapter) streamList.getAdapter()).notifyDataSetChanged();
							postsQuantity += 5;
						}
					}
				}
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
				mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE, new android.location.LocationListener() {

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
		if (currentX < Coordinates.TOP_LEFT_X && currentX > Coordinates.BOTTOM_RIGHT_X && currentY < Coordinates.BOTTOM_RIGHT_Y && currentY > Coordinates.TOP_LEFT_Y) {
			((MainHolderActivity) getActivity()).showFragment(1, true);
			getActivity().supportInvalidateOptionsMenu();
		} else {
			Toast.makeText(getActivity(), getString(R.string.sorry_message), Toast.LENGTH_SHORT).show();
		}
	}

}