import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import java.util.HashMap;

/**
 * SceneController manages different scenes/UI on the main window for different purposes
 */
public class SceneController {
    private DatabaseConn conn = new DatabaseConn();
    private HashMap<String, Pane> sceneMap = new HashMap<>();
    private Scene main;
	private Account userAccount;

    /**
     * Create the SceneController by loading the login and signup menu and displaying the login menu in the beginning, also
     * establishes a connection to the database.
     */
    public SceneController(){
        Pane start = loadFxml("loginMenu.fxml", "loginMenu", new LoginController());

        main = new Scene(start);
        main.getStylesheets().add("stylesheet.css");

        loadFxml("signupMenu.fxml", "signUpMenu", new SignUpController());
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
        userAccount = account;
		userAccount.setAllEvents(allEvents);
		if(userAccount != null) loadMainUI();
    }

    /**
     * Sets the user account to the specified Account instance
     * @param account the account to set
     */

    public void setUserAccount(Account account){
        userAccount = account;
        loadMainUI();
    }

    /**
     * Get the user account
     * @return user account of this instance
     */
	public Account getUserAccount(){
        return userAccount;
    }

    /**
     * Load the main UI
     */
    public void loadMainUI(){
        loadFxml("mainUI.fxml", "mainUI", new MainUIController());
    }

    /**
     * Load a fxml file and assign it a controller and add it to the hashmap with an appropriate title
     * @param url path to the fxml file
     * @param hashName name of the pane
     * @param controller controller to assign
     * @return the <code>Pane</code> object the fxml file was loaded to
     */
    public Pane loadFxml(String url, String hashName, Controller controller){
	    try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
            Pane loaded = loader.load();
            addScene(hashName, loaded);
            controller = loader.getController();
            controller.setSceneCtrl(this);
            return loaded;
        } catch (Exception ex){ex.printStackTrace(); return null;}
    }
}