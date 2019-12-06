package cy.movie.boundary;

import java.util.ArrayList;

import cy.movie.boundary.OptionsSelector.OnSelectOptionCallback;

public abstract class BaseMenuSelectionUi extends BaseUi implements OnSelectOptionCallback{
	private OptionsSelector<String> optionSelector;
	private ArrayList<String> menu;

	public BaseMenuSelectionUi() {
		menu = new ArrayList<String>();
		initMenu(menu);
		optionSelector = new OptionsSelector<String>(0, menu, this);
	}
	protected final ArrayList<String> getMenu(){
		return menu;
	}
	protected abstract void initMenu(ArrayList<String> menu);
	
	protected final OptionsSelector<String> getOptionSelector() {
		return optionSelector;
	}
	


}
