package cy.movie.boundary;

import java.util.ArrayList;
import java.util.Arrays;

import cy.movie.entity.MovieGoer;
import cy.movie.entity.User;

public class MainUi extends BaseMenuSelectionUi{
	ArrayList<String> menuPublic = new ArrayList<String>();
	ArrayList<String> menuMember = new ArrayList<String>();
	ArrayList<String> menuAdmin = new ArrayList<String>();
	
	
	@Override
	public void start() {
		updateMenu();
		getOptionSelector().askOptions();
	}
	
	public void updateMenu() {
		User user = getSessionManager().getUser();
		if (user == null) {
			getOptionSelector().setOptions(menuPublic);
			getOptionSelector().setRequestCode(CODE_REQ_MAIN);
		}
		else if (user instanceof MovieGoer) {
			getOptionSelector().setOptions(menuMember);
			getOptionSelector().setRequestCode(CODE_REQ_MAIN_MEMBER);
		} else { // assume admin
			getOptionSelector().setOptions(menuAdmin);
			getOptionSelector().setRequestCode(CODE_REQ_MAIN_ADMIN);
		}
	}


	@Override
	protected void initMenu(ArrayList<String> menu) {
		menuPublic.addAll(Arrays.asList(new String[] {
				"Search Movie",
				"Display Movie List",
				"Display Cineplexs",
				"Login",
		}));
		menuMember.addAll(Arrays.asList(new String[] {
				"Search Movie",
				"Display Movie List",
				"Display Cineplexs",
				"Booking History",
				"Logout"
		}));
		menuAdmin.addAll(Arrays.asList(new String[] {
				"Manage Movie",
				"Manage Cineplexs",
				"Generate Report",
				"Edig Pricing",
				
				"Logout"
		}));
		
		
	}

	
	
	@Override
	public void onSelected(int requestCode, int selection) {
		switch (requestCode) {
		case CODE_REQ_MAIN:
			handlePublicMenu(selection);
			break;
		case CODE_REQ_MAIN_MEMBER:
			handleMemberMenu(selection);
			break;
		case CODE_REQ_MAIN_ADMIN:
			handleAdminMenu(selection);
		default:
			throw new IllegalArgumentException("Unexpected value: " + selection);
		}
	}
	
	public void handlePublicMenu(int selection) {
		switch (selection) {
		case 4:
			new LoginUi().start();
			updateMenu();
			break;
		default:
			System.out.println("Unknown / Implementating function");
		}
	}
	
	public void handleMemberMenu(int selection) {
		switch (selection) {
		default:
			System.out.println("Unknown / Implementating function");
		}
	}

	public void handleAdminMenu(int selection) {
		switch (selection) {
		default:
			System.out.println("Unknown / Implementating function");
		}
	}






}
