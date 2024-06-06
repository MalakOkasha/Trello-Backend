package Service;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.websocket.server.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import Messaging.JMSClient;
import Model.*;

@Stateless
public class UserService {
	@PersistenceContext(unitName="trello")
    private EntityManager entityManager;

	
	public String Register(User newUser)
	{
		try {
            
		User user=getUserByEmail(newUser.getEmail());
		if(user==null)
		{
                newUser.getName();
                newUser.getPassword();
                newUser.getEmail();
                entityManager.persist(newUser);
                return "User with email " + newUser.getEmail() + " registered successfully.";
		}
        else 
        {
            return "User with email " + newUser.getEmail() + " already exists. Please choose a different email.";
        }
		}
		catch(Exception e) 
		{
            return "An error occurred while registering the user. Please try again later.";
        }
	
	}
	//////////////////////////////////////////////
	public User getUserByEmail(String email)
	{
		List<User> newUserList = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class).setParameter("email", email).getResultList();
		if(!newUserList.isEmpty())
		{
			return newUserList.get(0);
		}
		else
		{
			return null;
		}
	}
	//////////////////////////////////////
	
	
	public String LogIn(User user) 
	{
	    User newUser = getUserByEmail(user.getEmail());
	    if(newUser !=null)
	    {
	        if (newUser.getPassword().equals(user.getPassword())) 
	        {
	            return "Logged in successfully";
	        } 
	        else 
	        {
	            return "Incorrect password";
	        }
	    }
	    else
	    {
	        return "User not found";
	    }
	}
    /////////////////////////////////////////
	public User getUserById(int id)
	{
		List<User> newUserList = entityManager.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class).setParameter("id", id).getResultList();
		if(!newUserList.isEmpty())
		{
			return newUserList.get(0);
		}
		else
		{
			return null;
		}
	}
	////////////////////////////////////////////////////
	
    public List<User> getAllUsers() 
	{
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }
    
    /////////////////////////////////////////////////////
    public String updateUserName(Map<String, String> requestBody) {
        String newUserName = requestBody.get("newUserName");
        String oldUserName = requestBody.get("oldUserName"); 
        List<User> newUserList = entityManager.createQuery("SELECT u FROM User u WHERE u.name = :oldUserName", User.class)
                                              .setParameter("oldUserName", oldUserName) 
                                              .getResultList();
        if (!newUserList.isEmpty()) {
            User user = newUserList.get(0);
            user.setName(newUserName);
            entityManager.merge(user);
            return "Username updated successfully";
        } 
        else 
        {
            return "User not found";
        }
    }
    public String updateUserEmail(Map<String, String> requestBody) {
        String newUserEmail = requestBody.get("newUserEmail");
        String oldUserEmail = requestBody.get("oldUserEmail"); 
        User user=getUserByEmail(oldUserEmail);
        if(user!=null)
        {
            user.setEmail(newUserEmail);
            entityManager.merge(user);
            return "email updated successfully";
        }
         else 
         {
            return "User not found";
         }
    }
    public String updateUserPassword(Map<String, String> requestBody) {
        String userEmail = requestBody.get("userEmail");
        String newPassword = requestBody.get("newPassword"); 
        User user=getUserByEmail(userEmail);
        if(user!=null)
        {
            user.setPassword(newPassword);
            entityManager.merge(user);
            return "password updated successfully";
        }
         else 
         {
            return "User not found";
         }
    }
    
}
