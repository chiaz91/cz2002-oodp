package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import app.dataManager.BookingManager;
import app.dataManager.Menu;
import app.dataManager.OrderManager;
import app.dataManager.Restaurant;
import app.dataManager.TableManager;
import app.model.Booking;
import app.model.Invoice;
import app.model.MenuItem;
import app.model.Order;
import app.model.OrderItem;
import app.model.PromotionItem;
import app.model.Report;
import app.model.Staff;
import app.model.Table;
import app.util.DataUtility;
import app.util.Utility;


public class App implements AppConstants, OptionsSelector.OnSelectOptionCallback {
	public static void main(String[] args) {
		new App().run();
	}

	Staff user;
	Order selectedOrder;
	
	// menu selector
	OptionsSelector optsMain;
	OptionsSelector optsOrder;
	OptionsSelector optsOrderActions;
	OptionsSelector optsBooking;
	OptionsSelector optsSetting;
	OptionsSelector optsEditMenuItem;


	public void run() {
		// load data from file
		if (!DataUtility.loadData()) {
			System.out.println("Welcome to RRPSS!");
			System.out.println("It seems that you are first time using this program, would you like to start with sample data?");
			System.out.println("Data can be clear any time in Setting menu");
			if (Utility.inputBoolean("Import sample data [y/n]? ")) {
				DataUtility.loadWithDefaultMenu();
			}
		}

		// select user(staff)
		ArrayList<Staff> staffs = Restaurant.getInstance().getStaffs();
		if (staffs.size() == 0) {
			DataUtility.loadDefaultStaff();
		}
		user = selectStaffByIndex();

		initAppMenu();
		optsMain.askOptions();
		
		// program ended, save data
		DataUtility.saveData();
		System.out.println("Program ended.");
	}

	public void initAppMenu() {
		// Create Main Menu
//		String headerMain = "==================================================\n" 
//				+ "Welcome to <Store Name>\n"
//				+ "==================================================";
		String[] arMenuMain = new String[] { 
				"Order", 
				"Reservation", 
				"Table", 
				"Report", 
				"Setting" 
		};
		optsMain = new OptionsSelector(REQ_CODE_MENU_MAIN, arMenuMain);
		optsMain.setOnSelectOptionCallback(this);
//		optsMain.setHeader(headerMain);
		
		// order
		String[] arMenuOrder = new String[] {
				"Display list orders",
				"Create order",
				"Select order"
		};
		optsOrder = new OptionsSelector(REQ_CODE_MENU_ORDER, arMenuOrder);
		optsOrder.setOnSelectOptionCallback(this);
		
		// order action
		String[] arMenuOrderAction = new String[] { 
				"Display details",
				"Add item",
				"Update item quantity",
				"Delete item",
				"Print bill invoice",
				"Delete order"
		};
		optsOrderActions = new OptionsSelector(REQ_CODE_MENU_ORDER_ACTION, arMenuOrderAction);
		optsOrderActions.setOnSelectOptionCallback(this);
		
		// booking
		String[] arMenuBooking = new String[] { 
				"Create reservation", 
				"Display reservation",
				"Create order for reservation", 
				"Search by contact", 
				"Remove reservation" 
		};
		optsBooking = new OptionsSelector(REQ_CODE_MENU_BOOKING, arMenuBooking);
		optsBooking.setOnSelectOptionCallback(this);
		
		// setting
		// [Enhance] add Store configuration
		String[] arMenuSetting = new String[] { 
				"Edit menu items",
				"Load Data",
				"Save Data",
				"(TEST) Import sample menu",
				"(TEST) Generate order data",
				"(TEST) Generate booking data",
				"(TEST) Generate invoices",
				"(TEST) Clear data",
		};
		optsSetting = new OptionsSelector(REQ_CODE_MENU_SETTING, arMenuSetting);
		optsSetting.setOnSelectOptionCallback(this);
		
		// edit menu item
		String [] arEditMenuItem = new String[] {
				"Display menu",
				"Add menu item",
				"Add promotion item",
				"Edit menu item",
				"Delete menu item"
		};
		optsEditMenuItem = new OptionsSelector(REQ_CODE_MENU_EDIT_MENU_ITEM, arEditMenuItem);
		optsEditMenuItem.setOnSelectOptionCallback(this);
	}


