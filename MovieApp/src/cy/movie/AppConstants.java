package cy.movie;

import java.nio.file.Paths;

public interface AppConstants {
	final int LOG_LEVEL = Log.LEVEL_ERROR;
	final String APP_VERION = "1.0.0";
	final double GST = 0.07;
	final int MAX_NUM_BOOK_DATE = 3;
	final boolean IS_TESTING = false;
	final String DATA_FILE_NAME = "movie.dat";
	final String PATH_PROJECT = System.getProperty("user.dir");
	final String PATH_DATA_FOLDER = Paths.get(PATH_PROJECT, "data").toString();
	
	
	// request code
	final int CODE_REQ_DEFAULT = 0;
	final int CODE_REQ_MAIN = 1;
	final int CODE_REQ_MAIN_MEMBER = 2;
	final int CODE_REQ_MAIN_ADMIN = 3;
	final int CODE_REQ_MOVIE_SELECTION = 4;
	final int CODE_REQ_MOVIE_OPERTATION = 5;
	final int CODE_REQ_CINEPLEX_SELECTION = 6;
	final int CODE_REQ_CINEPLEX_OPERTATION = 7;
	final int CODE_REQ_TRANSACTION_SELECTION = 8;
	final int CODE_REQ_TRANSACTION_OPERTATION = 9;
	
	
	
	final String FORMAT_DATE = "dd/MM/yyyy";
	final String FORMAT_DATE_SHORT = "dd/MM/yy";
	final String FORMAT_MONTH = "MM/yyyy";
	final String FORMAT_TIME = "HH:mm";
	final String FORMAT_DATETIME = "dd/MM/yyyy HH:mm:ss";
	final String FORMAT_DATETIME_DETAIL = "dd/MM/yyyy HH:mm:ss.SSS";
}
