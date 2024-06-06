package Controller;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.stream.events.Comment;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import Model.Card;
import Service.CardService;
import Model.*;

@Path("/card")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CardController
{
    @EJB
    CardService cardService;

    @POST
    @Path("/createnewcard")
    public String createNewCard(Card card, @QueryParam("listId") int listId, @QueryParam("userId") int userId) 
    {
        return cardService.createCard(card, listId, userId);
    }

    @PUT
    @Path("/{cardId}/moveCardToAnotherList/{targetListId}")
    public String moveCardToAnotherList(@PathParam("cardId") int cardId, @PathParam("targetListId") int targetListId) 
    {
        return cardService.moveCardToList(cardId, targetListId);
    }

    @PUT
    @Path("/{cardId}/assigncardToUser/{assigneeId}")
    public String assignCardToUser(@PathParam("cardId") int cardId, @PathParam("assigneeId") int assigneeId)
    {
        return cardService.assignCardToUser(cardId, assigneeId);
    }

    @PUT
    @Path("/{cardId}/updateDescription")
    public String updateDescriptionToCard(@PathParam("cardId") int cardId, @QueryParam("description") String description) {
        return cardService.UpdateDescriptionToCard(cardId, description);
    }
    
    @PUT
    @Path("/{cardId}/addDescription")
    public String  addDescriptionToCard(@PathParam("cardId") int cardId, @QueryParam("description") String description) {
        return cardService.addDescriptionToCard(cardId, description);
    }
    
    /*
    @PUT
    @Path("/{cardId}/addComment")
    public String addCommentToCard(@PathParam("cardId") int cardId, Comment comment) {
        return cardService.addCommentToCard(cardId,comment);
    }*/
    @PUT
    @Path("/{cardId}/addComment")
    public String addCommentToCard(@PathParam("cardId") int cardId, Model.Comment comment) {
        return cardService.addCommentToCard(cardId, comment);
    }
}