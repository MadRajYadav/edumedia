package com.educational.edumedia;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "auth")
public class authModel {
	
	private String userId;
	private String password;
	public authModel() {
		super();
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
