import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUIPrompt extends Application {
    DatabaseConn conn;
    private Account userAccount;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setMaximized(true);
        primaryStage.setTitle("Planner");
//        loginMenu(primaryStage);
        goToSignUp(primaryStage);
    }

    public void goToSignUp(Stage stage)throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("signupMenu.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void loginMenu(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("loginMenu.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
