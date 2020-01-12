package com.to;

public class User {
	private String username;
	private String password;
	private int id;
	private String type;
	private String email;
	
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", id=" + id + ", type=" + type + ", email="
				+ email + "]";
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername( String username ) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword( String password ) {
		this.password = password;
	}
	public int getId () {
		return id;
	}
	
	public void setId ( int userId ) {
		this.id = userId;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}

