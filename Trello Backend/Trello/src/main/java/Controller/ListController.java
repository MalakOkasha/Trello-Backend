package Controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import Model.ListC;
import Service.ListService;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/list")
public class ListController {
	
		@EJB
		ListService listService;
		
		@POST
		@Path("/createList/{boardName}")
		public String createList(ListC list, @PathParam("boardName") String boardName)
		{
	
			return listService.createList(list, boardName);
		}
		
		////////////////////////////////////////////////////
		/*@DELETE
		@Path("/deleteList/{boardName}/{listId}")
		public String deleteList(@PathParam("boardName") String boardName, @PathParam("listId") int listId) 
		{
			return listService.deleteList(boardName, listId);
	    }*/
		@DELETE
		@Path("/deleteList/{listId}")
		public String deleteList( @PathParam("listId") int listId) 
		{
			return listService.deleteList(listId);
	    }
}

