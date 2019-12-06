package cy.movie.boundary;

import cy.movie.AppConstants;
import cy.movie.AppData;
import cy.movie.control.SessionManager;

public abstract class BaseUi implements AppConstants{
	
	public abstract void start();
	
	protected final AppData getAppData() {
		return AppData.getInstance();
	}
	
	protected final SessionManager getSessionManager() {
		return SessionManager.getInstance();
	}

}
