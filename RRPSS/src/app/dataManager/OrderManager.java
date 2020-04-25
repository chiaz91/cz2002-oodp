package app.dataManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import app.AppConstants;
import app.model.Invoice;
import app.model.Order;
import app.model.Table;
import app.util.Utility;

public class OrderManager implements AppConstants{
	private ArrayList<Order> orders;

	public OrderManager() {
		this.orders = new ArrayList<Order>();
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
		clearExpiredOrder();
		sort();
	}

	public void addOrder(Order order) {
		if (!orders.contains(order)) {
			orders.add(order);
		}
	}

	public void removeOrder(Order order) {
		if (orders.contains(order)) {
			orders.remove(order);
		}
	}

	public Order getOrderByTable(Table table) {
		clearExpiredOrder();
		
		for (Order order : orders) {
			if (order.getTable().equals(table)) {
				return order;
			}
		}
		return null;
	} 

	public ArrayList<Table> getOccupiedTable(){
		clearExpiredOrder();
		
		ArrayList<Table> list = new ArrayList<Table>();
		for (Order order : orders) {
			list.add(order.getTable() );
		}
		return list;
	}

	public void clearExpiredOrder() {
		Order order;
		Invoice invoice;
		int count = 0;
		Date now   = new Date();
		Date today = Utility.getToday();
		Date endAM = Utility.changeTime(today, AM_END, ORDER_EXPIRE_TIME_MIN);
		Date endPM = Utility.changeTime(today, PM_END, ORDER_EXPIRE_TIME_MIN);
		for (int i = orders.size()-1; i >= 0; i--) {
			boolean isExpired = false;
			order = orders.get(i);
			if (order.getCreateTime().before( today )) {
				isExpired = true;
			} else if (now.after(endAM) && order.getCreateTime().before(endAM) )  {
				isExpired = true;
			} else if (now.after(endPM) && order.getCreateTime().before(endPM) )  {
				isExpired = true;
			}
			if (isExpired) {
				//System.out.println(String.format("[Test] OrderMgr.clearExpired: isExpired %s", order));
				System.out.println(String.format(order.getType() + " Orders " + "for " + order.getTable() +" has expired due to the closing time session."));
					
				orders.remove(order);
				invoice = new Invoice(order, order.getCreateTime(), order.getCreator(), false);
				Restaurant.getInstance().getInvoiceManager().addInvoice( invoice );
				count++;
			}
		}
		if (count>0) {
			//System.out.println(String.format("[Test] OrderMgr.clearExpired: count(%d)", count));
			System.out.println("There are Total of " + count + " expired orders.\n");
		}
	}

	public void displayOrders() {
		clearExpiredOrder(); 
		
		Order order;
		if (orders.size() == 0) {
				System.out.println("No order record found");
		}else {
			System.out.println(String.format("%-4s %-5s %-3s %-5s %-8s", "No.", "Table", "Pax", "Items", "Subtotal"));
			for (int i=0; i<orders.size(); i++) {
				order = orders.get(i);
				System.out.println(String.format("%-4s %-5s %-3s %-5s %8.2f",
						"["+(i+1)+"]",
						order.getTable().getId(),
						order.getPax(),
						order.getTotalItems(),
						order.calSubTotal()
				));
			}
			System.out.println();
		}
	}

	public void sort() {
		Collections.sort(this.orders);
	}
	
}
