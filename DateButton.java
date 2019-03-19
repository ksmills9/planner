import javafx.scene.control.Button;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateButton {
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
