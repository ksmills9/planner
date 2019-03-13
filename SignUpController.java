import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TimeZone;

public class SignUpController {
    SceneController sceneCtrl;
    @FXML
    ComboBox timeZoneCombo;
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    Button signupBtn;

    @SuppressWarnings("unchecked")
    public void initialize() {
        ArrayList<String> tzList = new ArrayList(Arrays.asList(TimeZone.getAvailableIDs()));
        timeZoneCombo.getItems().setAll(tzList);
        int x = tzList.indexOf(TimeZone.getDefault().getID());
        timeZoneCombo.getSelectionModel().select(x);
    }

    public void setSceneCtrl(SceneController sceneCtrl) {
        initialize();
        this.sceneCtrl = sceneCtrl;
    }

    public void signupAccount(){
        String accountname = username.getText();
        String accountPassword = password.getText();
        String timezone = timeZoneCombo.getSelectionModel().getSelectedItem().toString();
        boolean error = false;
        if(accountname.length() < 3 || accountname.length() > 25){
            error = true;
            errorBox("Account name", "Account name must be within 3-25 characters", Alert.AlertType.INFORMATION);
        }
        if(accountPassword.length() < 8 || accountPassword.length() > 24){
            error = true;
            errorBox("Account password", "Account password must be within 8-24 characters", Alert.AlertType.INFORMATION);
        }
        if(!error) proceedToSignUp(accountname, accountPassword, timezone);
    }

    void proceedToSignUp(String accountname, String accountPassword, String timezone){
        Account newUser = new Account(accountname, timezone);

        int ID = sceneCtrl.getConn().addAccount(newUser, accountPassword);
        if(ID == -1) {
            errorBox("Something went wrong", "Could not create your account!", Alert.AlertType.ERROR);
            System.exit(-1);
        }
        else {
            newUser.setID(ID);
            sceneCtrl.setUserAccount(newUser);
            sceneCtrl.activate("MainUI");
        }
    }

    void errorBox(String title, String message, Alert.AlertType alertType){
        Alert errorAlert = new Alert(alertType);
        errorAlert.setHeaderText(title);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }
}
