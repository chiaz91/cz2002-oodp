package cy.movie.util;


import java.util.Date;
import java.util.Scanner;

import cy.movie.AppConstants;
import cy.movie.Log;


public class InputUtility implements AppConstants {
	private static Scanner sc = new Scanner(System.in);

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

	public static int inputInt(String hint, int min, int max) {
		if (max < min) {
			throw new IllegalArgumentException("max is less than min.");
		}
		
		int input;
		do {
			input = inputInt(hint);
		} while (input<min || input>max);
		return input;
	}
	
	public static int inputIntAtLeast(String hint, int min) {
		int input;
		do {
			input = inputInt(hint);
		} while (input<min);
		return input;
	}
	
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
	
	public static double inputDoubleAtLeast(String hint, double min) {
		double input;
		do {
			input = inputDouble(hint);
		} while (input<min);
		return input;
	}

	public static String inputString(String hint) {
		String input;
		do {
			System.out.print(hint);
			input = sc.nextLine();
		} while (input.length() == 0);
		return input;
	}

	public static String inputString(String hint, int length) {
		if (length<1) {
			throw new IllegalArgumentException("length should be at least 1");
		}
		String input;
		do {
			input = inputString(hint);
		} while (input.length()>length);
		return input;
	}
	
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
	
	public static Date inputDate(String hint) {
		String input;
		Date date;
		while(true) {
			input = inputString(hint);
			if (DateUtility.isValidDate(input)) {
				date = DateUtility.parseDate(FORMAT_DATE, input);
				Log.d("[Test] parsed DateTime: "+DateUtility.formatDate(FORMAT_DATETIME, date));
				
				if (DateUtility.formatDate(FORMAT_DATE, date).equalsIgnoreCase(input)) {
					return date;
				} else {
					System.out.println("Error: Date is not valid");
				}
			}
		}
	}

	public static Date inputDate(String hint, Date startDate, Date endDate) {
		if ( endDate.before(startDate)) {
			System.out.println("Error: Start date is after end date");
			return null;
		}
		Date input;
		while (true) {
			input = inputDate(hint);
			Log.d(String.format("[Test] Start\t%s \tisBefore %b", DateUtility.formatDate(FORMAT_DATETIME, startDate), input.before(startDate)));
			Log.d(String.format("[Test] End\t%s \tisAfter  %b", DateUtility.formatDate(FORMAT_DATETIME, endDate), input.after(endDate)));
			
			if ( input.before(startDate) ) {
				System.out.println(String.format("Error: Entered date cannot be before %s", DateUtility.formatDate(FORMAT_DATE, startDate)));
				continue;
			} else if ( input.after(endDate) ) {
				System.out.println(String.format("Error: Entered date cannot be after %s",  DateUtility.formatDate(FORMAT_DATE, endDate)));
				continue;
			}
			break;
		}
		return input;
	}

	public static Date inputTime(String hint, Date date) {
		String input;
		while(true) {
			input = inputString(hint).trim().toLowerCase();
			if (input.equals("q") || input.equals("quit")) {
				return null;
			}else if (DateUtility.isValidDate(input)) {
				String[] strTime = input.split(":");
				int  hr = Integer.parseInt(strTime[0]);
				int min = Integer.parseInt(strTime[1]);
				return DateUtility.changeTime(date, hr, min);
			}
		}
	}
}
