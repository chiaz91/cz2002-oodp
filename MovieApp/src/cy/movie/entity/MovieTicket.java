package cy.movie.entity;

import java.io.Serializable;
import java.util.Comparator;

public class MovieTicket implements Serializable, Comparable<MovieTicket>{
	public static final int TYPE_BASE    = 1;
	public static final int TYPE_PEAK    = 2;
	public static final int TYPE_PH      = 3;
	public static final int TYPE_ELDERLY = 4;
	public static final int TYPE_STUDENT = 5;

	ShowTime showtime;
	int type;
	int row;
	int col;
	

	public MovieTicket(ShowTime showtime, int type, int row, int col) {
		super();
		this.showtime = showtime;
		this.type = type;
		this.row = row;
		this.col = col;
	}


	public ShowTime getShowtime() {
		return showtime;
	}

	public void setShowtime(ShowTime showtime) {
		this.showtime = showtime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}


	@Override
	public int compareTo(MovieTicket other) {
		return Comparator.comparing(MovieTicket::getShowtime).compare(this, other);
	}

}
