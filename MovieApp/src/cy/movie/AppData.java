package cy.movie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import cy.movie.control.AuthenticationManager;
import cy.movie.control.HolidayManager;
import cy.movie.entity.Cinema;
import cy.movie.entity.Cineplex;
import cy.movie.entity.Movie;
import cy.movie.entity.MovieType;
import cy.movie.entity.Period;
import cy.movie.entity.ShowTime;
import cy.movie.util.DateUtility;
import cy.movie.util.IOUtility;
import cy.movie.util.IOUtility.LoadDataInterface;
import cy.movie.util.IOUtility.SaveDataInterface;
import cy.movie.value.MovieStatus;
import cy.movie.value.TimeUnit;

/**
 * App Data to save persistence data, that shouldn't be clear after user logout
 * 
 */
public class AppData implements AppConstants, Serializable {
	private static AppData instance;
	private HolidayManager phMgr;
	private AuthenticationManager authMgr;
	private ArrayList<Cineplex> cineplexes;
	private ArrayList<Movie> movies;
	private ArrayList<MovieType> movieTypes;

	private AppData() {
		authMgr = new AuthenticationManager();
		phMgr = new HolidayManager();
		cineplexes = new ArrayList<Cineplex>();
		movies = new ArrayList<Movie>();
		movieTypes = new ArrayList<MovieType>();
		
		addDefaultData();
		addDummyData();
	}

	public static AppData getInstance() {
		if (instance == null) {
			instance = new AppData();
		}
		return instance;
	}

	public HolidayManager getHolidayManager() {
		return phMgr;
	}

	public AuthenticationManager getAuthenticationManager() {
		return authMgr;
	}

	public ArrayList<Cineplex> getCineplexes() {
		return cineplexes;
	}

	public ArrayList<Movie> getMovies() {
		return movies;
	}

	public ArrayList<MovieType> getMovieTypes() {
		return movieTypes;
	}
	
	private void addDefaultData() {
		movieTypes.add(new MovieType("2D", 10, 12, 5, 7));
		movieTypes.add(new MovieType("3D", 12, 15, 7, -1));
		movieTypes.add(new MovieType("IMAX", 15, 17, -1, -1));
	}


	/*
	 * ============================================================================
	 * I/O Operation
	 * ============================================================================
	 */
	public void save() {
		IOUtility.save(DATA_FILE_NAME, new SaveDataInterface() {
			@Override
			public void beforeSaving() {
				Log.i("start saving data");
			}

			@Override
			public Object[] getSavingData() {
				// TODO: implement save data
//				return new Object[] {
//						phMgr.getHolidays(), 
//						authMgr.getUsers(), 
//						cineplexes, 
//						movies, 
//						movieTypes };
				return new Object[]{AppData.instance};
			}

			@Override
			public void afterSaving(boolean success, Exception e) {
				if (e != null) {
					Log.i("save data failed! e: " + e.getMessage());
					return;
				}

				Log.i("data saved!");
			}
		});
	}

	public void load() {
		IOUtility.load(DATA_FILE_NAME, new LoadDataInterface() {

			@Override
			public void beforeLoading() {
				Log.i("start loading data");
			}

			@SuppressWarnings("unchecked")
			@Override
			public void afterLoading(Object[] data, Exception e) {
				if (e != null) {
					Log.i("load data failed! e: " + e.getMessage());
					return;
				}
				// TODO: data retrieved, update data
//				phMgr.setHolidays((HashMap<Integer, ArrayList<Holiday>>) data[0]);
//				authMgr.setUsers((ArrayList<User>) data[1]);
//				cineplexes.addAll((ArrayList<Cineplex>) data[2]);
//				movies.addAll((ArrayList<Movie>) data[3]);
//				movieTypes.addAll((ArrayList<MovieType>) data[4]);
				AppData.instance = (AppData) data[0];
				Log.i("data loaded!");
			}
		});
	}
	
	
	
	
	private void addDummyData() {
		Random random = new Random();
		// generate movies
		String[] movieNames = {"Weathering with You", "Forzen", "Fast & Furious", };
		for (String name:movieNames) {
			ArrayList<String> casts = new ArrayList<String>();
			casts.add("Actor 1");
			casts.add("Actor 2");
			movies.add(new Movie(name,"some description", 60+random.nextInt(60), new Date(), MovieStatus.NOW_SHOWING, "Director "+(1+random.nextInt(10)), casts ));
		}
		
		// generate cineplex
		String[] locations = {"JE", "AMK", "CCK"};
		for (String location:locations) {
			ArrayList<Cinema> cinemas = new ArrayList<Cinema>();
			for (int h = 0; h < 2; h++) {
				ArrayList<Integer> stairways = new ArrayList<Integer>();
				stairways.add(3);
				stairways.add(7);
				cinemas.add( new Cinema( (location+(h+1))  , "Standard", 0, 5, 10, stairways));
			}
			cineplexes.add( new Cineplex("Cineplex "+location, location, cinemas));
		}
		
		// generate showtime
		for (Cineplex cineplex : cineplexes) {
			for (Cinema cinema : cineplex.getCinemas()) {
				for (int i = 0; i < MAX_NUM_BOOK_DATE; i++) {
					// determine start hour
					Date date = DateUtility.changeTime(new Date(), 8, 0);
					date.setTime(date.getTime() + i*TimeUnit.DAY.toMs());
					
					ArrayList<ShowTime> sts = cineplex.getScheduleManager().getShowTimes(date, cinema);
					if (sts != null && sts.size()>0) {
						Date last = sts.get(sts.size()-1).getPeriod().getEnd();
						date.setTime(last.getTime() + 10*TimeUnit.MINUTE.toMs() );
					}
//					Log.d(cineplex.getName()+", "+cinema.getCode()+", "+DateUtility.formatDate(FORMAT_DATE, date));
					for (Movie movie : movies) {
						Period period = new Period(date, new Date(date.getTime()+movie.getDurationMin()*TimeUnit.MINUTE.toMs()));
						ShowTime showtime = new ShowTime(movie, movieTypes.get(0), cinema, period);
						cineplex.getScheduleManager().addShowTime(showtime);
						
						date = new Date(period.getEnd().getTime()+10*TimeUnit.MINUTE.toMs());
					}

				}
			}
		}
	}
	
//	public void debug() {
//		Log.d("user: "+authMgr.getUsers().size());
//		Log.d("PH: "+phMgr.getHolidays().size());
//		Log.d("movies: "+movies.size());
//		Log.d("movieTypes: "+movieTypes.size());
//		Log.d("cineplexes: "+cineplexes.size());
//		for (Cineplex cineplex : cineplexes) {
//			Log.d(cineplex.getName()+" ["+cineplex.getScheduleManager().toString()+"]");
//		}
//	}

}