	public Staff selectStaffByIndex() {
		ArrayList<Staff> staffs = Restaurant.getInstance().getStaffs();
		Staff staff=null;
		for (int i = 0; i < staffs.size(); i++) {
			staff = staffs.get(i);
			System.out.println(String.format("[%d] %-10s %-2s %-15s %-10s ", i+1,staff.getId(), staff.getGender(), staff.getName(),  staff.getJobTitle()));
		}

		String msg = String.format("Select user [%d-%d]: ", 1, staffs.size());
		int idx = Utility.inputInt(msg, 1, staffs.size());
		return staffs.get(idx-1);
	}

	public MenuItem selectMenuItemById() {
		Menu menu =Restaurant.getInstance().getMenu();
		if (menu.getItems().size() == 0) {
			System.out.println("No menu item found.");
			return null;
		}
		MenuItem item = null;
		do {
			item = menu.getItemById(Utility.inputString("Enter item id: "));
		} while (item == null);
		return item;
	}

	public Table selectTableById(ArrayList<Table> listTable) {
		Table selTable=null;
		while (selTable==null) {
			String strTableId = Utility.inputString("Enter Table ID: ");
			for (Table table : listTable) {
				if (table.getId().equalsIgnoreCase(strTableId)) {
					selTable = table;
				}
			}
		}
		return selTable;
	}

	public Booking selectBookingByIndex(ArrayList<Booking> bookings) {
		int min = 1;
		int max = bookings.size();
		String msg = String.format("Enter index [%d-%d]: ", min, max); 
		int index = Utility.inputInt(msg, min, max);
		return bookings.get( index-1 );
	}

	public Order selectOrderByTable() {
		OrderManager orderMgr = Restaurant.getInstance().getOrderManager();
		if (orderMgr.getOrders().size() ==0) {
			return null;
		}
		orderMgr.displayOrders();
		ArrayList<Table> inUseTables = orderMgr.getOccupiedTable();
		Table table = selectTableById(inUseTables);
		return orderMgr.getOrderByTable(table);
	}
	

	@Override
	public void onSelected(int requestCode, int selection) {
		switch (requestCode) {
		case REQ_CODE_MENU_MAIN:
			handleMainMenu(selection);
			break;
		case REQ_CODE_MENU_ORDER:
			handleOrderMenu(selection);
			break;
		case REQ_CODE_MENU_ORDER_ACTION:
			handleOrderActionsMenu(selection);
			break;
		case REQ_CODE_MENU_BOOKING:
			handleBookingMenu(selection);
			break;
		case REQ_CODE_MENU_SETTING:
			handleSettingMenu(selection);
			break;
		case REQ_CODE_MENU_EDIT_MENU_ITEM:
			handleEditMenuItemMenu(selection);
			break;
		default:
			System.out.println(String.format("onSelected: ReqCode(%d), sel(%d) is incomplete", requestCode, selection));
			break;
		}

	}
	

	public void handleMainMenu(int selection) {
		switch (selection) {
		case 1: // on Order selected
			optsOrder.askOptions();
			break;

		case 2: // booking selected
			optsBooking.askOptions();
			break;

		case 3: // table selected
			displayDateInputShortcuts();
			Date selectedDate = Utility.inputDate("Enter date [DD/MM/YYYY]: ");
			if (Utility.isToday(selectedDate)) {
				Restaurant.getInstance().getTableManager().displayTablesStatus();
			}else if ( selectedDate.before(new Date()) ) {
				System.out.println("Not able to display on past history");
			}else {
				Restaurant.getInstance().getTableManager().displayTablesStatus(selectedDate);
			}
			break;
			
		case 4:
			doDisplayReport();
			break;
			
		case 5: // show setting menu
			optsSetting.askOptions();
			break;

		default:
			System.out.println(String.format("[Main] Option (%d) incomplete", selection));
			break;
		}
	}
	 
