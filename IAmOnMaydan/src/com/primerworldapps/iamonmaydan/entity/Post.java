package com.primerworldapps.iamonmaydan.entity;

import java.sql.Date;

public class Post {

	private long date;
	private String user;
	private String text;
	private String url;

	// avatar, likes, photos, location

	public String getUser() {
		return user;
	}

	public void setUser(String userName) {
		this.user = userName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPostUrl() {
		return url;
	}

	public void setPostUrl(String postUrl) {
		this.url = postUrl;
	}

	public Date getDate() {
		return new Date(date * 1000);
	}

}
