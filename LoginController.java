import javafx.event.ActionEvent;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;

import javafx.geometry.Pos;

public class LoginController {
    private SceneController sceneCtrl;
	private DatabaseConn conn;
	@FXML
	TextField nameField;
	@FXML
	PasswordField passField;

    public void loadSignUpMenu(ActionEvent event){
        sceneCtrl.activate("signUpMenu");
		
    }
	
	public void loadMainMenu(ActionEvent event){
		String accountName = "", password = "";
		
		//get accountName & password from textfields
		accountName=nameField.getText();
		password=passField.getText();
		
		Account logAccount = null;
		if(accountName!=null && password!=null){
			conn = sceneCtrl.getConn();
			try {
				logAccount = conn.login(accountName, password);
			} catch (SQLException ex){
				ex.printStackTrace();
			}
			
			if(logAccount != null) {
				//loginSuccessful
				sceneCtrl.setUserAccount(logAccount, conn.loadEvents(logAccount));
				sceneCtrl.activate("MainUI");
			}
			else {
				//handle incorrect name/pass
				Stage errorStage=new Stage();
				VBox errorPrompt=new VBox();
				Label errorLabel=new Label();
				
				errorLabel.setText("Invalid credentials.");
				errorPrompt.getChildren().add(errorLabel);
				errorPrompt.setAlignment(Pos.CENTER);
				
				Scene scene=new Scene(errorPrompt, 400,200);
				
				errorStage.setTitle("Login Error");
				errorStage.setScene(scene);
				errorStage.show();	
			}

		}
	}

    public void setSceneCtrl(SceneController sceneCtrl) {
        this.sceneCtrl = sceneCtrl;
    }
}
