package cy.movie.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

public class Cinema implements Serializable{
	private String code; // label for hall, can be number
	private String type; // type of hall 
	private double price;
	
	// for layout
	private int rows;
	private int cols;
	private ArrayList<Integer> stairways;
	
	public Cinema(String code, String type, double price, int rows, int cols, ArrayList<Integer> stairways) {
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

	public ArrayList<Integer> getStairways() {
		return stairways;
	}

	public void setStairways(ArrayList<Integer> stairways) {
		this.stairways = stairways;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cinema other = (Cinema) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cinema [" + code + "]";
	}
	
	
	
	
}
