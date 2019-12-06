package cy.movie.control;

import java.util.HashMap;

import cy.movie.entity.User;

/**
 * Session manager is used to temporary data, which can be use across all ui
 * like user login info, and maybe user selection object (movie or showtime)
 */
public class SessionManager {
	private static SessionManager instance;
	HashMap<String, Object> data;
	User user;
	
	
	private SessionManager() {
		data = new HashMap<String, Object>();
	}
	
	public static SessionManager getInstance() {
		if (instance == null) {
			instance = new SessionManager();
		}
		return instance;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public HashMap<String, Object> getSessionData(){
		return data;
	}
	
	public boolean isLogin() {
		return this.user != null;
	}
	
	public void clear() {
		data.clear();
	}
	
	public void logout() {
		// clear session
		this.user = null;
		clear();
	}
}
