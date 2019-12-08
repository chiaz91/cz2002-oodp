package cy.movie.boundary;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cy.movie.util.InputUtility;


public class OptionsSelector<T> {
	public interface OnSelectOptionCallback {
		public void onSelected(int requestCode, int selection);
	}
	
	private int requestCode;
	private String header;
	private List<T> options;
	private boolean exit;
	

	public OptionsSelector(int requestCode, List<T> options) {
		this.requestCode = requestCode;
		this.options = options;
		this.exit = false;
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
	public List<T> getOptions() {
		return this.options;
	}

	public void setOptions(List<T> options) {
		this.options = options;
	}
	
	@SuppressWarnings("unchecked")
	public void setOptions(T... options) {
		this.options = new ArrayList<T>();
		this.options.addAll( Arrays.asList(options) );
	}
	
	
	public String formatOption(T item) {
		return item.toString();
	}

	public void displayOptions(boolean enableQuit) {
		if (header!=null) {
			System.out.println(header);
		}
		
		// display menu options
		for (int i = 0; i < options.size(); i++) {
			System.out.println(String.format("[%d] %s", i+1, formatOption(options.get(i))  ));
		}
		if (enableQuit) {
			System.out.println(String.format("[%d] %s", options.size()+1, "Exit"));
		}
	}
	
	
	public T askOptions(boolean enableQuit) {
		T result = null;
		if (options.size() == 0) {
			System.out.println("Nothing to display");
		} else {
			int selection = -1;
			int min = 1;
			int max = options.size()+1;
			String msg = String.format("Enter option [%d-%d]: ", min, max);
			// loop asking option
			while(result==null) {
				displayOptions(enableQuit);
				
				selection = InputUtility.inputInt( msg, min, max);
				System.out.println();
				if (enableQuit && selection == options.size()+1) {
					break;
				} else {
					result = options.get(selection-1);
				}
			} 
		}
		return result;
	}
	
	public void askOptionsRepeatedly(String hint, boolean enableQuit, OnSelectOptionCallback callback) {
		if (options.size() == 0) {
			System.out.println("Nothing to display");
			return;
		}
		this.exit = false;
		int selection = -1;
		int min = 1;
		int max = options.size()+1;
		// loop asking option
		while(true) {
			if (this.exit) {
				return;
			}
			displayOptions(true);
			
			selection = InputUtility.inputInt( hint, min, max);
			System.out.println();
			if (selection == options.size()+1) {
				return;
			} else {
				if (callback != null) {
					callback.onSelected(requestCode, selection);
				}
			}
		} 
	}
	
	public void askOptionsRepeatedly(OnSelectOptionCallback callback) {
		int min = 1;
		int max = options.size()+1;
		String msg = String.format("Enter option [%d-%d]: ", min, max);
		askOptionsRepeatedly( msg, true, callback);
	}

	public void stopSelection() {
		this.exit = true;
	}
}

