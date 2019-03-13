import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.time.LocalDateTime;

public class MainUIController {
    private SceneController sceneCtrl;
    private final String[] DaysofWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    @FXML
    VBox calendar_box;
    @FXML
    GridPane root;
    @FXML
    Label username;
    @FXML
    Label dateText;

    public void setSceneCtrl(SceneController sceneCtrl) {
        this.sceneCtrl = sceneCtrl;
        initialize();
    }

    void initialize(){
        CreateCalendar(LocalDateTime.now());
        setName(); setDate();
    }

    void setName(){
        username.setText(sceneCtrl.getUserAccount().getName());
    }

    void setDate(){
        LocalDateTime today = LocalDateTime.now();
        dateText.setText(today.getDayOfWeek() +", "+today.getMonth().toString() + " " + today.getDayOfMonth() + ", " + today.getYear());
    }

    void CreateCalendar(LocalDateTime dateTime){
        calendar_box.getChildren().clear();
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
        row_1.setPercentHeight(20.0);
        row_1.setVgrow(Priority.ALWAYS);
        row_2.setVgrow(Priority.ALWAYS);
        row_2.setFillHeight(true);
        table.getRowConstraints().add(row_1);
        for(int i = 0; i < 7; i++){
            Label weekday = new Label(DaysofWeek[i]);
            weekday.getStyleClass().add("dayLabel");
            table.add(weekday, i, 0);
            table.getColumnConstraints().add(column);
            table.setHalignment(weekday, HPos.CENTER);
        }

        LocalDateTime curMonth = LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), 1, 0, 0);
        LocalDateTime nextMonth = curMonth.plusMonths(1).minusDays(1);
        int DaysInMonth = nextMonth.compareTo(curMonth) + 1;
        int padding = curMonth.getDayOfWeek().getValue();
        double rows = Math.ceil((DaysInMonth + padding) / 7.0);
        row_2.setPercentHeight(80/rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 1; j < 8; j++) {
                int count = i * 7 + j - padding;
                if (count > 0 && count <= DaysInMonth) {
                    Button date = new Button(""+count);
                    date.getStyleClass().add("dateLabel");
                    date.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                    table.add(date, j-1, i+1);
                }
            }
            table.getRowConstraints().add(row_2);
        }
    }
}