	public void handleOrderMenu(int selection) {
		selectedOrder = null;
		switch (selection) {
		case 1:
			Restaurant.getInstance().getOrderManager().displayOrders();
			break;
			
		case 2:
			doAddNewWalkinOrder();
			break;

		case 3:
			if (Restaurant.getInstance().getOrderManager().getOrders().size() > 0) {
				selectedOrder = selectOrderByTable();
				optsOrderActions.askOptions();
			} else {
				System.out.println("No order record");
			}			
			break;

		default:
			System.out.println(String.format("[Order] Option (%d) incomplete", selection));
			break;
		}
	}

	public void handleOrderActionsMenu(int selection) {
		switch (selection) {
		case 1: // display detail
			selectedOrder.displayDetail();
			System.out.println();
			break;

		case 2:// add item
			Restaurant.getInstance().getMenu().displayMenu();
			doAddOrderItemToOrder(selectedOrder);
			break;
			
		case 3: // update item
			doUpdateOrderItem(selectedOrder);
			break;
		case 4: // remove item
			doDeleteOrderItem(selectedOrder);
			break;
			
		case 5: // print bill invoice
			doPrintInvoice();
			break;
		
		case 6:
			if (Utility.inputBoolean("Delete current order [y/n]? ")) {
				Restaurant.getInstance().getOrderManager().removeOrder(selectedOrder);
				selectedOrder = null;
				optsOrderActions.stopSelection();
			}
			break;
			
		default:
			System.out.println(String.format("[Order Action] Option (%d) incomplete", selection));
			break;
		}
	}

	public void handleBookingMenu(int selection) {
		Date date;
		switch (selection) {
		case 1:
			doAddNewBooking();
			break;
			
		case 2:
			if (Utility.inputBoolean("Display all reservations [y/n]? ")) {
				Restaurant.getInstance().getBookingManager().displayBookingByDate(null);
			} else {
				displayDateInputShortcuts();
				System.out.println(String.format("Valid date between %s to %s", 
						Utility.formatDate(FORMAT_DATE, Utility.getDateByShortcuts("+1d")), 
						Utility.formatDate(FORMAT_DATE, Utility.getDateByShortcuts("+1m"))));
				date = Utility.inputDate("Enter date [DD/MM/YYYY]: ");
				Restaurant.getInstance().getBookingManager().displayBookingByDate(date);
			}
			break;
			
		case 3://Create order for reservation
			doAddNewBookedOrder();
			break;
			
		case 4: //Search by contact
			String contact = Utility.inputString(String.format("Enter contact [max %d char]: ", LENGTH_BOOKING_CONTACT), LENGTH_BOOKING_CONTACT);
			Restaurant.getInstance().getBookingManager().displayBookingByContact(contact);
			break;
			
		case 5: //  Remove reservation
			doDeleteBooking();
			break;
		default: 
			System.out.println(String.format("[Order] Option (%d) incomplete", selection));
			break;
		}
	}

