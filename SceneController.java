import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import java.util.HashMap;

/**
 * SceneController manages different scenes/UI on the main window for different purposes
 */
public class SceneController {
    DatabaseConn conn = new DatabaseConn();
    private HashMap<String, Pane> sceneMap = new HashMap<>();
    FXMLLoader loader = new FXMLLoader();
    private Scene main;
	private Account userAccount;

    /**
     * Create the SceneController by loading the login and signup menu and displaying the login menu in the beginning, also
     * establishes a connection to the database.
     */
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

    /**
     * Adds a Pane element to the hash map with an appropriate name
     * @param name the name of the pane
     * @param pane the pane to add
     */
    public void addScene(String name, Pane pane){
        sceneMap.put(name, pane);
    }

    /**
     * Sets the pane represented by the name as the main pane, essentially displaying it on the window
     * @param name the name of the pane to display
     */
    public void activate(String name){
        main.setRoot(sceneMap.get(name));
    }

    /**
     * Get the reference to the the main scene of the instance
     * @return reference to the the main scene
     */
    public Scene getMain() {
        return main;
    }

    /**
     * Get a reference to the DatabaseConn instance of this object
     * @return a reference to the DatabaseConn
     */
    public DatabaseConn getConn() {
        return conn;
    }

    /**
     * Sets the user account to the specified Account instance and adds an appropriate AllEvents account to the user account
     * @param account the account to set
     * @param allEvents the AllEvents instance to add to user account
     */
	public void setUserAccount(Account account, AllEvents allEvents){
        this.userAccount=account;
		userAccount.setAllEvents(allEvents);
        loadMainUI();
    }

    /**
     * Sets the user account to the specified Account instance
     * @param account the account to set
     */

    public void setUserAccount(Account account){
        this.userAccount = account;
        loadMainUI();
    }

    /**
     * Loads the main ui when the user logs in
     */
	void loadMainUI(){
        try {
            loader = new FXMLLoader(getClass().getResource( "MainUI.fxml" ));
            addScene("MainUI", loader.load());
            MainUIController mainCtrl = loader.getController();
            mainCtrl.setSceneCtrl(this);
        } catch (Exception ex){}
    }

    /**
     * Get the user account
     * @return user account of this instance
     */
	public Account getUserAccount(){
        return userAccount;
    }
}