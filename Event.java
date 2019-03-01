import java.time.LocalDateTime;
import java.time.format.*;

/**
* Event class that creates a new event for one user
*/
public class Event  {
    private int ID;
    private int userID;
    private String eventName;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); //formats String time to LocalDateTime
    private String location;

    /**
     * Create an event without end time - the end time will be set to one hour after start time
     * @param name name of the event
     * @param description details about the event
     * @param start the start time of event
     */
    Event(String name, String description, String start){
    	eventName = name;
    	this.description = description;
    	setDateTime(start);
    }
    
    /**
     * Create an event will all the details provided
     * @param name Name of the event
     * @param description details about the event
     * @param start the start time of event
     * @param end end time of the event
     */
    Event(int ID, int userID, String name, String description, String start, String end, String location){
        this.ID = ID;
    	this.userID = userID;
    	eventName = name;
    	this.description = description;
    	setDateTime(start, end);
    	this.location = location;
    }

    /**
     * Copy Constructor;
     * @param event
     */
    Event(Event event){
    	this.ID = event.getID();
    	this.userID = event.userID;
    	this.eventName = event.getName();
    	this.description = event.description;
    	this.startTime = event.startTime;
    	this.endTime = event.endTime;
    	this.location = event.location;
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
     * @param start the start time of event
     */
    public void setDateTime(String start) {
    	startTime = LocalDateTime.parse(start,formatter);
    	endTime = startTime.plusHours(1);
    }

    /**
     * Set the ID of the event
     * @param ID the ID of the event
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Set the location of the event
     * @param location The location of the event
     */
    public void setLocation(String location) {
    	this.location = location;
    }

    /**
     * Get the ID of the Event
     * @return the ID of the event
     */
    public int getID() {
    	return ID;
    }
    
    /**
     * Get the name of the event
     * @return eventName 
     */

    public String getName() {
    	return eventName;
    }
    
    /**
     * Get the description of the event
     * @return description
     */
    public String getDescription() {
    	return description;
    }
    
    
    /**
     * Get the location of the event
     * @return location
     */
    public String getLocation() {
    	return location;
    }

    /**
     * Get the start time of the event
     * @return event start time as a LocalDateTime Object
     */
    public LocalDateTime getStartTime(){
        return startTime;
    }

    /**
     * Get the end time of the event
     * @return event start time as a LocalDateTime Object
     */
    public LocalDateTime getEndTime(){
        return endTime;
    }

    /**
     * Get the start time of the event
     * @return event start time as a String
     */
    public String getStartTimeString() {
        return formatter.format(startTime);
    }

    /**
     * Get the end time of the event
     * @return event end time as a String
     */
    public String getEndTimeString() {
        return formatter.format(endTime);
    }

    /**
     * Check if the interval of the event is valid
     * @return <code>ture</code> if the start time of the event if before end time, <code>false</code> otherwise
     */
    public boolean isValidInterval(){
        return startTime.isBefore(endTime);
    }

    public int getUserID(){
        return userID;
    }
}
