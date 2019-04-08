package src.prompt.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import src.Event;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * WidgetController manages different utility UI windows
 */
public class WidgetController extends Controller {
    private Stage widget = new Stage();
    private Scene widgetScene = new Scene(new Pane());
    private MainUIController mainUICtrl;

    /**
     * Create the WidgetController with a reference back to the MainUIController it was created from
     * @param mainUICtrl reference to MainUIController
     */
    WidgetController(MainUIController mainUICtrl){
        this.mainUICtrl = mainUICtrl;
    }

    @FXML
    TextField eventName;
    @FXML
    TextArea eventDescription;
    @FXML
    TextField eventLocation;
    @FXML
    DatePicker eventStartDate;
    @FXML
    DatePicker eventEndDate;
    @FXML
    TextField eventStartTime;
    @FXML
    TextField eventEndTime;
    @FXML
    ComboBox frequencyCombo;
    @FXML
    TextField reminderName;
    @FXML
    DatePicker reminderDate;
    @FXML 
    TextField reminderTime;
    @FXML
    Label acc_name;
    @FXML
    Label acc_id;
    @FXML
    Label acc_timezone;
    @FXML
    Button theme_toggle;

    /**
     * Initialize the class by creating a stage to display all the widgets
     */
    @Override
    public void initializeClass() {
        widget.setWidth(500);
        widget.setAlwaysOnTop(true);
        widget.setScene(widgetScene);
        widgetScene.getStylesheets().add("/assets/stylesheet.css");
        widget.initStyle(StageStyle.UNDECORATED);
        widget.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) closeWidget();
        });
    }

    /**
     * Load the event and reminder creation UI onto the widget window and display it
     */
    @SuppressWarnings("unchecked")
    public void loadEventCreation(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource( "/assets/templates/CreateEventWidget.fxml" ));
            loader.setController(this);
            widgetScene.setRoot(loader.load());
        } catch (Exception ex){ex.printStackTrace();}
        widget.setTitle("Create an Event");
        frequencyCombo.getItems().addAll(Arrays.asList(Event.freqArray));
        frequencyCombo.getSelectionModel().select(0);
        showWidget();
    }

    /**
     * Takes the input from the fields of the Create Event window and calls necessary methods to create the event and display
     * error messages in case of invalid input
     */
    public void createEvent(){
        String event_name = eventName.getText();
        String event_desc = "";
        event_desc += eventDescription.getText();
        String event_location = "";
        event_location += eventLocation.getText();
        String event_freq = frequencyCombo.getSelectionModel().getSelectedItem().toString();
        String event_start = eventStartDate.getValue() + " " + eventStartTime.getText();
        String event_end = eventEndDate.getValue() + " " + eventEndTime.getText();
        Event newEvent = new Event(0, getSceneCtrl().getUserAccount().getID(), event_name, event_desc,
                event_start, event_end, event_location, event_freq);
        if (newEvent.isValidInterval()) {
            int event_ID = getSceneCtrl().getConn().addEvent(newEvent);
            newEvent.setID(event_ID);
            getSceneCtrl().getUserAccount().getAllEvents().addEvent(newEvent);
            errorBox("Event Created Successfully", "", Alert.AlertType.CONFIRMATION);
            mainUICtrl.CreateCalendar();
        } else errorBox("Invalid Start and End Time", "Start time must be set earlier than end time", Alert.AlertType.ERROR);
        closeWidget();
    }

    public void createReminder(){
        
    }

    /**
     * Close the widget window and remove the overlay from the main window
     */
    public void closeWidget(){
        widget.close();
        mainUICtrl.hideOverlay();
    }

    /**
     * Display the widget window and cast an overlay on the main window
     */
    public void showWidget(){
        if(widget.isShowing()) widget.close();
        widget.show();
        mainUICtrl.showOverlay();
    }

    /**
     * Display information about all events on a particular date when double clicked
     * @param onThisDay List of all events to show
     */
    public void loadEventsOnDate(ArrayList<Event> onThisDay){
        VBox root = new VBox();
        root.getStylesheets().add("/assets/stylesheet.css");
        root.setFillWidth(true);
        for(Event event: onThisDay){
            root.getChildren().add(generateEventGrid(event));
        }
        widgetScene.setRoot(root);
        showWidget();
    }

    public static GridPane generateEventGrid(Event event){
        GridPane layout = new GridPane();
        layout.getStyleClass().add("event-grid");
        Label eventName = new Label(event.getName());
        eventName.getStyleClass().add("event-name");
        Label description = new Label(event.getDescription());
        Label location = new Label(" at " + event.getLocation());
        Label startTime = new Label("From: " + event.getStartTimeString());
        Label endTime = new Label("To: " + event.getEndTimeString());
        startTime.getStyleClass().add("event-time");
        endTime.getStyleClass().add("event-time");
        layout.addRow(0, eventName, location);
        layout.add(description, 0, 1, 2, 1);
        layout.add(startTime, 0, 2, 2, 1);
        layout.add(endTime, 0, 3, 2, 1);
        return layout;
    }
    public void displayUserPanel(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource( "/assets/templates/profileMenu.fxml" ));
            loader.setController(this);
            widgetScene.setRoot(loader.load());
        } catch (Exception ex){ex.printStackTrace();}
        widget.setTitle("My account");
        acc_id.setText(""+getSceneCtrl().getUserAccount().getID());
        acc_name.setText(getSceneCtrl().getUserAccount().getName());
        acc_timezone.setText(getSceneCtrl().getUserAccount().getTimeZone());
        theme_toggle.setText(!(getSceneCtrl().getIsDark()) ? "Dark theme" : "Light theme");
        showWidget();
    }

    public void changeCSS(){
        getSceneCtrl().toggleStyleSheet();
        theme_toggle.setText(!(getSceneCtrl().getIsDark()) ? "Dark theme" : "Light theme");
    }

    public void logoutclicked(){
        closeWidget();
        getSceneCtrl().logoutUser();
    }
}