package cy.movie.boundary;

import cy.movie.entity.MovieGoer;
import cy.movie.entity.Transaction;
import cy.movie.entity.User;

public class DisplayBookingHistory extends BaseMenuSelectionUi{
	OptionsSelector<Transaction> transactionSelector;
	
	public DisplayBookingHistory() {
		getOptionSelector().setRequestCode(CODE_REQ_TRANSACTION_OPERTATION);
		User user = getSessionManager().getUser();
		if (user instanceof MovieGoer) {
			MovieGoer member = (MovieGoer) user;
			transactionSelector = new OptionsSelector<Transaction>(CODE_REQ_TRANSACTION_SELECTION, member.getTransactions()) {
				@Override
				public String formatOption(Transaction item) {
					return item.getTid();
				}
			};
			transactionSelector.askOptionsRepeatedly(this);
		}

	}
	

	@Override
	public void start() {
		initMenu("Display Detail");
		transactionSelector.askOptionsRepeatedly(this);
	}


	@Override
	public void onSelected(int requestCode, int selection) {
		if (requestCode == CODE_REQ_TRANSACTION_SELECTION) {
			Transaction selected = transactionSelector.getOptions().get(selection-1);
			getSessionManager().getSessionData().put("selected_transaction", selected);
			getOptionSelector().askOptionsRepeatedly(this);
		} else if (requestCode == CODE_REQ_TRANSACTION_OPERTATION){ 
			System.out.println("imeplementing");
			// TODO: complete implementation for check history
			// handle operation
//			switch (selection) {
//			case 1: // display details
//				break;
//			default:
//				break;
//			}
		}
	}


}
