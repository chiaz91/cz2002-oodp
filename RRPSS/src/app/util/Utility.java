package app.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.AppConstants;
/**
 * Utility class provides helper methods for following purposes
 * <ul>
 * <li>String operations</li>
 * <li>Getting user input with condition checking</li>
 * <li>Date related operation and validation</li>
 * </ul>
 */
public class Utility implements AppConstants {
	private static Scanner sc = new Scanner(System.in);
	/**
	 * Helper method to ensure double value is round to 2 decimal points
	 * @param number
	 * @return rounded number
	 */
	public static double round(double number) {
		return Math.round(number*100.0)/100.0;
	}
	/**
	 * Capitalize the first letter of the string input
	 * Return the updated string with capitalized first letter 
	 * @param string
	 * @return capitalized string
	 */
	public static String capitalize(String string) {
		if (string == null) {
			return null;
		}
		if (string.length() == 0) {
			return string;
		}
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
	}
	/**
	 * Helper method to shorten text using an ellipsis (...) if string exceeded max characters limit 
	 * @param string
	 * @param max - maximum number of characters allowed
	 * @return modified string
	 */
	public static String ellipsize(String string, int max) {
		if (string.length() <= max) {
			return string;
		} else {
			return string.substring(0, max-3)+"...";
		}
	}
	/**
	 * Helper method to collect integer data from user.
	 * It prevents user from providing invalid value by re-prompt hint message to re-collect data. 
	 * @param hint - display message for getting input 
	 * @return  {@code int} of user input
	 * @see #inputInt(String, int, int)
	 * @see #inputIntAtLeast(String, int)
	 */
	public static int inputInt(String hint) {
		int input;
		while(true) {
			try {
				System.out.print(hint);
				input = sc.nextInt();
				sc.nextLine();
				return input;
			} catch (Exception e) {
				sc.nextLine();
			}
		}
	}
	/**
	 * Helper method to collect integer data from user that is within a range.
	 * @param hint - display message for getting input
	 * @param min - minimum value for user input
	 * @param max - maximum value for user input
	 * @return {@code int} of user input
	 * @see #inputInt(String)
	 */
	public static int inputInt(String hint, int min, int max) {
		if (max < min) {
//			throw new Exception("max is less than min.");
			System.out.println("Invalid(max < min)");
			System.out.println(String.format("Error: invalid argument! max(%d) < min(%d)", max, min));
			return -1;
		}
		
		int input;
		do {
			input = inputInt(hint);
		} while (input<min || input>max);
		return input;
	}
	/**
	 * Helper method to collect integer data from user that is above minimum value provided
	 * @param hint - display message for getting input
	 * @param min - minimum value for user input
	 * @return {@code int} of user input
	 * @see #inputInt(String)
	 */
	public static int inputIntAtLeast(String hint, int min) {
		int input;
		do {
			input = inputInt(hint);
		} while (input<min);
		return input;
	}
	/**
	 * Helper method to collect decimal data from user.
	 * It prevents user from providing invalid value by re-prompt hint message to re-collect data. 
	 * @param hint - display message for getting input
	 * @return {@code double} of user input
	 * @see #inputDoubleAtLeast(String, double)
	 */
	public static double inputDouble(String hint) {
		double input;
		while(true) {
			try {
				System.out.print(hint);
				input = sc.nextDouble();
				sc.nextLine();
				return input;
			} catch (Exception e) {
				sc.nextLine();
			}
		}
	}
	/**
	 * Helper method to collect decimal data that is above minimum value provided
	 * @param hint - display message for getting input
	 * @param min- minimum value for user input
	 * @return  {@code String} of user input
	 * @see #inputDouble(String)
	 */
	public static double inputDoubleAtLeast(String hint, double min) {
		double input;
		do {
			input = inputDouble(hint);
		} while (input<min);
		return input;
	}
	/**
	 * Helper method to collect String data from user.
	 * It prevents user from entering empty string by re-prompt hint message to re-collect data. 
	 * @param hint - display message for getting input
	 * @return  {@code String} of user input
	 * @see #inputString(String, int)
	 */
	public static String inputString(String hint) {
		String input;
		do {
			System.out.print(hint);
			input = sc.nextLine();
		} while (input.length() == 0);
		return input;
	}
	/**
	 * Helper method to collect String data with restriction on number of characters
	 * @param hint - display message for getting input
	 * @param length - max number of characters user can enter
	 * @return  {@code String} of user input
	 * @see #inputString(String)
	 */
	public static String inputString(String hint, int length) {
		if (length<1) {
//			throw new Exception("length should be at least 1");
			System.out.println(String.format("Error: Invalid length(%d) < 1", length));
			return null;
		}
		String input;
		do {
			input = inputString(hint);
		} while (input.length()>length);
		return input;
	}
	/**
	 * Helper method to collect boolean data from user.
	 * It prevents user from entering empty string by re-prompt hint message to re-collect data. 
	 * Examples:
     * <blockquote><pre>
     * true: "y", "yes or "true"
     * false: "n", "no" or "false"
     * </pre></blockquote>
	 * @param hint - display message for getting input
	 * @return {@code boolean} of user input 
	 */
	public static boolean inputBoolean(String hint) {
		String input = null;
		while(true) {
			input = inputString(hint);
			input = input.trim().toLowerCase();
			if (input.equals("y") || input.equals("yes") || input.equals("true")) {
				return true;
			} else if (input.equals("n") || input.equals("no") || input.equals("false")) {
				return false;
			}
		}
	}
	/**
	 * Helper method to collect Date data from user. 
	 * It will perform following validations before date object is return
	 * <ol>
	 * <li>user input is shortcut provided</li>
 	 * <li>validate input with regular expression</li> 
	 * <li>attempt to convert to date object and compare with input if it is correctly converted</li>
	 * </ol>
	 * Example:
	 * <blockquote><pre>
	 * user input "29/02/2019" might be converted to "01/03/2019"
     * </pre></blockquote>
	 * @param hint - display message for getting input
	 * @return {@code Date} of user input 
	 * @see #getDateByShortcuts(String)
	 * @see #isValidDate(String)
	 */
	public static Date inputDate(String hint) {
		String input;
		Date date;
		while(true) {
			input = inputString(hint);
			date = getDateByShortcuts(input);
			if (date != null) {
				if (IS_TESTING) {
					System.out.println("[Test] shortcut DateTime: "+formatDate(FORMAT_DATETIME, date));
				}
				return date;
			}
			if (isValidDate(input)) {
				date = parseDate(FORMAT_DATE, input);
				if (IS_TESTING) {
					System.out.println("[Test] parsed DateTime: "+formatDate(FORMAT_DATETIME, date));
				}
				
				if (formatDate(FORMAT_DATE, date).equalsIgnoreCase(input)) {
					return date;
				} else {
					System.out.println("Error: Date is not valid");
				}
			}
		}
	}
	/**
	 * Helper method to collect Date data within a time interval
	 * @param hint - display message for getting input
	 * @param startDate - starting of interval
	 * @param endDate - ending of interval
	 * @return {@code Date} of user input 
	 * @see #inputDate(String)
	 */
	public static Date inputDate(String hint, Date startDate, Date endDate) {
		if ( endDate.before(startDate)) {
			System.out.println("Error: Start date is after end date");
			return null;
		}
		Date input;
		while (true) {
			input = inputDate(hint);
			if (IS_TESTING) {
				System.out.println(String.format("[Test] Start\t%s \tisBefore %b", formatDate(FORMAT_DATETIME, startDate), input.before(startDate)));
				System.out.println(String.format("[Test] End\t%s \tisAfter  %b", formatDate(FORMAT_DATETIME, endDate), input.after(endDate)));
			}
			
			if ( input.before(startDate) ) {
				System.out.println(String.format("Error: Entered date cannot be before %s", formatDate(FORMAT_DATE, startDate)));
				continue;
			} else if ( input.after(endDate) ) {
				System.out.println(String.format("Error: Entered date cannot be after %s",  formatDate(FORMAT_DATE, endDate)));
				continue;
			}
			break;
		}
		return input;
	}
	/**
	 * Helper method to collect time data from user.
	 * Return a Date object that hour and minutes modified with user input. 
	 * Input can be cancelled with keyword "q" or "quit" 
	 * @param hint - display message for getting input
	 * @param date - Date object to be modified
	 * @return {@code Date} of user input
	 * @see #isValidTime(String)
	 * @see #inputTimeWithinSession(String, Date)
	 */
	public static Date inputTime(String hint, Date date) {
		String input;
		while(true) {
			input = inputString(hint).trim().toLowerCase();
			if (input.equals("q") || input.equals("quit")) {
				return null;
			}else if (isValidTime(input)) {
				String[] strTime = input.split(":");
				int  hr = Integer.parseInt(strTime[0]);
				int min = Integer.parseInt(strTime[1]);
				return changeTime(date, hr, min);
			}
		}
	}
	/**
	 * Helper method to collect time data from user within restaurant operating session.
	 * @param hint - display message for getting input
	 * @param date - Date object to be modified
	 * @return {@code Date} of user input
	 * @see #inputTime(String, Date)
	 * @see #isValidTime(String)
	 * @see #getSession(Date)
	 * 
	 */
	public static Date inputTimeWithinSession(String hint, Date date) {
		Date input;
		char session;
		while (true) {
			input = Utility.inputTime(hint, date);
			if (input == null) {
				return null;
			}
			
			session = Utility.getSession(input);
			if (IS_TESTING) {
				System.out.println(String.format("[Test] DATETIME(%s), SESSION(%c)", formatDate(FORMAT_DATETIME_DETAIL, input), session ));
			}	
			if (session != 'n' && isAfterLastActionTime(input, session)) {
				System.out.println("Error: Entered time is after last action time");
				continue;
			}
			if (input.before(new Date())) {
				System.out.println("Error: Entered time is passed");
				continue;
			} else if (session!= 'a' && session!='p') {
				System.out.println("Error: Entered time is not operating hour");
				continue;
			}
			break;
		}
		return input;
	}
	
	
	
	


	
	
