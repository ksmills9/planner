package src.prompt.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import src.*;
import src.prompt.ConsolePrompt;


/**
 * Manages everything on the Main user interface found after a user logs in or signs up
 */
public class MainUIController extends Controller {
    private ArrayList<DateButton> dateList = new ArrayList<>();
    private LocalDateTime calendarViewDate = LocalDateTime.now();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); //formats String time to LocalDateTime
    private ArrayList<String> viewModes;
    private WidgetController widget = new WidgetController(this);

    @FXML
    VBox calendar_box;
    @FXML
    GridPane root;
    @FXML
    Label username;
    @FXML
    Label dateText;
    @FXML
    BorderPane overlay;
    @FXML
    ComboBox viewCombo;

    /**
     * Calls the necessary methods to initialize the view
     */
    public void initializeClass(){
        gotoToday();
        setName(); setDate();
        widget.setSceneCtrl(getSceneCtrl());
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
        username.setText(getSceneCtrl().getUserAccount().getName());
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

    public void createEventWidget(){
        widget.loadEventCreation();
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
            Label weekday = new Label(ConsolePrompt.DaysofWeek[i]);
            weekday.getStyleClass().add("dayLabel");
            table.add(weekday, i, 1);
            table.getColumnConstraints().add(column);
            table.setHalignment(weekday, HPos.CENTER);
        }

        LocalDateTime monthIterator = calendarViewDate.withDayOfMonth(1);
        LocalDateTime nextMonth = monthIterator.plusMonths(1).minusDays(1);
        int DaysInMonth = nextMonth.compareTo(monthIterator) + 1;
        int padding = monthIterator.getDayOfWeek().getValue()%7;
        double rows = Math.ceil((DaysInMonth + padding) / 7.0);
        row_2.setPercentHeight(70/rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 1; j < 8 && !monthIterator.isAfter(nextMonth); j++) {
                padding--;
                if (padding < 0) {
                    DateButton db = new DateButton(monthIterator, this);
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
        ArrayList<Event> eventsInView = getSceneCtrl().getUserAccount().getAllEvents().getEvents(start, start.plusMonths(1));
        for(Event event : eventsInView){
            DateButton db = dateList.get(event.getStartTime().getDayOfMonth()-1);
            db.addEventOnDate(event);
            if(db.getBtnRef().getStyleClass().indexOf("has-event") == -1) db.getBtnRef().getStyleClass().add("has-event");
        }
    }

    public WidgetController getWidget() {
        return widget;
    }

    public void showOverlay(){
        overlay.toFront();
    }

    public void hideOverlay(){
        overlay.toBack();
    }
    
    public void loadTimer() {
    	getSceneCtrl().activate("TimerView");
    }
}
