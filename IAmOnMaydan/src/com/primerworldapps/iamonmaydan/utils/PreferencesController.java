package com.primerworldapps.iamonmaydan.utils;

import com.primerworldapps.iamonmaydan.entity.User;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesController {

	private final static String PREFS_NAME = "IAmOnMaydanPreferences";
	private final String USER_ID = "user_id";
	private final String USER_STATE = "user_state";
	private final String USER_EMAIL = "user_email";
	private final String USER_NAME = "user_name";

	private static SharedPreferences preference;
	private static PreferencesController instance;
	private static User user;
	
	private static int type; //active-inactive for cases and solutions

	public static PreferencesController getInstance() {
		if (instance == null) {
			instance = new PreferencesController();
		}
		return instance;
	}

	public void init(Context context) {
		preference = context.getSharedPreferences(PREFS_NAME, 0);
		user = User.getInstance();

		loadUserInfo();
	}

	public void saveUserInfo() {
		SharedPreferences.Editor editor = preference.edit();
		editor.putInt(USER_ID, user.getId());
		editor.putBoolean(USER_STATE, user.isLoggedIn());
		editor.putString(USER_EMAIL, user.getEmail());
		editor.putString(USER_NAME, user.getName());
		editor.commit();
	}

	public void loadUserInfo() {
		user.setId(preference.getInt(USER_ID, 0));
		user.setLoggedIn(preference.getBoolean(USER_STATE, false));
		user.setEmail(preference.getString(USER_EMAIL, "error"));
		user.setName(preference.getString(USER_NAME, "error"));
	}
	
	public void clear() {
		preference.edit().clear().commit();
	}

	public static int getType() {
		return type;
	}

	public static void setType(int type) {
		PreferencesController.type = type;
	}

}
