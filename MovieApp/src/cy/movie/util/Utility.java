package cy.movie.util;

import java.util.List;

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
	
	public static String listToString(List<?> list) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < list.size(); i++) {
			if (i>0) {
				sb.append(", ");
			}
			sb.append(list.get(i).toString());
		}
		return sb.toString();
		
	}

}
