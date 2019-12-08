package cy.movie.boundary;

import java.util.ArrayList;
import java.util.Date;

import cy.movie.entity.Movie;
import cy.movie.util.InputUtility;
import cy.movie.value.MovieStatus;


public class ManageMovieUi extends BaseMenuSelectionUi{
	
	
	@Override
	public void start() {
		initMenu("Create Movie",
				"Edit Movie",
				"Remove Listing");
		getOptionSelector().askOptionsRepeatedly(this);
	}
	
	@Override
	public void onSelected(int requestCode, int selection) {
		switch (selection) {
		case 1:
			createMovie();
			break;
		case 2:
			editMovie();
			break;
		
		default:
			System.out.println("Implementing");
		}
	}
	
	private void createMovie() {
		// input title, synopsis, duration, Date release, status, director, casts
		System.out.println("Create new movie");
		String title = InputUtility.inputString("Name: ");
		String synopsis = InputUtility.inputString("Synopsis: ");
		int durationMin = InputUtility.inputInt("Duration [mins]: ");
		Date dateRelease = InputUtility.inputDate("Date release [DD/MM/YYYY]: ");
//		String status = InputUtility.inputString("Status: ");
		MovieStatus status = MovieStatus.COMING_SOON;
		String director = InputUtility.inputString("Director: ");
		ArrayList<String> casts = new ArrayList<String>();
		
		do {
			casts.add(InputUtility.inputString("Cast "+(casts.size()+1)+": "));
			if (casts.size() >= 2 && InputUtility.inputBoolean("Continue [Y/N]:")) {
				break;
			}
		} while (true);

		Movie newMovie = new Movie(title, synopsis, durationMin, dateRelease, status, director, casts);
		getAppData().getMovies().add( newMovie );
		System.out.println("Movie created");
		// TODO display movie details
	}
	
	private void editMovie() {
		
		
	}
	
	

	







}
