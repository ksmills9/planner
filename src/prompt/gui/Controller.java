package src.prompt.gui;

import javafx.scene.control.Alert;

/**
 * Controller class defines common properties of all child Controller-type classes
 */
public abstract class Controller {
    private SceneController sceneCtrl;

    /**
     * Sets the SceneController to the specified reference and invokes the initialize method
     * @param sceneCtrl reference of the SceneController instance
     */
    public void setSceneCtrl(SceneController sceneCtrl){
        this.sceneCtrl = sceneCtrl;
        initializeClass();
    }

    /**
     * Runs all the necessary command at the moment of initialization
     */
    public abstract void initializeClass();

    /**
     * Shows informational a pop-up alert box with a title and message
     * @param title title of the box
     * @param message message of the box
     * @param alertType alert type of the box
     */
    void errorBox(String title, String message, Alert.AlertType alertType){
        Alert errorAlert = new Alert(alertType);
        errorAlert.setHeaderText(title);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    /**
     * Get the SceneController of this instance
     * @return SceneController of this instance
     */
    public SceneController getSceneCtrl() {
        return sceneCtrl;
    }
}
