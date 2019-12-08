package cy.movie.boundary;

import java.util.ArrayList;
import java.util.Date;

import cy.movie.Log;
import cy.movie.boundary.OptionsSelector.OnSelectOptionCallback;
import cy.movie.entity.Cineplex;
import cy.movie.entity.Movie;
import cy.movie.entity.Review;
import cy.movie.entity.ShowTime;
import cy.movie.util.DateUtility;
import cy.movie.util.Utility;

public class DisplayMoviesUi extends BaseMenuSelectionUi{
	OptionsSelector<Movie> movieSelector;
	Movie selected;
	
	public DisplayMoviesUi() {
		getOptionSelector().setRequestCode(CODE_REQ_MOVIE_OPERTATION);
		movieSelector = new OptionsSelector<Movie>(CODE_REQ_MOVIE_SELECTION, getAppData().getMovies()) {
			@Override
			public String formatOption(Movie item) {
				return item.getTitle();
			}
		};
	}
	
	


	
	@Override
	public void start() {
		initMenu("Display Detail",
				"Show Review",
				"Display Show Times(for all cineplex)");
		movieSelector.askOptionsRepeatedly(this);
		
		// clear selection from session manager?
	}


	@Override
	public void onSelected(int requestCode, int selection) {
		if (requestCode == CODE_REQ_MOVIE_SELECTION) {
			selected = movieSelector.getOptions().get(selection-1);
			getSessionManager().getSessionData().put("selected_movie", selected);
			getOptionSelector().askOptionsRepeatedly(this);
		} else if (requestCode == CODE_REQ_MOVIE_OPERTATION){ 
			// handle operation
			switch (selection) {
			case 1: // display details
				displayMovieDetail();
				break;
			case 2: // display reviews
				displayReviews();
				break;
			case 3: // display showtimes across cineplex
				displayShowTime();
				break;
			default:
				break;
			}
		}
	}
	
	public void displayMovieDetail() {
		System.out.println("Title        : "+selected.getTitle() );
		System.out.println("Synopsis     : "+selected.getSynopsis() );
		System.out.println("Duration     : "+selected.getDurationMin() +" mins" );
		System.out.println("Release Date : "+DateUtility.formatDate(FORMAT_DATE, selected.getDateRelease()) );
		System.out.println("Status       : "+selected.getStatus() );
		System.out.println("Director     : "+selected.getDirector() );
		System.out.println("Casts        : " + Utility.listToString(selected.getCasts()));
		if (selected.getReviews().size()>1) {
			System.out.println("Overal Rating: "+selected.getOveralRating() );	
		}
	}
	
	public void displayReviews() {
		if (selected.getReviews().size() == 0) {
			System.out.println("No Review");
		} else {
			for (Review review : selected.getReviews()) {
				System.out.println(String.format("%s (%d): %s", review.getReviewer().getName(), review.getRating(), review.getComment()));
			}
		}
	}
	
	public void displayShowTime() {
		// select date (today+2days)
		ArrayList<Date> dates = DateUtility.getListDates(DateUtility.getToday(), MAX_NUM_BOOK_DATE);
//		OptionsSelector<Date> selector = new OptionsSelector<Date>(0, dates) {
//			@Override
//			public String formatOption(Date item) {
//				return DateUtility.formatDate(FORMAT_DATE, item);
//			}
//		};
//		selector.setOnSelectOptionCallback(new OnSelectOptionCallback() {
//			
//			@Override
//			public void onSelected(int requestCode, int selection) {
//				// display all show times of the date
//				Date selectedDate = selector.getOptions().get(selection-1);
//				displayShowTimeForDate(selectedDate); 
//				selector.stopSelection();
//			}
//		});
//		selector.askOptions();
		for (Date date : dates) {
			displayShowTimeForDate( date);
		}
		
	}
	
	private void displayShowTimeForDate(Date date) {
		System.out.println("Date: "+DateUtility.formatDate(FORMAT_DATE, date));
		for (Cineplex cineplex : getAppData().getCineplexes()) {
			System.out.print(cineplex.getName()+": ");
			ArrayList<ShowTime> st = cineplex.getScheduleManager().getShowTimes(date, selected);
			if (st.size()==0) {
				System.out.println("no show time for "+selected.getTitle());
			}else {
				for (int i = 0; i < st.size(); i++) {
					if (i>0) {
						System.out.print(", ");
					}
					System.out.print(DateUtility.formatDate(FORMAT_TIME, st.get(i).getPeriod().getStart()));
				}
				System.out.println();
			}
		}
		System.out.println();
	}

}
