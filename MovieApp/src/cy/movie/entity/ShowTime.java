package cy.movie.entity;

import java.io.Serializable;
import java.util.Arrays;

public class ShowTime implements Serializable, Comparable<ShowTime>{
	private Movie movie;
	private MovieType type;
	private Cinema cinema;
	private Period period;
	private boolean isPeak;
	private boolean[][] seatAvailability;
	
	
	public ShowTime(Movie movie, MovieType type, Cinema cinema, Period period) {
		super();
		this.movie = movie;
		this.type = type;
		this.cinema = cinema;
		this.period = period;
		seatAvailability = new boolean[cinema.getRows()][cinema.getCols()];
		for (int r = 0; r < seatAvailability.length; r++) {
			for (int c = 0; c < seatAvailability[r].length; c++) {
				seatAvailability[r][c] = true;
			}
		}
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

	public boolean[][] getSeatAvailability() {
		return seatAvailability;
	}

	public void setSeatAvailability(boolean[][] seatAvailability) {
		this.seatAvailability = seatAvailability;
	}
	
	
	public String getSeatAvailabilityString() {
		StringBuilder sb = new StringBuilder();
		for (int r = 0; r < cinema.getRows(); r++) {
			char label = (char)('A'+r);
			sb.append(label+" ");
			for (int c = 0; c < cinema.getCols(); c++) {
				if (c==0) {
					sb.append("[");
				} else if (cinema.getStairways().contains(c)){
					sb.append("]  [");
				} else {
					sb.append(",");
				}
				
				if (seatAvailability[r][c]) {
					sb.append((c+1));
				}else {
					sb.append("X");
				}
				
				if (c==cinema.getCols()-1) {
					sb.append("]");
				}
			}
			sb.append(" "+label+"\n");
		}
		return sb.toString();
	}


	@Override
	public int compareTo(ShowTime other) {
		return this.period.compareTo(other.getPeriod());
	}


	@Override
	public String toString() {
		return "ShowTime [" + movie + ", " + cinema + ", " + period + "]";
	}

	


	

}
