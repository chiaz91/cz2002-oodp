package cy.movie.value;

public enum TimeUnit {
	MILISECOND(1), SECOND(1000), MINUTE(1000*60), HOUR(1000*60*60), DAY(1000*60*60);
	int ms; 
	TimeUnit(int ms){
		this.ms = ms;
	}
	public int toMs(){
		return ms;
	}
}
