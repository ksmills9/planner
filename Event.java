import java.time.LocalDateTime;
import java.time.format.*;
import java.util.*;


/**
 * The Event class contains information and methods on all events of a paticular user.
 * @author Nandy
 */

public class Event  {
    private int ID; // will be assigned
    private String eventName; // needed
    private String description;
    private LocalDateTime startTime; // needed
    private LocalDateTime endTime; // default: +1 hour
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private String location;
    private boolean isBusy = true;
    
    
    /**
     *default constructor which takes no arguments 
     */
    Event(){
    	
    }
    
    /**
     * constructor that takes in a ID
     * @param id
     */
    Event(int id){
    	ID = id;
    }
    
    
    /**
     * change or create the event name
     * @param name The name of the event
     */
    public void setEventName(String name) {
    	eventName = name;
    }
    
    
    /**
     * @param description the details about the event
     */
    public void setDescription(String description) {
    	this.description = description;
    }
    
    
    /**
     * This method converts a start and end time of type String of the event
     * to a LocalDateTime type.
     * @param start the start time of the event
     * @param end end time of the event
     */
    public void setDateTime(String start, String end) { 
    	startTime = LocalDateTime.parse(start,formatter);
    	endTime = LocalDateTime.parse(end,formatter);
    	
    }
    
    
    /**
     * Converts start time of type String to LocalDateTime format.
     * the end time is set to one hour after the start time by default.
     * @param start
     */
    public void setDateTime(String start) {
    	startTime = LocalDateTime.parse(start,formatter);
    	endTime = startTime.plusHours(1);
    }
    
    
    /**
     * @param location The location of the event
     */
    public void setLocation(String location) {
    	this.location = location;
    }
    
    
    /**
     * @return eventName 
     */
    public String getName() {
    	return eventName;
    }
    
    /**
     * @return description
     */
    public String getDescription() {
    	return description;
    }
    
    
    /**
     * @return location     */
    public String getLocation() {
    	return location;
    }
    
    
    
    

}
