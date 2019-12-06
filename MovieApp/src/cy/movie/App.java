package cy.movie;

import cy.movie.boundary.MainUi;

public class App implements AppConstants{
	
	
	public void run() {
//		Log.setLevel(Log.LEVEL_INFO);
		Log.i("App version: "+APP_VERION);
		new MainUi().start();
		Log.i("App Ended");
	}
	
	public static void main(String[] args) {
		new App().run();
	}

}
