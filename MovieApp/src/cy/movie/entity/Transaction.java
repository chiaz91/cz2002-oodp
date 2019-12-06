package cy.movie.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Transaction implements Serializable {
	// transaction id (TID) - format XXXYYYYMMDDhhmm (XXX : cinema code in letters)
	private ArrayList<MovieTicket> tickets;
	private double price;
	
	public Transaction(ArrayList<MovieTicket> tickets, double price) {
		super();
		this.tickets = tickets;
		this.price = price;
	}
	public ArrayList<MovieTicket> getTickets() {
		return tickets;
	}
	public void setTickets(ArrayList<MovieTicket> tickets) {
		this.tickets = tickets;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

}
