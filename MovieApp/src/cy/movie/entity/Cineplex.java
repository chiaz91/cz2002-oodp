package cy.movie.entity;

import java.io.Serializable;
import java.util.ArrayList;

import cy.movie.ScheduleManager;

public class Cineplex implements Serializable{
	public static final int NUM_MIN_CINEMAS = 2;
	String name;
	String location;
	ArrayList<Cinema> cinemas; 
	ScheduleManager scheduleMgr;
	
	public Cineplex(String name, String location, ArrayList<Cinema> cinemas) {
		if (cinemas.size() < NUM_MIN_CINEMAS) {
			throw new IllegalArgumentException("provide at least 2 cinemas");
		}
		this.name = name;
		this.location = location;
		this.cinemas = cinemas;
		this.scheduleMgr = new ScheduleManager();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public ArrayList<Cinema> getCinemas() {
		return cinemas;
	}

	public void setCinemas(ArrayList<Cinema> cinemas) {
		this.cinemas = cinemas;
	}

	public ScheduleManager getScheduleManager() {
		return scheduleMgr;
	}



}
