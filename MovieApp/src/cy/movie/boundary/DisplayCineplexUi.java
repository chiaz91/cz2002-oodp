package cy.movie.boundary;

import java.util.ArrayList;
import java.util.Date;

import cy.movie.entity.Cineplex;
import cy.movie.entity.Movie;
import cy.movie.entity.ShowTime;
import cy.movie.util.DateUtility;

public class DisplayCineplexUi extends BaseMenuSelectionUi{
	OptionsSelector<Cineplex> cineplexSelector;
	Cineplex selected;
	
	public DisplayCineplexUi() {
		getOptionSelector().setRequestCode(CODE_REQ_CINEPLEX_OPERTATION);
		cineplexSelector = new OptionsSelector<Cineplex>(CODE_REQ_CINEPLEX_SELECTION, getAppData().getCineplexes()) {
			@Override
			public String formatOption(Cineplex item) {
				return String.format("%s (%s)", item.getName(), item.getLocation());
			}
		};
	}
	
	
	
	@Override
	public void start() {
		initMenu("Display Show Times", "Book Ticket");
		cineplexSelector.askOptionsRepeatedly(this);
	}



	@Override
	public void onSelected(int requestCode, int selection) {
		if (requestCode == CODE_REQ_CINEPLEX_SELECTION) {
			selected = cineplexSelector.getOptions().get(selection-1);
			getSessionManager().getSessionData().put("selected_cineplex", selected);
			getOptionSelector().askOptionsRepeatedly(this);
		} else if (requestCode == CODE_REQ_CINEPLEX_OPERTATION){
			// handle operation
			switch (selection) {
			case 1: // Display Show Times
				displayShowTimes();
				break;
//			case 2: // Book Ticket
//				bookTicket();
//				break;
			default:
				System.out.println("WIP");
				break;
			}
		}
	}
	
	public void displayShowTimes() {
		ArrayList<Date> dates = DateUtility.getListDates(DateUtility.getToday(), MAX_NUM_BOOK_DATE);
		for (Date date : dates) {
			System.out.println("Date: "+DateUtility.formatDate(FORMAT_DATE, date));
			for (Movie movie : getAppData().getMovies()) {
				ArrayList<ShowTime> showtimes = selected.getScheduleManager().getShowTimes(date, movie);
				if (showtimes!=null && showtimes.size()>0) {
					
					System.out.print(movie.getTitle()+": ");
					for (int i = 0; i < showtimes.size(); i++) {
						ShowTime st = showtimes.get(i);
						if (i>0) {
							System.out.print(", ");
						}
						System.out.print(st.getCinema().getCode()+" "+DateUtility.formatDate(FORMAT_TIME, st.getPeriod().getStart()));
					}
					System.out.println();
				}
			}
			System.out.println();
		}
	}
	
	public void bookTicket() {
		ArrayList<Date> dates = DateUtility.getListDates(DateUtility.getToday(), MAX_NUM_BOOK_DATE);
		// display & select date;
		// display & select movie;
		// display & select timing
		// display layout 
		// select for seat;
		// confirm booking
		// generate ticket
		// update seat availability 
	}


	

}
