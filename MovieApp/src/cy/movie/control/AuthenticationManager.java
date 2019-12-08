package cy.movie.control;


import java.io.Serializable;
import java.util.ArrayList;

import cy.movie.Log;
import cy.movie.entity.Admin;
import cy.movie.entity.MovieGoer;
import cy.movie.entity.User;

public class AuthenticationManager implements Serializable{
	private ArrayList<User> users;
	
	public AuthenticationManager() {
		users = new ArrayList<User>();
		
		generateDummyDate();
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
	
	public void logout() {
		SessionManager.getInstance().logout();
	}
	
	
	private void generateDummyDate() {
		users.add( new Admin("admin", "admin", "Admin User"));
		users.add(new MovieGoer("chiayu","123456", "Chia Yu"));
	}
	
	

}
