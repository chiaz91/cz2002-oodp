package cy.movie;


/**
 * For testing purpose 
 */
public class Log {
	public static final int LEVEL_NONE  = 0;
	public static final int LEVEL_DEBUG = 1;
	public static final int LEVEL_ERROR = 2;
	public static final int LEVEL_INFO  = 3;
	private static int level = LEVEL_NONE;
	
	
	public static void setLevel(int level) {
		Log.level = level;
	}
	
	public static void d(String msg) {
		if (level >= LEVEL_DEBUG) {
			System.out.println("Log.D::"+msg);
		}
	}
	
	public static void e(String msg) {
		if (level >= LEVEL_ERROR) {
			System.out.println("Log.E::"+msg);
		}
	}
	
	public static void i(String msg) {
		if (level >= LEVEL_INFO) {
			System.out.println("Log.I::"+msg);
		}
	}

}
