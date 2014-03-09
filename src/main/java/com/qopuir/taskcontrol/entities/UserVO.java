package com.qopuir.taskcontrol.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserVO {
	private String username;
	private String password;
	private String email;

	public UserVO() {
	}
	
	public UserVO(String username) {
		this.username = username;
	}
	
	public UserVO(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}

	public UserVO setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public UserVO setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public UserVO setEmail(String email) {
		this.email = email;
		return this;
	}
}