package cy.movie.entity;

import java.io.Serializable;
import java.util.Comparator;

public class User implements Serializable, Comparable<User>{
	private String userName;
	private String password;
	
	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean validatePassword(String password) {
		return this.password.equals(password);
	}

	@Override
	public int compareTo(User other) {
		return Comparator.comparing(User::getUserName).compare(this, other);
	}
	
}
