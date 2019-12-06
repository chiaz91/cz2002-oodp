package cy.movie.entity;

import java.io.Serializable;

public class ShowTime implements Serializable, Comparable<ShowTime>{
	private Movie movie;
	private MovieType type;
	private Cinema cinema;
	private Period period;
	private boolean isPeak;
	private boolean[][] SeatAvability;
	
	
	public ShowTime(Movie movie, MovieType type, Cinema cinema, Period period) {
		super();
		this.movie = movie;
		this.type = type;
		this.cinema = cinema;
		this.period = period;
		SeatAvability = new boolean[cinema.getRows()][cinema.getCols()];
	}


	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public MovieType getType() {
		return type;
	}

	public void setType(MovieType type) {
		this.type = type;
	}

	public Cinema getCinema() {
		return cinema;
	}

	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public boolean isPeak() {
		return isPeak;
	}

	public void setPeak(boolean isPeak) {
		this.isPeak = isPeak;
	}

	public boolean[][] getSeatAvability() {
		return SeatAvability;
	}

	public void setSeatAvability(boolean[][] seatAvability) {
		SeatAvability = seatAvability;
	}
	


	@Override
	public int compareTo(ShowTime other) {
		return this.period.compareTo(other.getPeriod());
	}

}
