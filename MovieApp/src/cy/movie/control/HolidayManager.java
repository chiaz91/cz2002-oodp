package cy.movie.control;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import cy.movie.entity.Holiday;
import cy.movie.util.DateUtility;
import cy.movie.value.TimeUnit;

public class HolidayManager {
	HashMap<Integer, ArrayList<Holiday>> holidays;
	
	public HolidayManager() {
		holidays = new HashMap<Integer, ArrayList<Holiday>>();
	}
	
	public HashMap<Integer, ArrayList<Holiday>> getHolidays() {
		return holidays;
	}

	public void setHolidays(HashMap<Integer, ArrayList<Holiday>> holidays) {
		this.holidays = holidays;
	}

	private ArrayList<Holiday> createHolidaysForYear(int year) {
		ArrayList<Holiday> list = new ArrayList<Holiday>();
		Date newyear = DateUtility.getDate(year, 1, 1);
		Date labour = DateUtility.getDate(year, 5, 1);
		Date national = DateUtility.getDate(year, 8, 9);
		Date xmas = DateUtility.getDate(year, 12, 25);
		list.add(new Holiday("New year", newyear));
		list.add(new Holiday("Labour Day", labour));
		list.add(new Holiday("National Day", national));
		list.add(new Holiday("Christmas Day", xmas));
		return list;
	}
	
	public ArrayList<Holiday> getHolidays(int year) {
		ArrayList<Holiday> list = holidays.get(year);
		if (list == null) {
			list = createHolidaysForYear(year);
			holidays.put(year, list);
		}
		return list;
	}
	
	public boolean isHoliday(Date date) {
		// get year
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		
		// search ph or ph eve
		ArrayList<Holiday> list = holidays.get(year);
		boolean isHoliday = false;
		for (Holiday ph : list) {
			// check holiday
			if(ph.getPeriod().isWithin(date)) {
				isHoliday = true;
				break;
			}
			// check eve
			long diff = ph.getPeriod().getStart().getTime() - date.getTime();
			if (diff>0 && diff<TimeUnit.DAY.toMs()) {
				isHoliday = true;
				break;
			}
		}
		return isHoliday;
	}
	

	// check expired?

}
