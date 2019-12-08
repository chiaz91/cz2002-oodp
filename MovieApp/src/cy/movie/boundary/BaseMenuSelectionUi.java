package cy.movie.boundary;

import java.util.ArrayList;
import java.util.Arrays;

import cy.movie.boundary.OptionsSelector.OnSelectOptionCallback;

public abstract class BaseMenuSelectionUi extends BaseUi implements OnSelectOptionCallback{
	private OptionsSelector<String> optionSelector;
	private ArrayList<String> menu;

	public BaseMenuSelectionUi() {
		menu = new ArrayList<String>();
		optionSelector = new OptionsSelector<String>(0, menu);
	}
	protected final ArrayList<String> getMenu(){
		return menu;
	}
	protected final void initMenu(ArrayList<String> menu) {
		this.menu.clear();
		this.menu.addAll(menu);
	}
	protected final void initMenu(String... menu) {
		this.menu.clear();
		this.menu.addAll(Arrays.asList(menu));
	}
	
	protected final OptionsSelector<String> getOptionSelector() {
		return optionSelector;
	}
	
}
