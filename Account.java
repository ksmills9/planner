
import java.util.TimeZone;

public class Account {
    private int ID;
    private String name;
    private TimeZone timeZone = TimeZone.getDefault();
    private AllEvents allEvents;

    Account(String name){
        this.name = name;
    }
    Account(String name, String timezone){
        this.name = name;
        this.timeZone = TimeZone.getTimeZone(timezone);
    }

    Account(int ID, String name, String timezone){
        this.name = name;
        this.timeZone = TimeZone.getTimeZone(timezone);
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public String getTimeZone() {
        return timeZone.toZoneId().toString();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = TimeZone.getTimeZone(timeZone);
    }

    public void setID(int ID){
        this.ID = ID;
    }

    public int getID(){
        return ID;
    }

    public AllEvents getAllEvents() {
        return allEvents;
    }

    public void setAllEvents(AllEvents allEvents) {
        this.allEvents = allEvents;
    }
}
