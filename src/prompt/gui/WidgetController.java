package src.prompt.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import src.Event;

import java.time.LocalDate;
import java.util.ArrayList;

public class WidgetController extends Controller {
    private Stage widget = new Stage();
    private Scene widgetScene = new Scene(new Pane());
    private MainUIController mainUICtrl;

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

    @Override
    public void initializeClass() {
        widget.setWidth(500);
        widget.setAlwaysOnTop(true);
        widget.setScene(widgetScene);
        widgetScene.getStylesheets().add("/assets/stylesheet.css");
    }

    public void loadEventCreation(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource( "/assets/templates/CreateEventWidget.fxml" ));
            loader.setController(this);
            widgetScene.setRoot(loader.load());
        } catch (Exception ex){ex.printStackTrace();}
        widget.setTitle("Create an Event");
        showWidget();
    }

    /**
     * Takes the input from the fields of the Create Event window and calls necessary methods to create the event and display
     * error messages in case of invalid input
     */
    public void createEvent(){
        String event_name = eventName.getText();
        String event_desc = " ";
        event_desc = eventDescription.getText();
        String event_location = " ";
        event_location = eventLocation.getText();
        String event_start = eventStartDate.getValue() + " " + eventStartTime.getText();
        String event_end = eventEndDate.getValue() + " " + eventEndTime.getText();
        Event newEvent = new Event(0, getSceneCtrl().getUserAccount().getID(), event_name, event_desc,
                event_start, event_end, event_location);
        if (newEvent.isValidInterval()) {
            int event_ID = getSceneCtrl().getConn().addEvent(newEvent);
            newEvent.setID(event_ID);
            getSceneCtrl().getUserAccount().getAllEvents().addEvent(newEvent);
            errorBox("Event Created Successfully", "", Alert.AlertType.CONFIRMATION);
            mainUICtrl.CreateCalendar();
        } else errorBox("Invalid Start and End Time", "Start time must be set earlier than end time", Alert.AlertType.ERROR);
    }
    public void closeWidget(){
        widget.close();
    }

    public void showWidget(){
        if(widget.isShowing()) widget.close();
        widget.showAndWait();
    }

    public void loadEventsOnDate(ArrayList<Event> onThisDay, LocalDate repDate){
        VBox root = new VBox();
        root.getStylesheets().add("/assets/stylesheet.css");
        root.setFillWidth(true);
        for(Event event: onThisDay){
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
            root.getChildren().add(layout);
        }
        widgetScene.setRoot(root);
        widget.setTitle("Events on " + repDate.toString());
        showWidget();
    }
}