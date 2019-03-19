import javafx.scene.control.Alert;

public abstract class Controller {
    private SceneController sceneCtrl;

    public void setSceneCtrl(SceneController sceneCtrl){
        this.sceneCtrl = sceneCtrl;
        System.out.println(sceneCtrl);
        initializeClass();
    }
    public abstract void initializeClass();

    void errorBox(String title, String message, Alert.AlertType alertType){
        Alert errorAlert = new Alert(alertType);
        errorAlert.setHeaderText(title);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    public SceneController getSceneCtrl() {
        return sceneCtrl;
    }
}
