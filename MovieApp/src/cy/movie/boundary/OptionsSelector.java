package cy.movie.boundary;
import java.util.List;

import cy.movie.util.InputUtility;


public class OptionsSelector<T> {
	public interface OnSelectOptionCallback {
		public void onSelected(int requestCode, int selection);
	}
	
	private int requestCode;
	private String header;
	private List<T> options;
	private OnSelectOptionCallback callback;
	private boolean exit;
	

	public OptionsSelector(int requestCode, List<T> options, OnSelectOptionCallback callback) {
		this.requestCode = requestCode;
		this.options = options;
		this.exit = false;
		this.callback = callback;
	}
	
	public int getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(int requestCode) {
		this.requestCode = requestCode;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public void setOptions(List<T> options) {
		this.options = options;
	}
	
	public void setOnSelectOptionCallback(OnSelectOptionCallback callback) {
		this.callback = callback;
	}
	
	public String formatOption(T item) {
		return item.toString();
	}

	public void displayOptions() {
		if (header!=null) {
			System.out.println(header);
		}
		
		// display menu options
		for (int i = 0; i < options.size(); i++) {
			System.out.println(String.format("[%d] %s", i+1, formatOption(options.get(i))  ));
		}
		System.out.println(String.format("[%d] %s", options.size()+1, "Exit"));
	}
	
	public void askOptions() {
		this.exit = false;
		int selection = -1;
		int min = 1;
		int max = options.size()+1;
		String msg = String.format("Enter option [%d-%d]: ", min, max);
		// loop asking option
		while(true) {
			if (this.exit) {
				return;
			}
			displayOptions();
			
			selection = InputUtility.inputInt( msg, min, max);
			System.out.println();
			if (selection == options.size()+1) {
				return;
			} else {
				if (this.callback != null) {
					this.callback.onSelected(requestCode, selection);
				}
			}
		} 
	}

	public void stopSelection() {
		this.exit = true;
	}
}

