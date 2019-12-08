package cy.movie.entity;

import java.io.Serializable;

public class MovieType implements Serializable{
	private String type;
	private double price=10;
	private double pricePeak=12;
	private double priceElderly=-1;
	private double priceStudent=-1;
	
	public MovieType(String type, double price, double pricePeak, double priceElderly, double priceStudent) {
		super();
		this.type = type;
		this.price = price;
		this.pricePeak = pricePeak;
		this.priceElderly = priceElderly;
		this.priceStudent = priceStudent;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getPricePeak() {
		return pricePeak;
	}
	public void setPricePeak(double pricePeak) {
		this.pricePeak = pricePeak;
	}
	public double getPriceElderly() {
		return priceElderly;
	}
	public void setPriceElderly(double priceElderly) {
		this.priceElderly = priceElderly;
	}
	public double getPriceStudent() {
		return priceStudent;
	}
	public void setPriceStudent(double priceStudent) {
		this.priceStudent = priceStudent;
	}

	@Override
	public String toString() {
		return "MovieType [type=" + type + "]";
	}
	
}
