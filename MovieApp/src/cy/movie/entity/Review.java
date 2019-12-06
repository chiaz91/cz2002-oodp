package cy.movie.entity;

import java.io.Serializable;
import java.util.Date;

public class Review implements Serializable{
	int rating;
	String comment;
	User reviewer;
	Date created;
	
	public Review(User reviewer, int rating, String comment ) {
		this.reviewer = reviewer;
		this.rating = rating;
		this.comment = comment;
		this.created = new Date();
	}
	
	public User getReviewer() {
		return reviewer;
	}
	
	public void setReviewer(User reviewer) {
		this.reviewer = reviewer;
	}
	
	
	public int getRating() {
		return rating;
	}
	
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
}
