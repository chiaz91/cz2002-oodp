package cy.movie.entity;

import java.io.Serializable;
import java.util.Set;

public class Cinema implements Serializable{
	private String code; // label for hall, can be number
	private String type; // type of hall 
	private double price;
	
	// for layout
	private int rows;
	private int cols;
	private Set<Integer> stairways;
	
	public Cinema(String code, String type, double price, int rows, int cols, Set<Integer> stairways) {
		super();
		this.code = code;
		this.type = type;
		this.price = price;
		this.rows = rows;
		this.cols = cols;
		this.stairways = stairways;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public Set<Integer> getStairways() {
		return stairways;
	}

	public void setStairways(Set<Integer> stairways) {
		this.stairways = stairways;
	}
	
	
	
	
}