	public void handleSettingMenu(int selection) {
		// [Enhance] UI.SettingMenu: add ability to change store configuration or stuff
		switch (selection) {
		case 1:
			optsEditMenuItem.askOptions();
			break;
			
		case 2:
			if (DataUtility.loadData()) {
				System.out.println("Data has been loaded");
			} else {
				System.out.println("Failed to load data");
			}
			// if staff not existed 
			if (!Restaurant.getInstance().getStaffs().contains(user)) {
				user = selectStaffByIndex();
			}
			break;
			
		case 3:
			if (DataUtility.saveData()) {
				System.out.println("Data saved");
			} else {
				System.out.println("Error: something wrong, please report this issue.");
			}
			break;
			
		case 4:
			System.out.println("Warning: Generate default menu will clear away current menu data.");
			if (Utility.inputBoolean("Proceed [y/n]? ")) {
				DataUtility.loadWithDefaultMenu();
				System.out.println("Default menu generation completed\n");
			}
			break;
			
		case 5:
			Date now = new Date();
			char session = Utility.getSession(now);
			if (canMakeOrder(now, session)) {
				if (Utility.inputBoolean("Proceed [y/n]? ")) {
					DataUtility.generateOrders();
					System.out.println("Order records generation completed\n");
				}
			}
			break;
			
		case 6:
			if (Utility.inputBoolean("Proceed [y/n]? ")) {
				DataUtility.generateBookings();
				System.out.println("Bookings records generation completed\n");
			}
			break;
			
		case 7:
			if (Utility.inputBoolean("Proceed [y/n]? ")) {
				DataUtility.generateInovices();
				System.out.println("Invoice records generation completed");
			}
			System.out.println();
			break;
			
		case 8:
			System.out.println("Warning: The following data will be cleared.");
			System.out.println("Menu, Orders, Bookings, Invoices");
			if (Utility.inputBoolean("Proceed [y/n]? ")) {
				Restaurant.getInstance().clearData();
				System.out.println("Data cleared\n");
			}
			break;
			
		default:
			System.out.println("[Setting] fn("+selection+") incomplete");
			break;
		}		
	}
	

	public void handleEditMenuItemMenu(int selection) {
		switch (selection) {
		case 1:
			Restaurant.getInstance().getMenu().displayMenu();
			break;
			
		case 2: // add menu item
			doAddNewMenuItem();
			break;
			
		case 3:
			doAddNewPromoItem();
			break;
			
		case 4: // edit item
			doUpdateMenuItem();
			break;
			
		case 5: // delete item
			doDeleteMenuItem();
			break;
		default:
			System.out.println("[Edit Menu] fn("+selection+") incomplete");
			break;
		}		
	}
	

	public void displayOperationHours() {
		System.out.println("Sessions:");
		System.out.println(String.format(" 'AM'\t%d:00 to %d:00", AM_START, AM_END));
		System.out.println(String.format(" 'PM'\t%d:00 to %d:00", PM_START, PM_END));
		System.out.println(String.format("Last order/booking will be %d mins before session end", LAST_ACTION_TIME_MIN));
	}
	
	private void displayDateInputShortcuts() {
		System.out.println("Date shortcuts:");
		System.out.println( String.format(" 'today'\tget today (%s)", Utility.formatDate(FORMAT_DATE, Utility.getToday()) ));
		System.out.println(" '\u00B1nd'\t\tn for number of days before/after today");
		System.out.println( String.format("\t\t'+1d': tomorrow (%s)" , Utility.formatDate(FORMAT_DATE, Utility.getDateByShortcuts("+1d")) ));
		System.out.println( String.format("\t\t'-1d': yesterday (%s)" , Utility.formatDate(FORMAT_DATE, Utility.getDateByShortcuts("-1d")) ));
		System.out.println(" '\u00B1nm'\t\tn for number of months before/after today");
		System.out.println( String.format("\t\t'+1m': a month after today (%s)" , Utility.formatDate(FORMAT_DATE, Utility.getDateByShortcuts("+1m")) ));
		System.out.println( String.format("\t\t'+1m': a month before today (%s)" , Utility.formatDate(FORMAT_DATE, Utility.getDateByShortcuts("-1m")) ));
	}
	

	private boolean canMakeOrder(Date date, char session) {
		if (Restaurant.getInstance().getMenu().getItems().size() == 0) {
			System.out.println("Unable to create order as no menu item found");
			System.out.println("Please update menu at Setting > Menu ");
			return false;
		}
		if (session != 'a' && session != 'p' ) {
			System.out.println("Now is not operating hour, not allow to create order");
			displayOperationHours();
			System.out.println();
			return false;
		}
		if (Utility.isAfterLastActionTime(date, session)) {
			System.out.println("After last order time. not allow to create order");
			displayOperationHours();
			return false;
		}
		return true;
	}

