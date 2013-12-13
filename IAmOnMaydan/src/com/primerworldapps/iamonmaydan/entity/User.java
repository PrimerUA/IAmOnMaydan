package com.primerworldapps.iamonmaydan.entity;

public class User {

	private static User instance;
	private boolean loggedIn;
	private int id;
	private String name;
	private String email;

	public static User getInstance() {
		if (instance == null) {
			instance = new User();
		}
		return instance;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

}
