package cy.movie.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;


public class Movie implements Serializable, Comparable<Movie>{
	private String title;
	private String synopsis;
	private int durationMin;
	private Date dateRelease;
	private String status;
	private String director;
	private ArrayList<String> casts;
	private double overalRating;
	private ArrayList<Review> reviews; 
	private double totalSales;
	
	public Movie(String title, String synopsis, int durationMin, Date dateRelease, String status, String director,
			ArrayList<String> casts, double overalRating, ArrayList<Review> reviews, double totalSales) {
		super();
		this.title = title;
		this.synopsis = synopsis;
		this.durationMin = durationMin;
		this.dateRelease = dateRelease;
		this.status = status;
		this.director = director;
		this.casts = casts;
		this.overalRating = overalRating;
		this.reviews = reviews;
		this.totalSales = totalSales;
	}
	
	public Movie(String title, String synopsis, int durationMin, Date dateRelease, String status, String director,
			ArrayList<String> casts ) {
		this( title,  synopsis,  durationMin, dateRelease,  status,  director, casts, 0, new ArrayList<Review>(),  0);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public Date getDateRelease() {
		return dateRelease;
	}

	public void setDateRelease(Date dateRelease) {
		this.dateRelease = dateRelease;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public ArrayList<String> getCasts() {
		return casts;
	}

	public void setCasts(ArrayList<String> casts) {
		this.casts = casts;
	}

	public double getOveralRating() {
		return overalRating;
	}

	public void setOveralRating(double overalRating) {
		this.overalRating = overalRating;
	}

	public ArrayList<Review> getReviews() {
		return reviews;
	}

	public void setReviews(ArrayList<Review> reviews) {
		this.reviews = reviews;
	}

	public double getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(double totalSales) {
		this.totalSales = totalSales;
	}

	public int getDurationMin() {
		return durationMin;
	}

	public void setDurationMin(int durationMin) {
		this.durationMin = durationMin;
	}

	@Override
	public int compareTo(Movie other) {
		return Comparator.comparing(Movie::getTitle).thenComparing(Movie::getDateRelease).compare(this, other);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movie other = (Movie) obj;
		if (director == null) {
			if (other.director != null)
				return false;
		} else if (!director.equals(other.director))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
	
	

}
