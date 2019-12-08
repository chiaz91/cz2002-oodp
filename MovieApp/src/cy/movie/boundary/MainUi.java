package cy.movie.boundary;

import cy.movie.entity.Admin;
import cy.movie.entity.User;

public class MainUi extends BaseMenuSelectionUi {
	String[] menuPublic = { 
			"Search Movie", 
			"Display Movie List", 
			"Display Cineplexs", 
			"Register", 
			"Login" };
	String[] menuMember = { 
			"Search Movie", 
			"Display Movie List", 
			"Display Cineplexs",
			"Booking History", 
			"Logout" }; 
	String[] menuAdmin =  { 
			"Manage Movie", 
			"Manage Movie Type", 
			"Manage Cineplexs", 
			"Generate Report", 
			"Logout" };

	@Override
	public void start() {		
		updateMenu();
		getOptionSelector().askOptionsRepeatedly(this);
	}


	public void updateMenu() {
		User user = getSessionManager().getUser();
		if (user == null) {
			getOptionSelector().setOptions(menuPublic);
			getOptionSelector().setRequestCode(CODE_REQ_MAIN);
		} else if (user instanceof Admin) {
			getOptionSelector().setOptions(menuAdmin);
			getOptionSelector().setRequestCode(CODE_REQ_MAIN_ADMIN);
		} else { 
			getOptionSelector().setOptions(menuMember);
			getOptionSelector().setRequestCode(CODE_REQ_MAIN_MEMBER);
		}
	}

	@Override
	public void onSelected(int requestCode, int selection) {
		switch (requestCode) {
		case CODE_REQ_MAIN:
			handlePublicMenu(selection);
			break;
		case CODE_REQ_MAIN_ADMIN:
			handleAdminMenu(selection);
			break;
		case CODE_REQ_MAIN_MEMBER:
			handleMemberMenu(selection);
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + selection);
		}
	}

	public void handlePublicMenu(int selection) {
		switch (selection) {
//		case 1: // search movie
//			break;
		case 2: // display movie list
			new DisplayMoviesUi().start();
			break;
		case 3: // display cineplex list
			new DisplayCineplexUi().start();
			break;
//		case 4:
//			// register
//			break;
		case 5:
			new LoginUi().start();
			updateMenu();
			break;
		default:
			System.out.println("Unknown / Implementating function");
		}
	}

	public void handleMemberMenu(int selection) {
		switch (selection) {
//		case 1: // search movie
//			break;
		case 2: // display movie list
			new DisplayMoviesUi().start();
			break;
		case 3: // display cineplex list
			new DisplayCineplexUi().start();
			break;
		case 4: // display booking history
			new DisplayBookingHistory().start();
			break;
		case 5:
			getAppData().getAuthenticationManager().logout();
			updateMenu();
			break;
		default:
			System.out.println("Unknown / Implementating function");
		}
	}

	public void handleAdminMenu(int selection) {
		switch (selection) {
//		case 1: // go manage movie ui
//			break;
//		case 2: // go manage movie type ui
//			break;
//		case 3: // go manage cineplex ui
//			break;
//		case 4: // go generate report ui
//			
//			break;
		case 5:
			getAppData().getAuthenticationManager().logout();
			updateMenu();
			break;
		default:
			System.out.println("Unknown / Implementating function");
		}
	}




}
