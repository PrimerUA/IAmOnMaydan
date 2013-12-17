package com.primerworldapps.iamonmaydan.executors;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.primerworldapps.iamonmaydan.entity.Post;
import com.primerworldapps.iamonmaydan.entity.User;

public class OperationExecutor {

	// //////////////////////////////////////// URLS

	private static String registerUrl = "http://svit.co/api.php?do=register";
	private static String createPostUrl = "http://svit.co/api.php?do=posts";

	private static String getPostsUrl(int from, int length) {
		return "http://svit.co/api.php?do=posts&from=" + from + "&number=" + length;
	}

	// //////////////////////////////////////// PUBLIC

	public void register(String name, String email) {
		try {
			HttpPost httpPost = new HttpPost(registerUrl);
			StringEntity buff = new StringEntity(new Gson().toJson(
					new UserRegistration(name, email)).toString(), HTTP.UTF_8);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			httpPost.setEntity(buff);

			HttpResponse response = new AsyncRequest().execute(httpPost).get();
			if (response != null) {
				fillUserDetails(name, email, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String createPost(NewPost newPost) {
		try {
			HttpPost httpPost = new HttpPost(createPostUrl);
			StringEntity buff = new StringEntity(new Gson().toJson(newPost)
					.toString(), HTTP.UTF_8);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			httpPost.setEntity(buff);

			HttpResponse response = new AsyncRequest().execute(httpPost).get();
			if (response != null) {
				return getPostUrl(response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Post> getPostList(int from, int length) {
		try {
			InputStream stream = getStream(getPostsUrl(from, length));

			if (stream != null) {
				return parseStreamListPost(stream);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// //////////////////////////////////////// PRIVATE

	private InputStream getStream(String url) {
		InputStream in = null;
		try {
			in = new AsyncStream().execute(url).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		return in;
	}

	private void fillUserDetails(String name, String email,
			HttpResponse response) throws IllegalStateException, IOException {

		InputStream in = response.getEntity().getContent();
		JsonReader reader = new JsonReader(new InputStreamReader(in));

		reader.beginObject();

		while (reader.hasNext()) {
			String buff = reader.nextName();

			if ("user".equals(buff)) {
				User.getInstance().setId(reader.nextInt());
			} else if ("token".equals(buff)) {
				User.getInstance().setToken(reader.nextString());
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();
		reader.close();
		User.getInstance().setName(name).setEmail(email).setLoggedIn(true);
		
	}

	private String getPostUrl(HttpResponse response) throws IOException {
		String url = null;
		InputStream in = response.getEntity().getContent();
		JsonReader reader = new JsonReader(new InputStreamReader(in));

		reader.beginObject();

		while (reader.hasNext()) {

			if ("url".equals(reader.nextName())) {
				url = reader.nextString();
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();
		reader.close();

		return url;
	}

	@SuppressWarnings("unchecked")
	private List<Post> parseStreamListPost(InputStream stream)
			throws IOException {
		List<Post> result = null;
		Reader reader = new InputStreamReader(stream);

		Type listType = new TypeToken<List<Post>>() {
		}.getType();
		Object buff = new Gson().fromJson(reader, listType);
		result = (List<Post>) buff;

		reader.close();
		return result;
	}

	@SuppressWarnings("unused")
	private class UserRegistration {
		private String name;
		private String email;

		public UserRegistration(String name, String email) {
			this.name = name;
			this.email = email;
		}
	}

	@SuppressWarnings("unused")
	public class NewPost {

		private int user;
		private String token;
		private String text;
		private double lat;
		private double longit;

		public NewPost(int userId, String token, String message,
				double latititude, double longitude) {
			this.user = userId;
			this.token = token;
			this.text = message;
			this.lat = 50.4500;
			this.longit = 30.500;
		}

	}

}
