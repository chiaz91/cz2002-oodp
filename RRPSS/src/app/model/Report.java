package app.model;

import java.util.ArrayList;
import java.util.Date;
import java.text.DecimalFormat;
import app.AppConstants;
import app.util.Utility;
import java.util.HashMap;
import java.util.Collections;
import java.util.Comparator;

public class Report implements AppConstants{
	private Date dateStart;
	private Date dateEnd;
	private ArrayList<Invoice> invoices;
	
	private int numInvoices = 0;
	private int numWalkins  = 0;
	private int numBooked   = 0;
	private int totalItemSold = 0;
	private double totalProfit = 0;
	private double totalLoss = 0;
	private int totalInvoiceLoss = 0;
	private double walkinProfit = 0;
	private double reservationProfit = 0;
	private int walkinItemSold = 0;
	private int reservationItemSold = 0;
	private ArrayList<MenuItem> listItems;
	private HashMap<String, Stats> statistics;
	

	private class Stats{
		int sold_count = 0;
		int loss_count = 0;
		double profit = 0;
		double loss = 0;
	}

	public Report(Date dateStart, Date dateEnd, ArrayList<Invoice> invoices) {
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.invoices = invoices;
		this.listItems =  new ArrayList<MenuItem>();
		this.statistics = new HashMap<String, Report.Stats>();
		
		prepareStats();
	}
	

	private void prepareStats() {
		numInvoices = invoices.size();
		
		// loop all invoices
		for (Invoice invoice : invoices) {
			if (invoice.getType().equals(TYPE_WALK_IN)) {
				numWalkins++;
			}else if (invoice.getType().equals(TYPE_RESERVATION)) {
				numBooked++;
			}
			

			// loop item list within each invoice
			for (OrderItem orderItem : invoice.getItems()) {
				MenuItem curItem = orderItem.getItem();
				if (!listItems.contains(curItem)) {
					listItems.add(curItem);
				}
				
				// check item stats is exist
				if (statistics.get(curItem.getId()) == null) {
					statistics.put(curItem.getId(), new Stats());
				}
				
				// note down stats
				Stats curItemStats = statistics.get(curItem.getId());
				if (invoice.isPaid()) {
					curItemStats.sold_count += orderItem.getQuantity();
					curItemStats.profit += curItem.getPrice()*orderItem.getQuantity();
					totalProfit += (curItem.getPrice()*orderItem.getQuantity());
					totalItemSold += orderItem.getQuantity();
					
					if (invoice.getType().equals(TYPE_WALK_IN)) {
						walkinProfit += (curItem.getPrice()*orderItem.getQuantity());
						walkinItemSold += orderItem.getQuantity();
					}
					
					else if (invoice.getType().equals(TYPE_RESERVATION)) {
						reservationProfit += (curItem.getPrice()*orderItem.getQuantity());
						reservationItemSold += orderItem.getQuantity();
					}
				} else {
					curItemStats.loss_count += orderItem.getQuantity();
					curItemStats.loss += curItem.getPrice()*orderItem.getQuantity();
					totalLoss += curItemStats.loss;
					totalInvoiceLoss++;
				}
			}
			
		}
	}
	

	public void display() {
		// only access to other classes
		if (IS_TESTING) {
			System.out.println("[TEST] Report is coming");
			Invoice curInvoice;			
			for (int i = 0; i < invoices.size(); i++) {
				System.out.println(i);
				curInvoice = invoices.get(i);
				System.out.println(String.format("[%d] %s", (i+1),curInvoice.toString()));
			}
			System.out.println();
		}		
//		
		displayDate();
		
		if (invoices.size() == 0) {
			System.out.println("No invoices records found.");
			return;
		}
		
		displayBasicStats();
		displayByType();
		displayTopQuantity();
		displayTopSales();
	}
	
	private void displayDate() {
		// if starting date and end date is the same, only prints 1 date
		if (dateStart.equals(dateEnd)) {
			System.out.println(String.format("Date: %s", Utility.formatDate(FORMAT_DATE, dateStart)));
		}
		
		else
		{
			System.out.println(String.format("Date: %s to %s", Utility.formatDate(FORMAT_DATE, dateStart),  Utility.formatDate(FORMAT_DATE, dateEnd)));
		}
		
		System.out.println();
	}
	
	private void displayBasicStats() {
		System.out.println("Total Invoices: "+numInvoices);
		System.out.println("Total items sold: "+totalItemSold);
		System.out.println(String.format("Total Profit: $%.2f", totalProfit));
		System.out.println();
		System.out.println("Invoices Lost: " + totalInvoiceLoss);
		System.out.println(String.format("Total Losses: $%.2f", totalLoss));
		System.out.println();
	}
	
	private void displayTopQuantity() {
		// sort items based on number of items sold
		Collections.sort(listItems, new Comparator<MenuItem>() {
			@Override
			public int compare(MenuItem o1, MenuItem o2) {
				Stats stats1 = statistics.get(o1.getId());
				Stats stats2 = statistics.get(o2.getId());
				return Integer.compare(stats2.sold_count, stats1.sold_count);
			}
		});
		
		// display them in ranking
		System.out.println("Ranking of items sold by Quantity:");
		System.out.println(String.format("%-6s %-20s %-6s", "idx", "Name", "# Sold"));
		for (int i = 0; i < listItems.size(); i++) {
			MenuItem curItem = listItems.get(i);
			Stats curItemStats = statistics.get(curItem.getId());
			System.out.println(String.format("%-6s %-20s %-6d", "["+(i+1)+"]", curItem.getName(), curItemStats.sold_count));
		}
		System.out.println();
	}
	
	private void displayTopSales() {
		
		// prints the item name, quantity sold, profits earned and how many % of total sales for each product
		// in descending order, starting from the item with the highest profits
		
		Collections.sort(listItems, new Comparator<MenuItem>() {

			@Override
			public int compare(MenuItem o1, MenuItem o2) {
				Stats stats1 = statistics.get(o1.getId());
				Stats stats2 = statistics.get(o2.getId());
				return Double.compare(stats2.profit, stats1.profit);
			}
		});
		
		// display them in ranking
		System.out.println("Ranking of items sold by Profits Earned:");
		System.out.println(String.format("%-6s %-19s %-20s %-6s", "idx", "Name", "Profits Earned", "Percentage of Total Sales"));
		for (int i = 0; i < listItems.size(); i++) {
			MenuItem curItem = listItems.get(i);
			Stats curItemStats = statistics.get(curItem.getId());
			System.out.println(String.format("%-6s %-20s" + "$" + "%-20.2f" + "%.2f", "["+(i+1)+"]", curItem.getName(), curItemStats.profit, (curItemStats.profit / totalProfit * 100)) + "%");
		}
		System.out.println();
	}
	

	private void displayByType(){
		//should display by random order or display by ascending/descending order
		//or should display by invoice number
		
		System.out.println("Total Invoices for walk in: "+ numWalkins);
		System.out.println("Total items sold: "+ walkinItemSold);
		System.out.println(String.format("Total Profit: $%.2f", walkinProfit));
		System.out.println();
		
		System.out.println("Total Invoices for booking: "+ numBooked);
		System.out.println("Total items sold: "+ reservationItemSold);
		System.out.println(String.format("Total Profit: $%.2f", reservationProfit));
		System.out.println();
		
	}
}
