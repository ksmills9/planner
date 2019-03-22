package src.prompt.gui;

import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import src.*;

public class LoginController extends Controller {
	@FXML
	TextField nameField;
	@FXML
	PasswordField passField;

	/**
	 * Loads the sign up Menu
	 */
    public void loadSignUpMenu(){
        getSceneCtrl().activate("signUpMenu");
    }

    /**
     * Collects the information from the input fields and attempts to log in the user
     */
	public void loadMainMenu(){
		String accountName = "", password = "";
		
		//get accountName & password from textfields
		accountName = nameField.getText();
		password = passField.getText();
		
		Account logAccount = null;
		if(accountName != null && password != null){
			try {
				logAccount = getSceneCtrl().getConn().login(accountName, password);
			} catch (SQLException ex){
				ex.printStackTrace();
			}
			
			if(logAccount != null) {
				//loginSuccessful
				getSceneCtrl().setUserAccount(logAccount, getSceneCtrl().getConn().loadEvents(logAccount));
				getSceneCtrl().activate("mainUI");
			}
			else {
				errorBox("Login Error", "Invalid credentials.", Alert.AlertType.ERROR);
			}
		}
	}

    /**
     * Show an error message and close the program if connection to the database fails
     */
    public void initializeClass() {
        if (!getSceneCtrl().getConn().connected()) {
            errorBox("Connection error", "Could not connect to the database", Alert.AlertType.ERROR);
            System.exit(-1);
        }
    }
}