	private void doAddNewWalkinOrder() {
		TableManager tableMgr = Restaurant.getInstance().getTableManager();
		OrderManager orderMgr = Restaurant.getInstance().getOrderManager();
		Menu menu = Restaurant.getInstance().getMenu();
		Date now = new Date();
		char session = Utility.getSession(now);
		
		if (!canMakeOrder(now, session)) {
			return;
		}
		
		int pax = Utility.inputIntAtLeast("Enter pax [>=1]: ", 1);
		Restaurant.getInstance().getTableManager().displayFreeTable(now, session, pax);
		ArrayList<Table> freeTables = tableMgr.getAvailableTables(now, session, pax);
		if (freeTables.size() == 0) {
			System.out.println("No remaining table");
			return;
		}
		Table selTable = selectTableById(freeTables);
		Order order = new Order(TYPE_WALK_IN, pax, selTable, user);
		if (Utility.inputBoolean("Start taking order [y/n]? ")) {
			menu.displayMenu();
			doAddOrderItemToOrder(order);
		}
		orderMgr.addOrder(order);
		System.out.println("Order has been created successfully.\n");
		order.displayDetail();
		System.out.println();
	}

	private void doPrintInvoice() {
		if (selectedOrder.getItems().size() == 0) {
			System.out.println("No order item found.");
			return;
		}
		
		// [Enhance] setting can toggle if menu item price included gst
		double subtotal = selectedOrder.calSubTotal();
		double servFee  = 0.01*SERVICE_CHARGE*subtotal;
		double gst = 0.01*GST*(subtotal+servFee);
		double finalPrice = Utility.round(subtotal+servFee+gst);
		
		selectedOrder.displayDetail();
		System.out.println(String.format("Service (%.1f%%)\t\t\t %6.2f", AppConstants.SERVICE_CHARGE, servFee));
		System.out.println(String.format("GST (%.1f%%)\t\t\t %6.2f", AppConstants.GST, gst));
		System.out.println(String.format("Total \t\t\t\t %6.2f", finalPrice));
		

		System.out.println();
		if (!Utility.inputBoolean("Proceed to payment [y/n]? ")) {
			return;
		}

		 // [Enhance] show hint for insufficient amount during payment
		String msg = String.format("Enter amount to be paid [>=%.2f]: ", finalPrice);
		double amtPaid = Utility.inputDoubleAtLeast(msg, finalPrice);

		
		// print invoice 
		System.out.println();
		selectedOrder.displayDetail();
		System.out.println(String.format("Service (%.1f%%)\t\t\t %6.2f", AppConstants.SERVICE_CHARGE, servFee));
		System.out.println(String.format("GST (%.1f%%)\t\t\t %6.2f", AppConstants.GST, gst));
		System.out.println(String.format("Total \t\t\t\t %6.2f", finalPrice));
		System.out.println("---------------------------------------");
		System.out.println(String.format("Payment\t\t\t\t %6.2f", amtPaid));
		System.out.println(String.format("Changes\t\t\t\t %6.2f\n", amtPaid-finalPrice));
		
		// remove from order list, add into invoice manager
		Restaurant.getInstance().getOrderManager().removeOrder(selectedOrder);
		Invoice invoice = new Invoice(selectedOrder, new Date(), user, true);
		Restaurant.getInstance().getInvoiceManager().addInvoice(invoice);
		selectedOrder = null;
		optsOrderActions.stopSelection();
	}

