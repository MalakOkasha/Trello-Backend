package Service;

import java.util.List;

import javax.ejb.Stateless;

//import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.websocket.server.PathParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import Model.*;

@Stateless
public class ListService {
	
	
	  @PersistenceContext(unitName="trello")
      private EntityManager entityManager;
	
	
	
	/*public String createList(ListC list, String boardName)
	{	
			Board board=entityManager.find(Board.class, boardName);  //always returns null lehh!!
			
			if(board!=null) 
			{
				    list.setBoard(board);
				    
					if(entityManager.find(ListC.class, list.getId())==null)
					{
						board.setList(list);
						entityManager.persist(list);
						
						List<ListC> lists =board.getAllLists();
						lists.add(list);
						board.setLists(lists);
						
						entityManager.merge(board);
						return "list created sucessfully";
					}
					else
					{
						return "List type already exists!";
					}
			}
			else
			{			
				return "BOARD NOT FOUND!!";
			} 
	}*/
	
	/////////////////////////////////////////////////////////////////////////
	
	  public String createList(ListC list, String boardName)  //shaghal bas ghalat
		{
			
			Board board = new Board();
			board.setName(boardName);
			entityManager.persist(board);
			
			if(board!=null) 
			{
				    list.setBoard(board);
				    
						board.setList(list);
						entityManager.persist(list);
						
						List<ListC> lists =board.getAllLists();
						lists.add(list);
						board.setLists(lists);
						
						entityManager.merge(board);
						return "list created sucessfully :)";
			}
			else
				return "Board is not found!";

		}
	  
	 /* public String createListt(ListC list, String boardName) 
	  {
		     Board board = list.getBoard();
		    if(board.getName().equals(boardName))
		    
		    	 list.setBoard(board);
		    
			if(entityManager.find(ListC.class, list.getType())==null)
			{
				board.setList(list);
				entityManager.persist(list);
				
				List<ListC> lists =board.getAllLists();
				lists.add(list);
				board.setLists(lists);
				
				entityManager.merge(board);
				return "list created sucessfully";
			}
			else
			{
				return "List type already exists!";
			}
	}
	else
	{			
		return "BOARD NOT FOUND";
	} 
		}*/

	
	/*public String deleteList(String boardName, int listId) 
	{
        Board board = entityManager.find(Board.class, boardName);
        ListC list = board.getAllLists().get(0);
            if (board.getAllLists().get(0).getId()==listId&&board !=null) 
            {
                entityManager.remove(list);
                return"list deleted successfully";
            } else 
            {
                throw new NotFoundException("List not found");
            }
    }*/
	
	public String deleteList(int listId) 
	{
        
		ListC list =entityManager.find(ListC.class, listId);
            if (list != null) 
            {
                entityManager.remove(list);
                return"list deleted successfully";
            } else 
            {
                return "List not found!";
            }
    }



}

