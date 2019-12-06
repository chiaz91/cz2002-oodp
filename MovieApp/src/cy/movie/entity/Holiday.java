package cy.movie.entity;

import java.io.Serializable;
import java.util.Date;

import cy.movie.value.TimeUnit;

public class Holiday implements Comparable<Holiday>, Serializable{
	String name;
	Period period;
	
	public Holiday(String name, Period period) {
		super();
		this.name = name;
		this.period = period;
	}
	
	public Holiday(String name, Date date, long durationMs) {
		this(name, new Period(date, durationMs-1));
	}
	
	public Holiday(String name, Date date) {
		this(name, new Period(date, TimeUnit.DAY.toMs()-1));
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Period getPeriod() {
		return period;
	}
	public void setPeriod(Period period) {
		this.period = period;
	}
	
	@Override
	public int compareTo(Holiday other) {
		return this.getPeriod().compareTo(other.getPeriod());
	}
}