	private void doAddNewBooking() {
		// ask date time: tomorrow <= inputDate <= today+1M
		
		Date tmr = Utility.getTomorrow();
		Date monthLater = Utility.getMonthLater();
		displayDateInputShortcuts(); 
		System.out.println(String.format("Valid date between %s to %s", Utility.formatDate(FORMAT_DATE, tmr), Utility.formatDate(FORMAT_DATE, monthLater)));
		Date date = Utility.inputDate("Enter date of booking [DD/MM/YYYY]: ", tmr, monthLater);
		displayOperationHours();
		date = Utility.inputTimeWithinSession("Enter arrival time [HH:MM or q=quit]: ", date);
		if (date == null) {
			System.out.println("Create booking has been cancelled\n");
			return;
		}
		int pax = Utility.inputIntAtLeast("Enter pax: ", 1);
		// ask table
		TableManager tableMgr = Restaurant.getInstance().getTableManager();
		Table table;
		tableMgr.displayFreeTable(date, Utility.getSession(date), pax);
		ArrayList<Table> freeTables = tableMgr.getAvailableTables(date, Utility.getSession(date), pax);
		if (freeTables.size() > 0) {
			table = selectTableById(freeTables);
			String name    = Utility.inputString(String.format("Enter name [max %d char]: ", LENGTH_BOOKING_NAME), LENGTH_BOOKING_NAME);
			String contact = Utility.inputString(String.format("Enter contact [max %d char]: ", LENGTH_BOOKING_CONTACT), LENGTH_BOOKING_CONTACT);
			
			// add into bookingMgr
			Booking booking = new  Booking(name, contact, pax, table, date);
			Restaurant.getInstance().getBookingManager().addBooking(booking);
			System.out.println("Booking has been created successfully!");
			booking.display();
			System.out.println();
		}
		
	}

	private void doAddNewBookedOrder() {
		BookingManager bookingMgr = Restaurant.getInstance().getBookingManager();
		OrderManager orderMgr = Restaurant.getInstance().getOrderManager();
		Menu menu = Restaurant.getInstance().getMenu();
		// check operating hour
		Date date = new Date();
		char session = Utility.getSession(date);
		if (!canMakeOrder(date, session)) {
			return;
		}
		
		// display today's booking
		ArrayList<Booking> bookings = bookingMgr.getBookingsByDate(date);
		if (bookings == null || bookings.size() == 0) {
			System.out.println("No booking found");
			return;
		}
		bookingMgr.displayBookingByDate(date);
		Booking booking = selectBookingByIndex(bookings);
		
		// convert into order
		Order order = new Order(TYPE_RESERVATION, booking.getPax(), booking.getTable(), user);
		if (Utility.inputBoolean("Start taking order [y/n]? ")) {
			menu.displayMenu();
			doAddOrderItemToOrder(order);
		}
		orderMgr.addOrder(order);
		bookingMgr.removeBooking(booking);
		
		System.out.println("Order has been created successfully!");
		order.displayDetail();
		System.out.println();
	}

	private void doDeleteBooking() {
		BookingManager bookingMgr = Restaurant.getInstance().getBookingManager();
		ArrayList<Booking> bookings = bookingMgr.getBookingsByDate(null);
		if (bookings.size() == 0) {
			System.out.println("No booking found");
			return;
		}
		while(bookings.size() > 0) {
			bookingMgr.displayBookingByDate(null);
			Booking booking = selectBookingByIndex(bookings);
			String msg = String.format("Confirm delete booking on %s by %s (%s) [y/n]?", Utility.formatDate(FORMAT_DATETIME, booking.getArrivalTime()), booking.getName(), booking.getContact());
			if (Utility.inputBoolean(msg)) {
				bookingMgr.removeBooking(booking);
				bookings.remove(booking);
			}
			if (bookings.size() == 0) {
				System.out.println("No remaining booking");
				return;
			}
			if (!Utility.inputBoolean("Continue [y/n]? ")) {
				break;
			}
		}
	}
	

	private void doAddOrderItemToOrder(Order order) {
		OrderItem orderingItem;
		do {
			// ask for input for menu
			MenuItem menuItem = selectMenuItemById();
			
			// ask for quantity for order
			int quantity = 0;
			if (order.hasMenuItem(menuItem)) {
				System.out.println("Previously ordered!");
				System.out.println("Previous Quantity will be replaced by new quantity");
				System.out.println(order.getOrderItem(menuItem));
				quantity = Utility.inputIntAtLeast("Enter new quantity [>=1]: ", 1);
				
				orderingItem = order.getOrderItem(menuItem);
				orderingItem.setQuantity(quantity);
			}else {
				quantity = Utility.inputIntAtLeast("Enter quantity [>=1]: ", 1);
				
				orderingItem = new OrderItem(menuItem, quantity);
				order.getItems().add(orderingItem);
			}
		} while(Utility.inputBoolean("Continue [y/n]?"));
		System.out.println();
	}

