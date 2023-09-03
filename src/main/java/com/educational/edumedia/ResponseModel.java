package com.educational.edumedia;



import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResponseModel {

	private String Status;
	private UserModel Data;
	
	public ResponseModel() {
		super();
	}
	public ResponseModel(String status, UserModel data) {
		super();
		Status = status;
		Data = data;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public UserModel getData() {
		return Data;
	}
	public void setData(UserModel data) {
		Data=data;
	}
	
	
	
	
	
}
