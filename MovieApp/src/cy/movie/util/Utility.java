package cy.movie.util;

public class Utility {
	
	public static double round(double number) {
		return Math.round(number*100.0)/100.0;
	}

	public static String capitalize(String string) {
		if (string == null) {
			return null;
		}
		if (string.length() == 0) {
			return string;
		}
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
	}

	public static String ellipsize(String string, int max) {
		if (string.length() <= max) {
			return string;
		} else {
			return string.substring(0, max-3)+"...";
		}
	}

}
