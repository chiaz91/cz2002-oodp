package cy.movie.value;

public enum MovieStatus {
	COMING_SOON("Coming soon"), PREVIEW("Preview"), NOW_SHOWING("Now showing"), END_OF_SHOWING("End of showing");

	String desc;
	MovieStatus(String desc){
		this.desc=desc;
	}
	
	@Override
	public String toString() {
		return desc;
	}
	
	
}
