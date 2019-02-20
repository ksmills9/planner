
import java.util.TimeZone;

public class Account {
    private int ID;
    private String name;
    private TimeZone timeZone;
    private AllEvents allEvents;

    Account(String name){
        this.name = name;
    }
    Account(String name, String timezone){
        this.name = name;
        this.timeZone = TimeZone.getTimeZone(timezone);
    }

    Account(int ID, String name, String timezone, String events){
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

}
