package cy.movie;

import java.util.ArrayList;

import cy.movie.control.AuthenticationManager;
import cy.movie.control.HolidayManager;
import cy.movie.entity.Cineplex;

/**
 * App Data to save persistence data, that shouldn't be clear after user logout
 * 
 */
public class AppData {
	private static AppData instance;
	private HolidayManager phMgr;
	private AuthenticationManager authMgr;
	private ArrayList<Cineplex> cineplexs;
	

	private AppData() {
		authMgr = new AuthenticationManager();
		phMgr = new HolidayManager();
		cineplexs = new ArrayList<Cineplex>();
		
		// load data
	}
	
	public static AppData getInstance() {
		if (instance==null) {
			instance = new AppData();
		}
		return instance;
	}
	
	public HolidayManager getHolidayManager() {
		return phMgr;
	}
	
	public AuthenticationManager getAuthenticationManager() {
		return authMgr;
	}
	
	public ArrayList<Cineplex> getListCineplexs(){
		return cineplexs;
	}
	
	
	
	// save
	
	// load
}