	private void doUpdateOrderItem(Order order) {
		System.out.println("Update order item");
		if (order.getItems().size() == 0) {
			System.out.println("No items under this order");
			return;
		}
		
		order.displayListItems(true);
		int min = 1;
		int max = order.getItems().size();
		String msg = String.format("Select item by index [%d-%d]: ", min, max);
		
		// loop update quantity of item
		do {
			int idx = Utility.inputInt(msg, min, max) -1;
			OrderItem orderItem = order.getItems().get(idx);
			System.out.println(String.format("%d x %s", orderItem.getQuantity(), orderItem.getItem().getName()));
			int quantity = Utility.inputIntAtLeast("Enter new quantity [>=1]: ", 1);
			orderItem.setQuantity(quantity);
		} while (Utility.inputBoolean("Continue [y/n]?"));
		System.out.println();
	}
	
	private void doDeleteOrderItem(Order order) {
		System.out.println("Remove order item");
		order.displayListItems(true);
		if (order.getItems().size() == 0) {
			System.out.println("No item to delete");
			return;
		}

		while (order.getItems().size() > 0) {
			int min = 1;
			int max = order.getItems().size();
			String msg = String.format("Select item by index [%d-%d]: ", min, max);
			int idx = Utility.inputInt(msg, min, max) -1;
			OrderItem orderItem = order.getItems().get(idx);

			
			msg = String.format("Deleting %d x %s [y/n]: ", orderItem.getQuantity(), orderItem.getItem().getName());
			if (Utility.inputBoolean(msg)) {
				order.getItems().remove(orderItem);
			}
			if (order.getItems().size() == 0) {
				System.out.println("No remaining item");
				break;
			}
			if ( !Utility.inputBoolean("Continue [y/n]?")) {
				break;
			}
		}
	}
	
	private void doAddNewMenuItem() {
		Menu menu = Restaurant.getInstance().getMenu();
		String type = Utility.inputString(String.format("Enter type [max %d char]: ", LENGTH_MENU_ITEM_TYPE), LENGTH_MENU_ITEM_TYPE);
		String id = Utility.inputString(String.format("Enter id [max %d char]: ", LENGTH_MENU_ITEM_ID), LENGTH_MENU_ITEM_ID);
		while (menu.isExistId(id)) {
			System.out.println("Id is existed!");
			id = Utility.inputString(String.format("Enter id [max %d char]: ", LENGTH_MENU_ITEM_ID), LENGTH_MENU_ITEM_ID);
		}
		String name = Utility.inputString(String.format("Enter name [max %d char]: ", LENGTH_MENU_ITEM_NAME), LENGTH_MENU_ITEM_NAME);
		String description = Utility.inputString(String.format("Enter description [max %d char]: ", LENGTH_MENU_ITEM_DESC), LENGTH_MENU_ITEM_DESC);
		double price = Utility.inputDoubleAtLeast(String.format("Enter price [>=%.2f]: ", 0.1), 0.1);
		
		menu.addItem(new MenuItem(id.toUpperCase(), Utility.capitalize(type), name, description, price));
		System.out.println("New Item added into menu");
	}

	private void doAddNewPromoItem() {
		Menu menu = Restaurant.getInstance().getMenu();
//		String type = Utility.inputString(String.format("Enter type [max %d char.]: ", LENGTH_MENU_ITEM_TYPE), LENGTH_MENU_ITEM_TYPE);
		String type = "Promo";
		String id = Utility.inputString(String.format("Enter id [max %d char]: ", LENGTH_MENU_ITEM_ID), LENGTH_MENU_ITEM_ID);
		while (menu.isExistId(id)) {
			System.out.println("Id is existed!");
			id = Utility.inputString(String.format("Enter id [max %d char]: ", LENGTH_MENU_ITEM_ID), LENGTH_MENU_ITEM_ID);
		}
		String name = Utility.inputString(String.format("Enter name [max %d char]: ", LENGTH_MENU_ITEM_NAME), LENGTH_MENU_ITEM_NAME);
//		String description = Utility.inputString(String.format("Enter description [max %d char]: ", LENGTH_MENU_ITEM_DESC), LENGTH_MENU_ITEM_DESC);
		double price = Utility.inputDoubleAtLeast(String.format("Enter price [>=%.2f]: ", 0.1), 0.1);
		
		PromotionItem promoItem = new PromotionItem(id.toUpperCase(), Utility.capitalize(type), name, "", price);
		doAddPromoCombinations(promoItem);
		promoItem.setDescription(Arrays.deepToString(promoItem.getCombination().toArray()));
		
		menu.addItem(promoItem);
		System.out.println("New Item added into menu");
	}

