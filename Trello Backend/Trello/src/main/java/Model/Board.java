package Model;

import java.util.ArrayList;
import java.util.List;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Stateless
@Entity
public class Board{
	
	@Id
	String name;

	 @ManyToMany(fetch = FetchType.LAZY)
	 @JoinTable(
		        name = "collaborator", // Name of the join table
		        joinColumns = @JoinColumn(name = "name"), // Column in the join table referencing the board
		        inverseJoinColumns = @JoinColumn(name = "id") // Column in the join table referencing the user
		    )
    List<User> users;
	
	 @OneToMany(mappedBy = "board", fetch = FetchType.EAGER) // One board can have many lists
	 @JsonManagedReference
	 private List<ListC> lists; 
	 
	
	public Board() {
    }
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<ListC> getAllLists() {
		return lists;
	}

	public void setList(ListC list) {
        if (lists == null) {
            lists = new ArrayList<>();
        }
        lists.add(list);
    }

	public void setLists(List<ListC> lists) {
		this.lists = lists;
	}
	
	

}
