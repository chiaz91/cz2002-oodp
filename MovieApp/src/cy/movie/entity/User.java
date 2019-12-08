package cy.movie.entity;

import java.io.Serializable;
import java.util.Comparator;

import cy.movie.Log;

public class User implements Serializable, Comparable<User>{
	private String userName;
	private String password;
	private String name;
	

	public User(String userName, String password, String name) {
		this.userName = userName;
		this.password = password;
		this.name = name;
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
		Log.d("validatePassword: this("+this.password+") vs arg("+password+")");
		return this.password.equals(password);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Override
	public int compareTo(User other) {
		return Comparator.comparing(User::getUserName).compare(this, other);
	}
	
}