	private void doAddPromoCombinations(PromotionItem promoItem) {
		Restaurant.getInstance().getMenu().displayNoPromo();		
		while(true ) {
			MenuItem item = selectMenuItemById();
			if (item instanceof PromotionItem) {
				continue;
			}
			if (promoItem.getCombination().contains(item)) {
				System.out.println(item.getName()+"is existed");
				continue;
			}
			promoItem.getCombination().add(item);
			if (!Utility.inputBoolean("Continue [y/n]?")) {
				break;
			}
		}
//		promoItem.setDescription(Arrays.deepToString(promoItem.getCombination().toArray()));
	}

	private void doUpdateMenuItem() {
		Menu menu = Restaurant.getInstance().getMenu();
		menu.displayMenu();
		MenuItem item = selectMenuItemById();
		
		System.out.println("Previous name: "+ item.getName());
		String name = Utility.inputString(String.format("Enter new name [s=skip, max %d char]: ", LENGTH_MENU_ITEM_NAME), LENGTH_MENU_ITEM_NAME);
		if (!name.equalsIgnoreCase("s")) {
			item.setName(name);
		}
		
		System.out.println("Previous description: "+ item.getDescription());
		String desc = Utility.inputString(String.format("Enter new description [s=skip, max %d char]: ", LENGTH_MENU_ITEM_DESC), LENGTH_MENU_ITEM_DESC);
		if (!desc.equalsIgnoreCase("s")) {
			item.setDescription(desc);
		}
		
		System.out.println("Previous price: "+ item.getPrice());
		double price = Utility.inputDoubleAtLeast(String.format("Enter price [>=%.2f]: ", 0.1), 0.1);
		item.setPrice(price);
		
		if (item instanceof PromotionItem) {
			if (Utility.inputBoolean("Reset combination [y/n]? ")) {
				PromotionItem promoItem = (PromotionItem) item;
				promoItem.getCombination().clear();
				doAddPromoCombinations(promoItem);
			}
		}
	}

	private void doDeleteMenuItem() {
		Menu menu = Restaurant.getInstance().getMenu();
		menu.displayMenu();
		while (menu.getItems().size() > 0) {
//			System.out.println(Arrays.deepToString(menu.getItems().toArray()));
			MenuItem item = selectMenuItemById();
			if (Utility.inputBoolean(String.format("Delete %s-%s [y/n]?", item.getId(), item.getName()))) {
				menu.removeItem(item);
			}
			if (menu.getItems().size() == 0) {
				System.out.println("No remaining item");
				break;
			}
			if (!Utility.inputBoolean("Continue [y/n]?")) {
				break;
			}
		}
	}

	private void doDisplayReport() {
		displayDateInputShortcuts();

		Date startDate = Utility.inputDate("Enter starting date [DD/MM/YYYY]: ");
		Date endDate = Utility.inputDate("Enter end date [DD/MM/YYYY]: ");
		if (endDate.before(startDate)) {
			Date temp = startDate;
			startDate = endDate;
			endDate = temp;
		}
		// change end date to end of the day
		endDate = Utility.changeTime(endDate, 23, 59);
		
		// retrieve and display report
		Report report = Restaurant.getInstance().getInvoiceManager().getReport(startDate, endDate);
		report.display();
		
	}
}
