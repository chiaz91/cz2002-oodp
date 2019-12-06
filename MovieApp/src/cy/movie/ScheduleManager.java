package cy.movie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import cy.movie.entity.Cinema;
import cy.movie.entity.Movie;
import cy.movie.entity.ShowTime;
import cy.movie.util.DateUtility;

public class ScheduleManager implements Serializable{
	HashMap<String, ArrayList<ShowTime>> data;
	
	
	
	private boolean canAddShowtime(ShowTime adding) {
		ArrayList<ShowTime> showtimes = getShowTimes(adding.getPeriod().getStart(), adding.getCinema());
		boolean canAdd = true;
		if (showtimes != null) {
			for (ShowTime other: showtimes) {
				if (other.getPeriod().isWithin(adding.getPeriod().getStart()) || other.getPeriod().isWithin(adding.getPeriod().getEnd())) {
					canAdd = false;
					break;
				}
			}	
		}
		return canAdd;
	}
	
	public boolean addShowTime(ShowTime adding) {
		boolean canAdd = canAddShowtime(adding);
		if (canAdd) {
			String strDate = DateUtility.formatDate(AppConstants.FORMAT_DATE, adding.getPeriod().getStart());
			ArrayList<ShowTime> showtimes = data.get(strDate);
			if (showtimes == null) {
				showtimes = new ArrayList<ShowTime>();
			}
			showtimes.add(adding);
			Collections.sort(showtimes);
			data.put(strDate, showtimes);
		}
		return canAdd;
	}

	
	public ArrayList<ShowTime> getShowTimes(Date date){
		String strDate = DateUtility.formatDate(AppConstants.FORMAT_DATE, date);
		return data.get(strDate);
	}
	
	public ArrayList<ShowTime> getShowTimes(Date date, String keyword){
		String lowerKey = keyword.toLowerCase();
		ArrayList<ShowTime> showtimes = getShowTimes(date);
		if (showtimes != null) {
			Movie movie;
			for (int i = showtimes.size()-1; i >= 0; i--) {
				movie = showtimes.get(i).getMovie();
				boolean keep = false;
				
				if (movie.getTitle().toLowerCase().contains(lowerKey)) { // check title
					keep = true;
				} else if (movie.getDirector().toLowerCase().contains(lowerKey)) { // check director
					keep = true;
				} else { // check cast
					for (String cast : movie.getCasts()) {
						if (cast.toLowerCase().contains(lowerKey)) {
							keep = true;
							break;
						}
					}
				}
				
				if (!keep) {
					showtimes.remove(i);
				}
			}
		}
		return showtimes;
		
	}
	
	public ArrayList<ShowTime> getShowTimes(Date date, Movie movie){
		ArrayList<ShowTime> showtimes = getShowTimes(date);
		if (showtimes != null) {
			ShowTime st;
			for (int i = showtimes.size()-1; i >= 0; i--) {
				st = showtimes.get(i);
				if (!st.getMovie().equals(movie)) {
					showtimes.remove(i);
				}
			}
		}
		return showtimes;
	}
	
	public ArrayList<ShowTime> getShowTimes(Date date, Cinema cinema){
		ArrayList<ShowTime> showtimes = getShowTimes(date);
		if (showtimes != null) {
			ShowTime st;
			for (int i = showtimes.size()-1; i >= 0; i--) {
				st = showtimes.get(i);
				if (!st.getCinema().equals(cinema)) {
					showtimes.remove(i);
				}
			}
		}
		return showtimes;
	}
	
	public void clearExpired() {
		Date today = DateUtility.getToday();
		Date other;
		for (String strDate : data.keySet()) {
			other = DateUtility.parseDate(AppConstants.FORMAT_DATE, strDate);
			if (other.before(today)) {
				data.remove(strDate);
			}
		}

		// remove today
		ArrayList<ShowTime> showtimes = getShowTimes(today);
		if (showtimes != null) {
			ShowTime st;
			for (int i = showtimes.size()-1; i >= 0; i--) {
				st = showtimes.get(i);
				if (st.getPeriod().getStart().before(today)) {
					showtimes.remove(i);
				}
			}
		}		
	}

}
 