package cy.movie;

public interface AppConstants {
	final int LOG_LEVEL = Log.LEVEL_ERROR;
	final String APP_VERION = "1.0.0";
	final double GST = 0.07;
	
	// request code
	final int CODE_REQ_DEFAULT = 0;
	final int CODE_REQ_MAIN = 1;
	final int CODE_REQ_MAIN_MEMBER = 2;
	final int CODE_REQ_MAIN_ADMIN = 3;
	
	
	
	final String FORMAT_DATE = "dd/MM/yyyy";
	final String FORMAT_DATE_SHORT = "dd/MM/yy";
	final String FORMAT_MONTH = "MM/yyyy";
	final String FORMAT_TIME = "HH:mm";
	final String FORMAT_DATETIME = "dd/MM/yyyy HH:mm:ss.SSS";
	final String FORMAT_DATETIME_DETAIL = "dd/MM/yyyy HH:mm:ss.SSS";
}
