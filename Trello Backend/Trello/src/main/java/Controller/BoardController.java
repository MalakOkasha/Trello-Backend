package Controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import Model.Board;
import Service.BoardService;

//@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/board")
public class BoardController {
	

	  @EJB
	  BoardService boardService;
	 
	
	  @POST
	  @Path("/createBoard/{userID}") 
	  public String createBoard(Board b, @PathParam("userID") int userID) 
	  {
		  
	    //boardService.createBoard(b); 
	    return boardService.createBoard(b, userID);  //"Board created";
	  }
	  
	  @GET
	  @Path("/getBoards/{userID}") 
	  public List<Board> getBoard(@PathParam("userID") int userID) 
	  {
	    return boardService.getBoards(userID); 
	  }
	  
	  @GET
	  @Path("/getAllBoards") 
	  public List<Board> getAllBoard() 
	  {
	    return boardService.getAllBoards(); 
	  } 
	  
	  @DELETE
	  @Path("/{name}/{userID}")
	  public String deleteBoard(@PathParam("name") String name, @PathParam("userID") int userID)
	  {
		  return boardService.deleteBoard(name, userID);
	  }
	  
	  @PUT
	  @Path("/invite/{colabId}/{boardName}")
	  public String inviteUser(@PathParam("colabId") int colabId, @PathParam("boardName") String boardName)
	  {
		  return boardService.inviteUser(colabId, boardName);
	  }
	  
	  @PUT
	  @Path("/invitations/{colabId}/{boardName}/{response}")
	  public String acceptInvitation(@PathParam("colabId") int colabId, @PathParam("boardName") String boardName,  @PathParam("response") String response)
	  {
		  return boardService.acceptInvitation(colabId, boardName, response);
	  }
	  
	  
	 
	 


	
}
