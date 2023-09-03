package com.educational.edumedia;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("users")
public class UserResource {
	
	
	
	private final static String passForApi = "@Educational-Media+ST-STD-7uy2323hj";

	@GET
	@Path("/check")
	@Produces(MediaType.APPLICATION_JSON)
    public Response getIt() {
		System.out.print("called");
        return Response.ok("User api is called.").build();
    }
	
	
	@POST
	@Path("/add-new")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(@HeaderParam("authKey") String authKey, UserModel user) {
		if(isAuthorized(authKey)) {
			boolean isEmail = isValidEmail(user.getEmail());
			boolean isPassword = isValidPassword(user.getPassword());
			if( isEmail && isPassword) {
				UsersRepo repo = new UsersRepo();
				Response response = repo.addUser(user);
				return response;
			}else if(!isEmail) {
				return Response.status(Response.Status.NOT_FOUND).entity("UserId is not valid.").build();
			}else {
				return Response.status(Response.Status.NOT_FOUND).entity("Password is not valid.").build();
			}
		
		}else {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Null").build();
		}
	}
	
	@GET
	@Path("/get-user/id={id}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getUser(@HeaderParam("authKey") String authKey, @PathParam("id") int id) {
		if(isAuthorized(authKey)) {
			try {
				Response response = null;
				UsersRepo repo = new UsersRepo();
				UserModel user = repo.getUser(id);
				 
				if(user != null) {
					response = Response.ok( new ResponseModel("Success",user)).build();
				}else {
					response = Response.status(Response.Status.NOT_FOUND).entity(new ResponseModel("Failed",user)).build();
				}
				return response;
			}catch(Exception e) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"Status\" : \"Server error\"}").build();
			}
		}else {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Null").build();
		}
	}


	@POST
	@Path("/auth-user")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Produces(MediaType.APPLICATION_JSON)
	public Response authUser(@HeaderParam("authKey") String authKey,authModel auth) {
		if(isAuthorized(authKey)) {
			UsersRepo repo = new UsersRepo();
			return repo.authUser(auth);
		}else {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Null").build();
		}
	}
	
	
	
	
	 public static boolean isValidEmail(String email) {
	        String regex = "^[a-z]+[a-z0-9]+[a-z0-9.]+[a-z0-9]+@[a-z]{2,}+\\.[a-z]{2,}+$";

	        Pattern pattern = Pattern.compile(regex);
	        Matcher matcher = pattern.matcher(email);

	        return matcher.matches();
	    }
	 public static boolean isValidPassword(String password) {
		 String regex ="^(?=.*[0-9])(?=.*[A-Z])(?=.*[.#@$&*])[a-zA-Z0-9.#@$&*]{8,}$";
		 Pattern pattern = Pattern.compile(regex);
	        Matcher matcher = pattern.matcher(password);
	        return matcher.matches();
	 }
	 
	 public static boolean isAuthorized(String pass) {
		 if(pass.equals(passForApi)) {
			 return true;
		 }
		 return false;
	 }
	
}
