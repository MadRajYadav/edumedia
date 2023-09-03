package com.educational.edumedia;

import jakarta.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="data")
public class UserModel {

//	@XmlAttribute
	private int Id;
	private String email;
	private String password;
	public UserModel() {
		super();
	}
	public UserModel(int id, String email, String password) {
		super();
		Id = id;
		this.email = email;
		this.password = password;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
