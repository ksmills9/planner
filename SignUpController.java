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

    /**
     * Initialize the UI by adding options to the Timezone combo box with valid IDs
     */
    @SuppressWarnings("unchecked")
    public void initialize() {
        ArrayList<String> tzList = new ArrayList(Arrays.asList(TimeZone.getAvailableIDs()));
        timeZoneCombo.getItems().setAll(tzList);
        int x = tzList.indexOf(TimeZone.getDefault().getID());
        timeZoneCombo.getSelectionModel().select(x);
    }

    /**
     * Set the SceneController reference and initialize the UI
     * @param sceneCtrl the SceneController this instance was created from
     */
    public void setSceneCtrl(SceneController sceneCtrl) {
        initialize();
        this.sceneCtrl = sceneCtrl;
    }

    /**
     * Takes the inputs from respective fields, checks for errors and proceeds to create the the account
     */
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

    /**
     * Call methods to create the account and set it as the user account for the SceneController
     * @param accountname name of the account
     * @param accountPassword password of the account
     * @param timezone timezone of the account
     */
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

    /**
     * Display an alert message in different situation
     * @param title title of the alert
     * @param message message of the alert
     * @param alertType type of the alert
     */
    void errorBox(String title, String message, Alert.AlertType alertType){
        Alert errorAlert = new Alert(alertType);
        errorAlert.setHeaderText(title);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }
}
