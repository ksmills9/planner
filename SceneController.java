import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import java.util.HashMap;

public class SceneController {
    DatabaseConn conn = new DatabaseConn();
    private HashMap<String, Pane> sceneMap = new HashMap<>();
    FXMLLoader loader = new FXMLLoader();
    private Scene main;
	private Account userAccount;

    public SceneController(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource( "loginMenu.fxml" ));
            Pane start = loader.load();
            addScene("loginMenu", start);
            LoginController logCtrl = loader.getController();
            logCtrl.setSceneCtrl(this);

            main = new Scene(start);

            loader = new FXMLLoader(getClass().getResource( "signupMenu.fxml" ));
            addScene("signUpMenu", loader.load());
            SignUpController signCtrl = loader.getController();
            signCtrl.setSceneCtrl(this);

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void addScene(String name, Pane pane){
        sceneMap.put(name, pane);
    }

    public void activate(String name){
        main.setRoot(sceneMap.get(name));
    }

    public Scene getMain() {
        return main;
    }

    public DatabaseConn getConn() {
        return conn;
    }
	
	public void setUserAccount(Account account, AllEvents allEvents){
        this.userAccount=account;
		userAccount.setAllEvents(allEvents);
        loadMainUI();
    }

    public void setUserAccount(Account account){
        this.userAccount = account;
        loadMainUI();
    }

	void loadMainUI(){
        try {
            loader = new FXMLLoader(getClass().getResource( "MainUI.fxml" ));
            addScene("MainUI", loader.load());
            MainUIController mainCtrl = loader.getController();
            mainCtrl.setSceneCtrl(this);
        } catch (Exception ex){}
    }

	public Account getUserAccount(){
        return userAccount;
    }
	
}