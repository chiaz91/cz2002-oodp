package cy.movie.entity;

import java.util.ArrayList;

public class MovieGoer extends User{
	ArrayList<Transaction> transactions;
	
	public MovieGoer(String userName, String password, String name, ArrayList<Transaction> transactions) {
		super(userName, password, name);
		this.transactions = transactions;
	}
	
	public MovieGoer(String userName, String password, String name) {
		this(userName, password, name, new ArrayList<Transaction>());
	}
	
	
	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(ArrayList<Transaction> transactions) {
		this.transactions = transactions;
	}

	
}
