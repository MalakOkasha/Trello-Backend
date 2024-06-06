package Service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import Messaging.JMSClient;
import Model.Card;
import Model.Comment;
import Model.ListC;
import Model.User;

@Stateless
public class CardService
{

	@Inject
	JMSClient jmsUtil;
    @PersistenceContext(unitName = "trello")
    EntityManager entityManager;
    
    //a.Users can create cards within a list to represent individual tasks.
    public String createCard(Card cardObjCreated, int ID_Of_List, int ID_Of_User)
    {
        User userObj;
        ListC listObj;

        listObj = entityManager.find(ListC.class, ID_Of_List);            
        userObj = entityManager.find(User.class, ID_Of_User);

        if (listObj == null)
        {
            if (userObj == null)
            {
               return("Error encountered! ..List and User are not found!");
            } 
            else
            {
                return("Error encountered! ..List is not found!");
            }
        } 
        else if (userObj == null)
        {
            return("Error encountered! ..User is not found!");
        } 
        else 
         {
        	cardObjCreated.setList(listObj);
            cardObjCreated.setUser(userObj);
            entityManager.persist(cardObjCreated);
            return "Card created successfully!";
        }
    }


   // b. Users can move cards between lists.
    public String moveCardToList(int cardId, int anotherListId)
    {
        Card cardObj;
        ListC anotherList;
        
        cardObj= entityManager.find(Card.class, cardId);
        anotherList = entityManager.find(ListC.class, anotherListId);
        
        if (cardObj == null)
        {
            if (anotherList == null)
            {
            	return "Error encountered! ..card and the other list are not found!";
            } 
            else
            {
            	return "Error encountered! ..card is not found!";
            }
        } 
        else if (anotherList == null)
        {
        	return "Error encountered! ..list is not found!";
        } 
        else 
         {
        	cardObj.setList(anotherList);
            entityManager.merge(cardObj);     
            return "Card moved successfully to the other list!";
        }

    }
     //c. Users can assign cards to themselves or other collaborators.
    public String assignCardToUser(int cardUsed_Id, int userToBeAssigned_Id) 
    {
        Card cardObject;
        User AssignedUser;
        cardObject= entityManager.find(Card.class, cardUsed_Id); //finding the card with that specific card id.
        AssignedUser= entityManager.find(User.class, userToBeAssigned_Id); //finding the user with that specific user id.
        
        if (cardObject == null)
        {
            if (AssignedUser == null)
            {
                return("Error encountered! ..the card and the user to be assigned are not found!");
            } 
            else
            {
                return ("Error encountered! ..card is not found!");
            }
        } 
        else if (AssignedUser == null)
        {
            return("Error encountered! ..user to be assigned is not found!");
        } 
        else 
         {
        	cardObject.setUser(AssignedUser); 
            entityManager.merge(cardObject);
            jmsUtil.sendMessage("Card "+cardObject.getName()+" has been assigned to " +  AssignedUser.getName());
            return "User has been assigned to the card successfully! ";
         }

    }
    public String addDescriptionToCard(int cardUsedId, String descriptionToBeAdded) 
    {
        Card cardObject;
        cardObject= entityManager.find(Card.class, cardUsedId);    //Find the card object by its ID.
        if (cardObject == null) //Check if the card object does not exists.
        {
        	return "Error encountered! ..Card not found"; //throws exception with prompt error message.
        } 
        else //Check if the card object exists.
        {
        	cardObject.setDescription(descriptionToBeAdded);  //setting the description that the user needs to add.
            entityManager.merge(cardObject);   //If the card exists, update its description.
            jmsUtil.sendMessage("Card "+cardObject.getName()+" has been added and here's the description: " +  descriptionToBeAdded );
            return "Description added to card successfully! ";  //prompting to the user that description has been added.
            
        }
    }
    
    //d. Users can add descriptions to cards.
    public String UpdateDescriptionToCard(int cardUsedId, String descriptionToBeAdded) 
    {
        Card cardObject;
        cardObject= entityManager.find(Card.class, cardUsedId);    //Find the card object by its ID.
        if (cardObject == null) //Check if the card object does not exists.
        {
        	return "Error encountered! ..Card not found"; //throws exception with prompt error message.
        } 
        else //Check if the card object exists.
        {
        	cardObject.setDescription(descriptionToBeAdded);  //setting the description that the user needs to add.
            entityManager.merge(cardObject);   //If the card exists, update its description.
            jmsUtil.sendMessage("Card "+cardObject.getName()+" has been updated here's the new description: " +  descriptionToBeAdded );
            return "Description added to card successfully! ";  //prompting to the user that description has been added.
            
        }
    }

    //d. Users can add comments to cards.
   
    /*public String addCommentToCard(int cardUsedId, String commentToBeAdded) 
    {
        Card cardObject;
        List<String>userNames;
        cardObject= entityManager.find(Card.class, cardUsedId);        //Find the card object by its ID.
        if (cardObject == null)  //Check if the card object does not exists.
        {
        	return ("Card not found");
        	
        } 
        else  //Check if the card object exists.
        {
        	cardObject.(commentToBeAdded); //setting the comment that the user needs to add.
            entityManager.merge(cardObject);    //If the card exists, update its comment.
            List<Comment>comments=cardObject.getComments();
            
            for (Comment comment : comments) {
                String userName = comment.getUsername();
                userNames.add(userName);
            }
            for (int i = 0; i < userNames.size(); i++) 
            {
                String username = userNames.get(i);
                jmsUtil.sendMessage(username);
            }
            return " Comment added to card successfully!  ";  //prompting to the user that comment has been added.
        }
        
    }*/
    /*
    public String addCommentToCard(int cardUsedId, Comment commentToBeAdded) {
        Card cardObject;
        List<String> userNames = new ArrayList<>(); // Initialize userNames list

        cardObject = entityManager.find(Card.class, cardUsedId); // Find the card object by its ID.

        if (cardObject == null) {
            return "Card not found";
        } else {
            cardObject.setComment(commentToBeAdded); // Setting the comment that the user needs to add.
            entityManager.merge(cardObject); // If the card exists, update its comment.
            List<Comment> comments = cardObject.getComments();

            for (Comment comment : comments) {
                String userName = comment.getUsername();
                userNames.add(userName);
            }

            for (int i = 0; i < userNames.size(); i++) {
                String username = userNames.get(i);
                jmsUtil.sendMessage(username);
            }
            return "Comment added to card successfully!";
        }
    }*/
    public String addCommentToCard(int cardUsedId, Comment commentToBeAdded) 
    {
        Card cardObject;
        List<String> userNames = new ArrayList<>(); // Initialize userNames list

        cardObject = entityManager.find(Card.class, cardUsedId); // Find the card object by its ID.

        if (cardObject == null) {
            return "Card not found";
        } else {
            cardObject.setComment(commentToBeAdded); // Adding the comment to the card using setComment method.
            entityManager.merge(cardObject); // If the card exists, update its comment.
            List<Comment> comments = cardObject.getComments();

            for (Comment comment : comments) {
                String userName = comment.getUsername();
                userNames.add(userName);
            }

            for (int i = 0; i < userNames.size(); i++) {
                String username = userNames.get(i);
                jmsUtil.sendMessage("there is a new comment "+commentToBeAdded.getComment() +username);
            }

            return "Comment added to card successfully!";
        }
    }
    

 
}