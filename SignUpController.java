import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class SignUpController implements Initializable {
    SceneController sceneCtrl;
    @FXML
    ComboBox timeZoneCombo;

    @Override
    @SuppressWarnings("unchecked")
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeZoneCombo.getItems().addAll(TimeZone.getAvailableIDs());
    }

    public void setSceneCtrl(SceneController sceneCtrl) {
        this.sceneCtrl = sceneCtrl;
    }

}
