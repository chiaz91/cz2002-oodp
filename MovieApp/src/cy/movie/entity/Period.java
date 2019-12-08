package cy.movie.entity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

import cy.movie.AppConstants;
import cy.movie.util.DateUtility;

public class Period implements Comparable<Period>, Serializable{
	private Date start;
	private Date end;

	
	public Period(Date start, Date end) {
		if (end.before(start)) {
			throw new IllegalArgumentException("End date before Start date");
		}
		this.start = start;
		this.end = end;
	}
	
	public Period(Date start, long durationMs) {
		this(start, new Date(start.getTime()+durationMs));
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
	
	public boolean isWithin(Date other) {
		return other.after(start) && other.before(end);
	}
	
	public long getDurationMs() {
		return end.getTime()-start.getTime();
	}

	@Override
	public int compareTo(Period other) {
		return Comparator.comparing(Period::getStart)
				.thenComparing(Period::getEnd)
				.compare(this, other);
	}

	@Override
	public String toString() {
		return "Period [" + DateUtility.formatDate(AppConstants.FORMAT_DATETIME, start) + " - " + DateUtility.formatDate(AppConstants.FORMAT_DATETIME, end) + "]";
	}
	
	
}
