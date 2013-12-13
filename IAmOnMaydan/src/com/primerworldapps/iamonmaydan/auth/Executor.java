package com.primerworldapps.iamonmaydan.auth;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;

import com.google.gson.Gson;
import com.primerworldapps.iamonmaydan.entity.User;

public class Executor {

	private Gson gson = new Gson();
	private final static String url = "http://eagleclub.meximas.com/registerMaidan.php";

	public void sendUSerInfo(User user) {
		try {
			HttpPost httpPost = new HttpPost(url);
			StringEntity buff = new StringEntity(gson.toJson(user).toString(), HTTP.UTF_8);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			httpPost.setEntity(buff);

			new AsyncRequest().execute(httpPost);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
