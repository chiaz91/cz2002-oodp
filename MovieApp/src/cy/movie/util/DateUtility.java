package cy.movie.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cy.movie.AppConstants;
import cy.movie.Log;
import cy.movie.entity.Period;
import cy.movie.value.TimeUnit;

public class DateUtility implements AppConstants{	
	
	/**
	 * Helper method to validate whether a string is valid Date with regular expression.
 	 * <blockquote><pre>
	 * Format: dd/mm/yyyy
	 * dd   - 01~31
	 * mm   - 01~12
	 * yyyy - 0000~9999
     * </pre></blockquote>
     * However, it could not check number of days with respective month. Eg. February can vary between 28 or 29 days 
	 * 
	 * @param strDate - string value to be validate
	 * @return {@code boolean} for validity of Date
	 */
	public static boolean isValidDate(String strDate) {
        String regex = "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$"; 
        Pattern pattern = Pattern.compile(regex); 
        Matcher matcher = pattern.matcher(strDate); 
        return matcher.matches();
	}
	
	/**
	 * Helper method to validate whether a string is valid time with regular expression.
 	 * <blockquote><pre>
	 * Format: hh:mm
	 * hh - 0~23
	 * mm - 0~59
     * </pre></blockquote>
     * @param strTime - string value to be validate
	 * @return {@code boolean} for validity of Time
	 */
	public static boolean isValidTime(String strTime) {
        String regex = "^([0-1]\\d|2[0-3]):([0-5]\\d)$"; 
        Pattern pattern = Pattern.compile(regex); 
        Matcher matcher = pattern.matcher(strTime); 
        return matcher.matches();
	}
	
	/**
	 * Helper method to check if 2 Date object is on same date.<br>
	 * Hours and minutes will not be compared.
	 * @param date1
	 * @param date2
	 * @return boolean
	 */
	public static boolean isSameDate(Date date1, Date date2) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
		return sdf.format(date1).equals(sdf.format(date2));
	}
	
	/**
	 * Helper method to convert string into Date object with format provided.
	 * Default formats can be found in {@link AppConstants}
	 * @param format - format for converting to Date object
	 * @param strDate - formatted DateTime string for converting 
	 * @return {@code Date} 
	 * @see #formatDate(String, Date)
	 */
	public static Date parseDate(String format, String strDate) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(strDate);
		} catch (Exception e) {
			System.out.println("Error: error parsing date");
			System.out.println(String.format("Entered date: %s", strDate));			
			return null;
		}
	}
	
	/**
	 * Helper method to convert Date to String value with format provided
	 * Default formats can be found in {@link AppConstants}
	 * @param format - format for converting to String value
	 * @param date - Date object for formatting
	 * @return String
	 * @see #parseDate(String, String)
	 */
	public static String formatDate(String format, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	/**
	 * Helper method to get Date object of today's date.
	 * Date object will be starting of the date, i.e. hours, minutes and seconds will be set to 0
	 * @return Date
	 */
	public static Date getToday() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	public static Date getDate(int year, int month, int day, int hr, int min) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR_OF_DAY, hr);
		calendar.set(Calendar.MINUTE, min);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date getDate(int year, int month, int day) {
		return getDate(year, month, day, 0, 0);
	}
	
	/**
	 * Method to change value of existing Date time value
	 * returns updated Date value
	 * @param date - Date object to be modifying
	 * @param hr
	 * @param min
	 * @return Date
	 */
	public static Date changeTime(Date date, int hr, int min) {
//		Log.d(String.format("[Test] changeTime= date:%s, hr:%d, min:%d", formatDate(FORMAT_DATETIME_DETAIL, date), hr, min));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, hr);
		calendar.set(Calendar.MINUTE, min);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	
	
	public static double calDifference(Date start, Date end, TimeUnit unit) {
		Period p = new Period(start, end);
		return (1.0*p.getDurationMs())/unit.toMs();
	}
	
	public static ArrayList<Date> getListDates(Date start, int numDates){
		ArrayList<Date> dates = new ArrayList<Date>();
		dates.add(start);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);
		for (int i = 0; i < numDates-1; i++) {
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			dates.add(calendar.getTime());
		}
		return dates;
	}
}