	// Date related
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
	 * Method to get current session
	 * returns char value
	 * 
	 * Helper method to check session of provided Date object
	 * <blockquote><pre>
	 * 'a': AM session
	 * 'p': PM session
	 * 'n': Not in operating hour
     * </pre></blockquote>
	 * Duration of session can be found in {@link AppConstants}
	 * @param date - Date object for session checking
	 * @return session
	 */
	public static char getSession(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int hr = calendar.get(Calendar.HOUR_OF_DAY);
//		System.out.println("");
		if (hr>=AM_START && hr<AM_END) {
			return 'a';
		} else if (hr>=PM_START && hr<PM_END) {
			return 'p';
		} else {
			return 'n'; // n/a not applicable
		}
	}
	/**
	 * Helper method to check whether a Date object is over last action time of each session.
	 * Applies to:
	 * <ul>
	 * <li>creating order</li>
	 * <li>creating booking</li>
	 * </ul>
	 * Last action time is indicated in {@link AppConstants}
	 * @param date - DateTime for checking 
	 * @param session - operating session for checking
	 * @return boolean 
	 * @see #getSession(Date)
	 */
	public static boolean isAfterLastActionTime(Date date, char session) {
//		if (session != 'a' && session != 'p') {
//			System.out.println("Warning: Invalid session("+session+")");
//		}
		
		Calendar calendar = Calendar.getInstance();
		switch (session) {
		case 'a':
			calendar.setTime( Utility.changeTime(date, AM_END, 0) );
			calendar.add(Calendar.MINUTE, -LAST_ACTION_TIME_MIN);
			return date.after(calendar.getTime());
		case 'p':
			calendar.setTime( Utility.changeTime(date, PM_END, 0) );
			calendar.add(Calendar.MINUTE, -LAST_ACTION_TIME_MIN);
			return date.after(calendar.getTime());
		default:
			return true;	
		}
		
	}
	/**
	 * Helper method to convert a String value to Date object.<br>
	 * Supported shortcuts with system date is 19/04/2019:
	 * <blockquote><pre>
	 * "today" -> 19/04/2019
	 * "+1d"   -> 20/04/2019
	 * "-1d"   -> 18/04/2019
	 * "+1m"   -> 19/05/2019
	 * "-1m"   -> 19/03/2019
     * </pre></blockquote>
     * where 1 can be replaced by any int value
	 * @param shortcut
	 * @return Date
	 */
	public static Date getDateByShortcuts(String shortcut) {

		shortcut = shortcut.trim().toLowerCase();
		if (shortcut.contentEquals("today")) {
			return getToday();
		}
		
		if (shortcut.startsWith("+") || shortcut.startsWith("-")) {
			try {
				String unit = shortcut.substring(0, shortcut.length()-1);
//				if (IS_TESTING) {
//					System.out.println(String.format("[Test] getDateByShortcuts: shortcut(%s) -> unit(%s)", shortcut, unit));
//				}
				
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(getToday());
				if ( shortcut.endsWith("d") ) {
					calendar.add(Calendar.DAY_OF_YEAR, Integer.parseInt(unit));
					return calendar.getTime();
				}
				if ( shortcut.endsWith("m") ) {
					calendar.add(Calendar.MONTH, Integer.parseInt(unit));
					return calendar.getTime();
				}
			} catch (Exception e) {
				if (IS_TESTING) {
					System.out.println(String.format("[Test] getDateByShortcuts: shortcut(%s) is invalid", shortcut));
				}
			}
		}
		return null;
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
	/**
	 * Helper method to get Date object of tomorrow date.
	 * Date object will be starting of the date, i.e. hours, minutes and seconds will be set to 0
	 * @return Date
	 */
	public static Date getTomorrow() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getToday());
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		return calendar.getTime();
	}
	/**
	 * Helper method to get Date object that is one month away from today's date.
	 * Date object will be starting of the date, i.e. hours, minutes and seconds will be set to 0
	 * @return Date
	 */
	public static Date getMonthLater() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getToday());
		calendar.add(Calendar.MONTH, 1);
		return calendar.getTime();
	}
	
