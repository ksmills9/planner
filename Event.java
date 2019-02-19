import java.time.LocalDateTime;
import java.time.format.*;

public class Event  {
    private int ID; // will be assigned
    private String eventName; // needed
    private String description;
    private LocalDateTime startTime; // needed
    private LocalDateTime endTime; // default: +1 hour
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); //formats String time to LocalDateTime
    private String location; // location of the event
    private boolean isBusy = true;
    
    
    /**
     *default constructor with no arguments 
     */
    Event(){
    	
    }
    
    /**
     * constructor that takes in a ID
     * @param id Unique id associated with event
     */
    Event(int id){
    	ID = id;
    }
    
    
    /**
     * 
     * @param id Unique id associated with event
     * @param name The name of the event
     * @param description Details about the event
     * @param start When event will start (default one hour end time)
     */
    Event(int id, String name, String description, String start){
    	ID = id;
    	eventName = name;
    	this.description = description;
    	setDateTime(start);
    }
    
    /**
     * 
     * @param id Unique to event 
     * @param name Name of the event
     * @param description details about the event
     * @param start the start time of event
     * @param end end time of the event
     */
    Event(int id, String name, String description, String start, String end){
    	ID = id;
    	eventName = name;
    	this.description = description;
    	setDateTime(start, end);
    	
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
     * This method converts a start and end time of type String of the event time
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
