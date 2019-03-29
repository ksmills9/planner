package src.prompt;

import src.prompt.gui.SceneController;
import javafx.application.Application;
import javafx.stage.Stage;

public class GUIPrompt extends Application {
    private SceneController sceneCtrl;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setMaximized(true);
	primaryStage.getIcons().add(new Image("file:assets\\images\\planner.png"));
        primaryStage.setTitle("Planner");
        sceneCtrl = new SceneController();
        primaryStage.setScene(sceneCtrl.getMain());
        primaryStage.show();
    }
}
