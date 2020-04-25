package app.dataManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import app.AppConstants;
import app.model.Table;
import app.util.Utility;

public class TableManager implements AppConstants {
	ArrayList<Table> tables;


	public TableManager() {
		this.tables = new ArrayList<Table>();
		init();
	}

	public ArrayList<Table> getTables() {
		return tables;
	}

	public void setTables(ArrayList<Table> tables) {
		this.tables = tables;
	}

	public void init() {
		int count = 1;
		// 10 x 2-seats
		for (int i = 0; i < 10; i++) {
			tables.add(new Table("T" + (count++), 2));
		}
		
		// 10 x 4-seats
		for (int i = 0; i < 10; i++) {
			tables.add(new Table("T" + (count++), 4));
		}

		// 5 x 8-seats
		for (int i = 0; i < 5; i++) {
			tables.add(new Table("T" + (count++), 8));
		}
		// 5 x 10-seats
		for (int i = 0; i < 5; i++) {
			tables.add(new Table("T" + (count++), 10));
		}

	}

	public ArrayList<Table> getAvailableTables(Date date, char session) {
		Date today = Utility.getToday();
		if (date.before(today)) {
			return null;
		}
		
		// check if session is over
		if (Utility.isToday(date)) {
			Date now = new Date();
			Date endSession = new Date(); 
			endSession = Utility.changeTime(endSession, AM_END, 0);
			if (session == 'a' &&  now.after(endSession) ) {
				return null;
			}
			endSession = Utility.changeTime(endSession, PM_END, 0);
			if (session == 'p' && now.after(endSession) ) {
				return null;
			}
		}
		
		// check booked tables
		ArrayList<Table> freeTables = new ArrayList<Table>();
		ArrayList<Table> bookedTables = Restaurant.getInstance().getBookingManager().getOccupiedTables(date, session);
		
		freeTables.addAll(this.tables);
		freeTables.removeAll(bookedTables);

		// check currently in use table (from order)
		if (Utility.isToday(date) && Utility.getSession(new Date())==session) {
			freeTables.removeAll(Restaurant.getInstance().getOrderManager().getOccupiedTable());
		}
		return freeTables;
	}

	public ArrayList<Table> getAvailableTables(Date date, char session, int pax) {
		ArrayList<Table> freeTables = getAvailableTables(date, session);
		for (int i = freeTables.size()-1; i >= 0; i--) {
			if (freeTables.get(i).getSeats()<pax) {
				freeTables.remove(i);
			}
		}
		return freeTables;
	}

	public Table getTableById(String id) {
		for (Table table : tables) {
			if (table.getId().equalsIgnoreCase(id)) {
				return table;
			}
		}
		return null;
	}

	public void displayTablesStatus() {
		displayTablesStatus(new Date());
	}

	public void displayTablesStatus(Date date) {
		System.out.println(String.format("Date: %s", Utility.formatDate(AppConstants.FORMAT_DATE, date)));
		System.out.println(String.format("%-4s \t%-4s \t%-5s \t%-8s \t%-8s", "No.", "Id", "Seats", "AM", "PM"));
		Table table;
		ArrayList<Table> freeTablesAm = getAvailableTables(date, 'a');
		ArrayList<Table> freeTablesPm = getAvailableTables(date, 'p');

		for (int i = 0; i < this.tables.size(); i++) {
			table = this.tables.get(i);
			System.out.println(String.format("[%2d] \t%-4s \t%-5s \t%-8s \t%-8s", 
					i + 1, 
					table.getId(),
					table.getSeats(), 
					freeTablesAm==null?"N.A" : freeTablesAm.contains(table) ? "Vacated" : "Occupied",
					freeTablesPm==null?"N.A" : freeTablesPm.contains(table) ? "Vacated" : "Occupied")
			);
		}
	}

	public void displayFreeTable(Date date, char session, int pax) {
		ArrayList<Table> freeTables = getAvailableTables(date, session, pax);
		if (freeTables.size() == 0) {
			System.out.println("No available tables");
		} else {
			System.out.println("Available tables as follow: ");
			int tableSeats = 0;
			Table table;
			for (int i=0; i<freeTables.size(); i++) {
				table = freeTables.get(i);
				if (table.getSeats()>tableSeats) {
					if (i>0) {
						System.out.println();
					}
					tableSeats = table.getSeats();
					System.out.print(String.format("%2d seats: ", tableSeats));
				}
				System.out.print(table.getId()+" ");
			}
			System.out.println();
		}
	}
	

	public void sort() {
		Collections.sort(this.tables);
	}

}
