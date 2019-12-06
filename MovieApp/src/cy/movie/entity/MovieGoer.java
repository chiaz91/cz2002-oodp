package cy.movie.entity;

import java.util.ArrayList;

public class MovieGoer extends User{
	ArrayList<Transaction> transactions;
	
	public MovieGoer(String userName, String password, ArrayList<Transaction> transactions) {
		super(userName, password);
		this.transactions = transactions;
	}
	
	
	
}
