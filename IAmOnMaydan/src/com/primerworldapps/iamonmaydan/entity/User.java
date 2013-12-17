package com.primerworldapps.iamonmaydan.entity;

public class User {

	private static User instance;
	private boolean loggedIn;
	private int id;
	private String name;
	private String email;
	private String token;

	public static User getInstance() {
		if (instance == null) {
			instance = new User();
		}
		return instance;
	}
	
	public User() {
	}

	public int getId() {
		return id;
	}

	public User setId(int id) {
		this.id = id;
		return instance;
	}

	public String getName() {
		return name;
	}

	public User setName(String name) {
		this.name = name;
		return instance;
	}

	public String getEmail() {
		return email;
	}

	public User setEmail(String email) {
		this.email = email;
		return instance;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public User setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
		return instance;
	}

	public String getToken() {
		return token;
	}

	public User setToken(String token) {
		this.token = token;
		return instance;
	}

}
