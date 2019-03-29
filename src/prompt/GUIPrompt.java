package src.prompt;

import src.prompt.gui.SceneController;
import javafx.application.Application;
import javafx.stage.Stage;

public class GUIPrompt extends Application {
    private SceneController sceneCtrl;
    
    //credit: https://stackoverflow.com/questions/10121991/javafx-application-icon
	//change icon thumbnail of program to ---.png work in progress
	//primaryStage.getIcons().add(new Image("file:------"));

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setMaximized(true);
        primaryStage.setTitle("Planner");
        sceneCtrl = new SceneController();
        primaryStage.setScene(sceneCtrl.getMain());
        primaryStage.show();
    }
}
