import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

public class LoginController {
    private SceneController sceneCtrl;

    public void loadSignUpMenu(ActionEvent event){
        sceneCtrl.activate("signUpMenu");
    }

    public void setSceneCtrl(SceneController sceneCtrl) {
        this.sceneCtrl = sceneCtrl;
    }
}