//	public static Date createDate(int year, int month, int day, int hr, int min) {
//		Calendar calendar = Calendar.getInstance();
//		calendar.set(Calendar.YEAR, year);
//		calendar.set(Calendar.MONTH, month);
//		calendar.set(Calendar.DAY_OF_MONTH, day);
//		calendar.set(Calendar.HOUR_OF_DAY, hr);
//		calendar.set(Calendar.MINUTE, min);
//		calendar.set(Calendar.SECOND, 0);
//		calendar.set(Calendar.MILLISECOND, 0);
//		return calendar.getTime();
//	}
	
//	public static Date createDate(int year, int month, int day) {
//		return createDate(year, month, day, 0, 0);
//	}
	/**
	 * Method to change value of existing Date time value
	 * returns updated Date value
	 * @param date - Date object to be modifying
	 * @param hr
	 * @param min
	 * @return Date
	 */
	public static Date changeTime(Date date, int hr, int min) {
//		if (IS_TESTING) {
//			System.out.println(String.format("[Test] changeTime= date:%s, hr:%d, min:%d", formatDate(FORMAT_DATETIME_DETAIL, date), hr, min));
//		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, hr);
		calendar.set(Calendar.MINUTE, min);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		//date.setTime(calendar.getTimeInMillis());
		return calendar.getTime();
	}
	/**
	 * Helper method to check if 2 Date object is on same date.<br>
	 * Hours and minutes will not be compared.
	 * @param date1
	 * @param date2
	 * @return boolean
	 */
	public static boolean isSameDate(Date date1, Date date2) {
		/*
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date1);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH); // Note: zero based!
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.setTime(date2);
		return year == calendar.get(Calendar.YEAR) 
			&&  month == calendar.get(Calendar.MONTH)
			&&  day ==calendar.get(Calendar.DAY_OF_MONTH);
		*/
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
		return sdf.format(date1).equals(sdf.format(date2));
	}
	/**
	 * Helper method to check if provided Date is same date as today
	 * @param date
	 * @return boolean
	 * @see #isSameDate(Date, Date)
	 */
	public static boolean isToday(Date date) {
		// maybe should use calendar object to check
		return isSameDate(new Date(), date);
	}

	/**
	 * Helper method to check whether a provided Date is within a interval.e
	 * @param start - start of interval
	 * @param end - end of interval
	 * @param date - date to be checking
	 * @return boolean
	 */
	public static boolean isBetween(Date start, Date end, Date date) {
		return date.after(start) && date.before(end);
	}
}
