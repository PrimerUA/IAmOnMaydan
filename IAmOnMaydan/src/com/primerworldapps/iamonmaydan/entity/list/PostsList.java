package com.primerworldapps.iamonmaydan.entity.list;

import java.util.ArrayList;

import com.primerworldapps.iamonmaydan.entity.Post;

public class PostsList {

	private static PostsList instance;

	public static PostsList getInstance() {
		if (instance == null) {
			instance = new PostsList();
		}
		return instance;
	}

	public PostsList() {
	}

	private ArrayList<Post> postsList;

	public ArrayList<Post> getPostsList() {
		return postsList;
	}

	public void setPostsList(ArrayList<Post> postsList) {
		this.postsList = postsList;
	}
}
