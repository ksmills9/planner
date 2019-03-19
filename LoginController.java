import javafx.event.ActionEvent;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;

import javafx.geometry.Pos;

public class LoginController extends Controller {
	@FXML
	TextField nameField;
	@FXML
	PasswordField passField;

	/**
	 * Loads the sign up Menu
	 * @param event event that was fired
	 */
    public void loadSignUpMenu(ActionEvent event){
        getSceneCtrl().activate("signUpMenu");
    }

	public void loadMainMenu(ActionEvent event){
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

    public void initializeClass() { }
}
