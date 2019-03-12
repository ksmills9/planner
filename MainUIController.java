import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.util.Duration;

import java.awt.*;

public class MainUIController {
    private SceneController sceneCtrl;
    @FXML
    Button optionBtn;

    public void setSceneCtrl(SceneController sceneCtrl) {
        this.sceneCtrl = sceneCtrl;
        initialize();
    }

    void initialize(){
    }

    public void btnHover(){
    }
    public void btnHoverNot(){
    }
}
