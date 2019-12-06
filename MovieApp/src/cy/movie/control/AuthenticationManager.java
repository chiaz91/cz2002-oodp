package cy.movie.control;


import java.util.ArrayList;

import cy.movie.entity.User;

public class AuthenticationManager {
	private ArrayList<User> users;
	
	public AuthenticationManager() {
		users = new ArrayList<User>();
	}
	
	public ArrayList<User> getUsers() {
		return users;
	}
	
	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}




	public boolean login(String username, String password) {
		for (User u : users) {
			if (u.getUserName().equalsIgnoreCase(username)) {
				if (u.validatePassword(password)) {
					SessionManager.getInstance().setUser(u);
					return true;
				}
				break;
			}
		}
		return false;
	}
	
	

}
