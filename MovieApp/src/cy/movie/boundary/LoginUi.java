package cy.movie.boundary;

import cy.movie.control.AuthenticationManager;
import cy.movie.util.InputUtility;

public class LoginUi extends BaseUi{

	@Override
	public void start() {
		AuthenticationManager authMgr = getAppData().getAuthenticationManager();
		String username = "";
		String password = "";
		
		while (true) {
			username = InputUtility.inputString("Acc : ", 1);
			if (username.equalsIgnoreCase("q")) {
				break;
			}
			password = InputUtility.inputString("Pass: ", 1);
			if (authMgr.login(username, password)) {
				System.out.println("Login Success");
				break;
			} else {
				System.out.println("Login Failed");
			}
		}
	}


}
