package me.byungjin.db;

public class UserSchema {
	String id;
	String password;
	
	public UserSchema(String id, String pw) {
		this.id = id;
		this.password = pw;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getPassword() {
		return this.password;
	}
}
