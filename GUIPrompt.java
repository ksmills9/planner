import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GUIPrompt extends Application {
    private Account userAccount;
    private SceneController sceneCtrl;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setMaximized(true);
        primaryStage.setTitle("Planner");
        sceneCtrl = new SceneController();
        primaryStage.setScene(sceneCtrl.getMain());
        primaryStage.show();
        if (!sceneCtrl.getConn().connected()) {
            connError();
            primaryStage.close();
        }
    }

    void connError(){
        Label message = new Label("Could not connect to the database");
        Pane pane = new Pane();
        pane.getChildren().add(message);
        Stage errStage = new Stage();
        errStage.setScene(new Scene(pane));
        errStage.show();
    }
}
