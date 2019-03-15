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

    public void setSceneCtrl(SceneController sceneCtrl) {
        this.sceneCtrl = sceneCtrl;
        initialize();
    }

    void initialize(){
        gotoToday();
        setName(); setDate();
    }

    void setViewCombo(){
        viewCombo.getItems().addAll();
    }

    void setName(){
        username.setText(sceneCtrl.getUserAccount().getName());
    }

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

    void setDate(){
        LocalDateTime today = LocalDateTime.now();
        dateText.setText(today.getDayOfWeek().toString().substring(0,1)+ today.getDayOfWeek().toString().substring(1).toLowerCase() + ", " +
                today.getMonth().toString().substring(0,1) + today.getMonth().toString().substring(1).toLowerCase() + " " +
                today.getDayOfMonth() + ", " + today.getYear());
    }

    public void calendarNext(){
        calendarViewDate = calendarViewDate.plusMonths(1);
        CreateCalendar();
    }

    public void calendarPrevious(){
        calendarViewDate = calendarViewDate.minusMonths(1);
        CreateCalendar();
    }

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

    public void createEvent(){
        String event_name = eventName.getText();
        String event_desc = eventDescription.getText();
        String event_location = eventLocation.getText();
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
        } else errorBox("Invalid Start and End Time", "Start time must be set earlier than end time", Alert.AlertType.ERROR);
    }

    public void closeWidget(){
        widget.close();
    }


    public void viewChange(){

    }

    void showEvents(ArrayList<Event> events, String title){

    }

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

    void styleevents(LocalDateTime start){
        ArrayList<Event> eventsInView = sceneCtrl.getUserAccount().getAllEvents().getEvents(start, start.plusMonths(1));
        for(Event event : eventsInView){
            DateButton db = dateList.get(event.getStartTime().getDayOfMonth()-1);
            if(db.getBtnRef().getStyleClass().indexOf("has-event") == -1) db.getBtnRef().getStyleClass().add("has-event");
        }
    }

    void errorBox(String title, String message, Alert.AlertType alertType){
        Alert errorAlert = new Alert(alertType);
        errorAlert.setHeaderText(title);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

}

class DateButton {
    private Button btnRef;
    private LocalDateTime repDate;

    DateButton(LocalDateTime repDate){
        this.repDate = repDate;
        btnRef = new Button("" +repDate.getDayOfMonth());
        btnRef.getStyleClass().add("dateLabel");
        if (repDate.isEqual(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS))) btnRef.getStyleClass().add("today");
        btnRef.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }

    public Button getBtnRef() {
        return btnRef;
    }

    public LocalDateTime getRepDate() {
        return repDate;
    }
}