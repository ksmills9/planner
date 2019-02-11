import java.time.LocalDateTime;

public class Event {
    private int ID; // will be assigned
    private String eventName; // needed
    private String description;
    private LocalDateTime startTime; // needed
    private LocalDateTime endTime; // default: +1 hour
    private String location;
    private boolean isBusy = true;

}
