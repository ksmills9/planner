import javafx.application.Application;
import javafx.scene.control.Alert;
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
        Alert errorAlert = new Alert(AlertType.ERROR);
    	errorAlert.setHeaderText("Database connection error");
    	errorAlert.setContentText("Could not connect to the database");
        errorAlert.showAndWait();
        //Label message = new Label("Could not connect to the database");
        //Pane pane = new Pane();
        //pane.getChildren().add(message);
        //Stage errStage = new Stage();
        //errStage.setScene(new Scene(pane));
        //errStage.show();
    }
}
