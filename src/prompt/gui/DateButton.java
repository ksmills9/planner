package src.prompt.gui;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import src.Event;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class DateButton {
    private Button btnRef;
    private MainUIController mainUICtrl;
    private LocalDateTime repDate;
    private ArrayList<Event> onThisDay = new ArrayList<>();

    /**
     * Creates a DateButton with the Date it represents and calls the method to create a button element
     * @param repDate the date to represent
     */
    DateButton(LocalDateTime repDate, MainUIController mainUICtrl){
        this.repDate = repDate;
        this.mainUICtrl = mainUICtrl;
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
        btnRef.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getClickCount() == 2 && onThisDay.size() > 0) showEventsOnDate();
            }
        });
    }

    /**
     * Displays a widget with all events of a particular date listed
     */
    public void showEventsOnDate(){
        mainUICtrl.getWidget().loadEventsOnDate(onThisDay);
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

    /**
     * Add an event to the list of all events on the date it represents
     * @param event the event to add
     */
    public void addEventOnDate(Event event){
        onThisDay.add(event);
    }
}
