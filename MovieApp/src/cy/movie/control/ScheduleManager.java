package cy.movie.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import cy.movie.AppConstants;
import cy.movie.Log;
import cy.movie.entity.Cinema;
import cy.movie.entity.Movie;
import cy.movie.entity.ShowTime;
import cy.movie.util.DateUtility;

public class ScheduleManager implements Serializable{
	HashMap<String, ArrayList<ShowTime>> data;
	
	public ScheduleManager() {
		data = new HashMap<String, ArrayList<ShowTime>>();
	}
	
	
	
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
//		Log.d("addShowTime::"+canAdd+", "+adding.toString());
		return canAdd;
	}

	
	public ArrayList<ShowTime> getShowTimes(Date date){
		String strDate = DateUtility.formatDate(AppConstants.FORMAT_DATE, date);
		ArrayList<ShowTime> showtimes = data.get(strDate);
		ArrayList<ShowTime> result = new ArrayList<ShowTime>();
		if (showtimes!=null) {
			result.addAll(showtimes);
		}
		return result;
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
	
//	public void clearExpired() {
//		Date today = DateUtility.getToday();
//		Date other;
//		for (String strDate : data.keySet()) {
//			other = DateUtility.parseDate(AppConstants.FORMAT_DATE, strDate);
//			if (other.before(today)) {
//				data.remove(strDate);
//			}
//		}
//
//		// remove today
//		ArrayList<ShowTime> showtimes = getShowTimes(today);
//		if (showtimes != null) {
//			ShowTime st;
//			for (int i = showtimes.size()-1; i >= 0; i--) {
//				st = showtimes.get(i);
//				if (st.getPeriod().getStart().before(today)) {
//					showtimes.remove(i);
//				}
//			}
//		}		
//	}
	
//	public void debug() {
//		Set<String> keys = data.keySet();
//		for (String key : keys) {
//			ArrayList<ShowTime> sts = data.get(key);
//			if (sts==null) {
//				Log.d("ScheduleMgr.debug::"+key+", data=null");
//			}else if (sts.size()==0) {
//				Log.d("ScheduleMgr.debug::"+key+", data.size = 0");
//			} else {
//				StringBuilder sb = new StringBuilder();
//				for (ShowTime st : sts) {
//					sb.append(st+"\n");
//				}
//
//				Log.d("ScheduleMgr.debug::"+key+", data.size = \n"+sb.toString());
//			}
//			
//		}
//	}



	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Set<String> keys = data.keySet();
		int i=0;
		for (String key : keys) {
			if (i>0) {
				sb.append(", ");
			}
			ArrayList<ShowTime> st = data.get(key);
			if (st == null) {
				sb.append(key+":null");
			} else {
				sb.append(key+":"+st.size());
			}
			i++;
		}
		return sb.toString();
	}
	
	

}
 