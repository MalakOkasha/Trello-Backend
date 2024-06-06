package Model;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Stateless
@Entity
public class ListC {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String type;
	
	 @JsonManagedReference
	 @OneToMany(mappedBy = "list", fetch = FetchType.EAGER)
	 List<Card>card;
	
	 @ManyToOne // Many lists can belong to one board
	 @JoinColumn(name = "name") // Name of the foreign key column in the List table
	 @JsonBackReference
	 Board board;
	 
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Board getBoard() {
		return board;
	}
	public void setBoard(Board board) {
		this.board = board;
	}
	
	
}
