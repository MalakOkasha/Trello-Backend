package Service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import Model.Board;
import Model.User;

@Stateless
public class BoardService {
	
	
	@PersistenceContext(unitName = "trello")
	private EntityManager entityManager;
	
    
	public String createBoard(Board b, int userID) //law el user msh logged in!
	{
		User user = entityManager.find(User.class, userID);
		if(user!=null) 
		{
			
			if (entityManager.find(Board.class, b.getName()) == null)
			{
				b.setUsers(List.of(user));
				
				b.setName(b.getName());
				entityManager.persist(b);
				
				 List<Board> userBoards = user.getBoards();
		         userBoards.add(b);
		         user.setBoards(userBoards);
		         
				 entityManager.merge(user);
				return "Board is created successfully!";
				
			}
			else
				return "Board already exists!";
		}
		else
			return " User with Id " + userID + " does't exist!";
			
	}
	//////////////////////////////////////////////////////////////////
	
	public List<Board> getBoards(int userID) 
	{
		User user = entityManager.find(User.class, userID);
		
		if(user!=null)
		{
			TypedQuery<Board> query = entityManager.createQuery("SELECT b FROM Board b JOIN FETCH b.users u WHERE u.id = :userID", Board.class).setParameter("userID", userID);
			List<Board> boards = query.getResultList();
			return boards;
		}
		
		else 
			return null;		
	}
	///////////////////////////////////////////////////////////////
	public List<Board> getAllBoards () 
	{
		TypedQuery<Board> query = entityManager. createQuery ("SELECT b from Board b LEFT JOIN FETCH b.users ", Board.class);
		return query. getResultList () ;
	}

	///////////////////////////////////////////////////
	
	 public String deleteBoard(String name, int userID) 
	 { 
		    Board board = entityManager.find(Board.class, name);	
		    
		    if (board != null && board.getUsers().get(0).getId() == userID) {
		    	
		    	 entityManager.remove(board);
		    	 return "Board is deleted Successfully! ";

		    	  }
		    else 
	            return "Board does not exist or does not belong to the specified user!";
	 }
	 ///////////////////////////////////////////////////////////////
	 //@TeamLeader
	  public String inviteUser(int colabId, String boardName)
	 {
		 Board board = entityManager.find(Board.class, boardName);	
		 
		 if(board != null)
		    {
			 //int senderID = board.getUser().getId(); //id of the team leader
		     User newUser = entityManager.find(User.class, colabId);
		     if (newUser != null && !board.getUsers().contains(newUser)) 
		        {
	                board.getUsers().add(newUser);
	                entityManager.merge(board);
	                return "Invitation is Sent :)";
	            }
		     else
		    	 return "The User you want to send and invitation to doesn't exist!";
		    }
		 return "The Board doesn't exist!";
	 }
	 //permission collab only
	 public String acceptInvitation(int colabId, String boardName, String response)
	 {
		 User user = entityManager.find(User.class, colabId);
	     Board board = entityManager.find(Board.class, boardName);
	     if (user != null && board != null)
	     {
	    	 if(response.equals("Accept"))
			 {
				 return "Invitation is Accepted :)";
			 }
			 else if(response.equals("Deny"))
			 {
				if (board.getUsers().contains(user))
				{
					 board.getUsers().remove(user);
			         entityManager.merge(board);
			         return "Invitation is Denied :(";	        
				}
				else
					return "You are not invited to this Board!";
			     
			 }
			 else
				 return "You can only (Accept) or (Deny) the invitation";
	    	 
	     }
	    
	     return "The User or Board doesn't exist!";
	 }

}
