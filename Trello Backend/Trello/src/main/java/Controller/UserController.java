package Controller;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import Model.User;
import Service.UserService;

//@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/user")

public class UserController {
	
	@EJB
	UserService userService;
	//TEST DONE
	
	@POST
	@Path("/register")
	public String Register(User newUser)
	{
			return userService.Register(newUser);
	}

	//TEST DONE
	@POST
	@Path("/login")
	public String LogIn(User user) 
	{
	    return userService.LogIn(user);
	}

/////////////////////////////////////////////////	

	@GET
    @Path("/all")
    public List<User> getAllUsers() 
	{
        return userService.getAllUsers();
    }
///////////////////////////////////////////
	@PUT
    @Path("/updateByUserName")
    public String updateUserName(Map<String, String> requestBody) 
	{
		return userService.updateUserName(requestBody);
	}
	///////////////////////////////////////////
	@PUT
    @Path("/updateByEmail")
	 public String updateUserEmail(Map<String, String> requestBody) {
		return userService.updateUserEmail(requestBody);
	 }
    //////////////////////////////////////////
	@PUT
    @Path("/updateByPassword")
    public String updateUserPassword(Map<String, String> requestBody) {
    	return userService.updateUserPassword(requestBody);
    }
//////////////////////////////////////////
	}
	