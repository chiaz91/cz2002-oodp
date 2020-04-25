package app.dataManager;

import java.util.ArrayList;

import app.AppConstants;
import app.model.Staff;

public class Restaurant implements AppConstants{
	private static Restaurant instance = null;
	private ArrayList<Staff> staffs;
	private BookingManager bookingMgr;
	private OrderManager orderMgr;
	private InvoiceManager invoiceMgr;
	private TableManager tableMgr;
	private Menu menu;

	public static Restaurant getInstance() {
		// Lazy initialization
		if (instance==null) {
			instance = new Restaurant();
		}
		return instance;
	}

	private Restaurant() {
		this.staffs = new ArrayList<Staff>();
		this.bookingMgr = new BookingManager();
		this.orderMgr = new OrderManager();
		this.invoiceMgr = new InvoiceManager();
		this.tableMgr = new TableManager();
		this.menu = new Menu();
	}

	public ArrayList<Staff> getStaffs() {
		return staffs;
	}

	public void setStaffs(ArrayList<Staff> staffs) {
		this.staffs = staffs;
	}

	public BookingManager getBookingManager() {
		return bookingMgr;
	}

	public OrderManager getOrderManager() {
		return orderMgr;
	}

	public InvoiceManager getInvoiceManager() {
		return invoiceMgr;
	}

	public TableManager getTableManager() {
		return tableMgr;
	}

	public Menu getMenu() {
		return menu;
	}

	public void clearData() {
		this.bookingMgr.getBookings().clear();
		this.orderMgr.getOrders().clear();
		this.invoiceMgr.getInvoices().clear();
		this.menu.getItems().clear();
//		this.tableMgr.getTables().clear();
	}
}
