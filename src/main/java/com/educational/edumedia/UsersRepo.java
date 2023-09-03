package com.educational.edumedia;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import jakarta.ws.rs.core.Response;



public class UsersRepo {
	
	final static Logger logger = Logger.getLogger(UsersRepo.class);
	String url = "jdbc:mysql://localhost:3306/educationalmedia";
	String dbUser = "root";
	String password = "";
	String dbName = "educationalmedia";
	Connection con = null;
	
	public UsersRepo() {
		super();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, dbUser, password);
		} catch (Exception e) {
			logger.error(e);
		}


	}

	public Response addUser(UserModel user){
		String query = "insert into users values(?,?,?)";
		try{
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, user.getId());
			st.setString(2, user.getEmail());
			st.setString(3, user.getPassword());
			int result  = st.executeUpdate();
			if(result == 1){
				query = "select * from users where email = '" + user.getEmail()+"'";
					Statement st1 = con.createStatement();
					ResultSet rs = st.executeQuery(query);
					rs.next();
				return Response.ok("{\"Status\" : \"Success\", \"Id\" : \""+rs.getString(1)+"\"}").build();
			}else{
				return Response.status(Response.Status.BAD_REQUEST).entity("{\"Status\" : \"Failed\"}").build();
			}
		}catch(Exception e){
			logger.error(e);
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"Status\" : \"Server error\"}").build();
		
	}

	public List<UserModel> getUsers(){
		List<UserModel> users = new ArrayList<>();
		String query = "select * from users";
		try{
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				UserModel user = new UserModel(rs.getInt(1), rs.getString(2), rs.getString(3));
				users.add(user);
			}
			con.close();
		}catch(Exception e){
			logger.error(e);
		}
		return users;
	}
	
	public UserModel getUser(int id) {
		String query = "select * from users where Id = " + id;
		UserModel user = null;
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			if(rs.next()) {
				user = new UserModel(rs.getInt(1), rs.getString(2), rs.getString(3));
				return user;
			}
			con.close();
		}catch(Exception e) {
			logger.error(e);
		}
		
		return user;
		
	}
	
	public Response authUser(authModel auth) {
		String status = "";
		String query = "select * from users where email = '" + auth.getUserId()+"'";
		try {
			
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			if(rs.next()) {
				if(rs.getString(3).equals(auth.getPassword())) {
					status = "{\"Status\" : \"Success\", \"Id\" : \""+rs.getString(1)+"\"}";
					return Response.ok(status).build();
				}else {
					status = "{\"Status\" : \"Wrong password\"}";
					return Response.status(Response.Status.UNAUTHORIZED).entity(status).build();
				}
			}else {
				status = "{\"Status\" : \"Unknown userId\"}";
				return Response.status(Response.Status.UNAUTHORIZED).entity(status).build();
			}
		}catch(Exception e) {
			logger.error(e);
			status = "{\"Status\" : \"Server error\"}";
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(status).build();
		}
		
	}
	
	
	
	protected void finalize() {
		try {
			con.close();
		} catch (SQLException e) {
			logger.error(e);
		}
	}
	
	
	
	
	
}
