import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;


/**
 * Manages everything on the Main user interface found after a user logs in or signs up
 */
public class MainUIController {
    private SceneController sceneCtrl;
    private final String[] DaysofWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    private ArrayList<DateButton> dateList = new ArrayList<>();
    private LocalDateTime calendarViewDate = LocalDateTime.now();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); //formats String time to LocalDateTime
    private ArrayList<String> viewModes;

    private Stage widget;

    @FXML
    VBox calendar_box;
    @FXML
    GridPane root;
    @FXML
    Label username;
    @FXML
    Label dateText;

    @FXML
    FlowPane overlay;
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
    ComboBox viewCombo;

    /**
     * Sets the reference to the central scene controller and initializes the UI
     * @param sceneCtrl SceneController instance this controller was created from
     */
    public void setSceneCtrl(SceneController sceneCtrl) {
        this.sceneCtrl = sceneCtrl;
        initialize();
    }

    /**
     * Calls the necessary methods to initialize the view
     */
    void initialize(){
        gotoToday();
        setName(); setDate();
    }

    /**
     * Expected to manage different calendar views - work in progress
     */
    void setViewCombo(){
        viewCombo.getItems().addAll();
    }

    /**
     * Update the corresponding Label to username
     */
    void setName(){
        username.setText(sceneCtrl.getUserAccount().getName());
    }

    /**
     * Updates the calendar view to show current month
     */
    public void gotoToday(){
        calendarViewDate = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        CreateCalendar();
        boolean found = false;
        for(int i = 0; i < dateList.size() && !found; i++) {
            if(dateList.get(i).getBtnRef().getStyleClass().size() > 2){
                if (dateList.get(i).getBtnRef().getStyleClass().get(2).equals("today")) {
                    dateList.get(i).getBtnRef().requestFocus();
                    found = true;
                }
            }
        }
    }

    /**
     * Updates the corresponding Label with current date
     */
    void setDate(){
        LocalDateTime today = LocalDateTime.now();
        dateText.setText(today.getDayOfWeek().toString().substring(0,1)+ today.getDayOfWeek().toString().substring(1).toLowerCase() + ", " +
                today.getMonth().toString().substring(0,1) + today.getMonth().toString().substring(1).toLowerCase() + " " +
                today.getDayOfMonth() + ", " + today.getYear());
    }

    /**
     * Updates the calendar view with next month
     */
    public void calendarNext(){
        calendarViewDate = calendarViewDate.plusMonths(1);
        CreateCalendar();
    }

    /**
     * Updates the calender view with previous month
     */
    public void calendarPrevious(){
        calendarViewDate = calendarViewDate.minusMonths(1);
        CreateCalendar();
    }

    /**
     * Opens a new window with necessary input fields to create and event.
     */
    public void createEventWidget(){
        widget = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource( "CreateEventWidget.fxml" ));
            loader.setController(this);
            widget.setScene(new Scene(loader.load()));
        } catch (Exception ex){ex.printStackTrace();}
        widget.setTitle("Create an Event");
        widget.show();
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
        Event newEvent = new Event(0, sceneCtrl.getUserAccount().getID(), event_name, event_desc,
                event_start, event_end, event_location);
        if (newEvent.isValidInterval()) {
            int event_ID = sceneCtrl.getConn().addEvent(newEvent);
            newEvent.setID(event_ID);
            sceneCtrl.getUserAccount().getAllEvents().addEvent(newEvent);
            errorBox("Event Created Successfully", "", Alert.AlertType.CONFIRMATION);
            closeWidget();
            CreateCalendar();
        } else errorBox("Invalid Start and End Time", "Start time must be set earlier than end time", Alert.AlertType.ERROR);
    }

    /**
     * Closes the widget
     */
    public void closeWidget(){
        widget.close();
    }

    /**
     * Manages different views - work in progress
     */
    public void viewChange(){

    }

    /**
     * Shows all events passed as a parameter - work in prrgress
     * @param events events to display
     * @param title the title to display
     */
    void showEvents(ArrayList<Event> events, String title){

    }

    /**
     * Displays all dates of the month of calendarViewDate in a Calendar format. creates an instance of DateButton class for
     * every date in the view and appends it to dateList
     */
    void CreateCalendar(){
        calendar_box.getChildren().clear();
        dateList.clear();
        GridPane table = new GridPane();
        calendar_box.getChildren().add(table);
        table.toBack();
        table.getStyleClass().add("calendar-grid");
        table.setPrefHeight(620);
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(100.0/7);
        column.setHgrow(Priority.ALWAYS);
        column.setFillWidth(true);
        RowConstraints row_1 = new RowConstraints();
        RowConstraints row_2 = new RowConstraints();
        row_1.setPercentHeight(15.0);
        row_1.setVgrow(Priority.ALWAYS);
        row_2.setVgrow(Priority.ALWAYS);
        row_2.setFillHeight(true);
        table.getRowConstraints().add(row_1);
        Label viewMonth = new Label(calendarViewDate.getMonth() + ", " + calendarViewDate.getYear());
        viewMonth.getStyleClass().add("dayLabel");
        table.add(viewMonth, 0, 0, 7, 1);
        table.setHalignment(viewMonth, HPos.CENTER);
        table.getRowConstraints().add(row_1);
        for(int i = 0; i < 7; i++){
            Label weekday = new Label(DaysofWeek[i]);
            weekday.getStyleClass().add("dayLabel");
            table.add(weekday, i, 1);
            table.getColumnConstraints().add(column);
            table.setHalignment(weekday, HPos.CENTER);
        }

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime monthIterator = calendarViewDate.withDayOfMonth(1);
        LocalDateTime nextMonth = monthIterator.plusMonths(1).minusDays(1);
        int DaysInMonth = nextMonth.compareTo(monthIterator) + 1;
        int padding = monthIterator.getDayOfWeek().getValue();
        double rows = Math.ceil((DaysInMonth + padding) / 7.0);
        row_2.setPercentHeight(70/rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 1; j < 8 && !monthIterator.isAfter(nextMonth); j++) {
                padding--;
                if (padding < 0) {
                    DateButton db = new DateButton(monthIterator);
                    monthIterator = monthIterator.plusDays(1);
                    dateList.add(db);
                    table.add(db.getBtnRef(), j-1, i+2);
                }
            }
            table.getRowConstraints().add(row_2);
        }
        dateList.get(0).getBtnRef().requestFocus();
        styleevents(calendarViewDate.withDayOfMonth(1));
    }


    /**
     * Adds a red background to the dates containing an event
     * @param start first day of the month of calendarViewDate
     */
    void styleevents(LocalDateTime start){
        ArrayList<Event> eventsInView = sceneCtrl.getUserAccount().getAllEvents().getEvents(start, start.plusMonths(1));
        for(Event event : eventsInView){
            DateButton db = dateList.get(event.getStartTime().getDayOfMonth()-1);
            if(db.getBtnRef().getStyleClass().indexOf("has-event") == -1) db.getBtnRef().getStyleClass().add("has-event");
        }
    }

    /**
     * Displays alert boxes on different occasion
     * @param title the title of the alert
     * @param message the message of the alert
     * @param alertType type of the alert
     */
    void errorBox(String title, String message, Alert.AlertType alertType){
        Alert errorAlert = new Alert(alertType);
        errorAlert.setHeaderText(title);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

}

/**
 * DateButton class represents dates in a calender view
 */
class DateButton {
    private Button btnRef;
    private LocalDateTime repDate;

    /**
     * Creates a DateButton with the Date it represents and calls the method to create a button element
     * @param repDate the date to represent
     */
    DateButton(LocalDateTime repDate){
        this.repDate = repDate;
        createBtn();
    }

    /**
     * Create a button and add a blue border to it if the instance current date.
     */
    void createBtn(){
        btnRef = new Button("" +repDate.getDayOfMonth());
        btnRef.getStyleClass().add("dateLabel");
        if (repDate.isEqual(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS))) btnRef.getStyleClass().add("today");
        btnRef.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }

    /**
     * Get the reference to the button created
     * @return the button instance
     */
    public Button getBtnRef() {
        return btnRef;
    }

    /**
     * get the date the instance represents
     * @return the date this instance represents
     */
    public LocalDateTime getRepDate() {
        return repDate;
    }
}