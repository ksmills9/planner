package src;

import java.time.LocalDateTime;
import java.time.format.*;
import java.util.*;

/**
* Event class that creates a new event for one user
*/
public class Event{
    private int ID;
    private int userID;
    private String eventType = "event";
    private String eventName;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); //formats String time to LocalDateTime
    private String location;
    public static final String[] freqArray = new String[]{"Once","Everyday","Week","Month","Year"};
    private String frequency = freqArray[0];
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
     * @param eventName Name of the event
     * @param description details about the event
     * @param start the start time of event
     * @param ID the Id of the event
     * @param userID The ID of the user who created the event
     * @param occurence How often the event will occur. 
     */
    Event(int ID, int userID, String eventName, String description, 
        String start, String occurence){

        this.ID = ID;
        this.userID = userID;
        this.eventName = eventName;
        this.description = description;
        setDateTime(start);
        setFrequency(frequency);

    }


    /**
     * Create an event will all the details provided
     * @param eventName Name of the event
     * @param description details about the event
     * @param start the start time of event
     * @param end end time of the event
     * @param ID the Id of the event
     * @param userID The ID of the user who created the event
     * @param location The location of the Event
     */
    public Event(int ID, int userID, String eventName, String description, String start, String end,
        String location){
        
        this.ID = ID;
    	this.userID = userID;
    	this.eventName = eventName;
    	this.description = description;
    	setDateTime(start, end);
    	this.location = location;
    }

    /**
     * Create an event will all the details provided
     * @param eventName Name of the event
     * @param description details about the event
     * @param start the start time of event
     * @param end end time of the event in string format
     * @param location the location of the event in string format
     * @param occurrence how often the event occurs. legal arguments: ONCE, EVERYDAY, WEEK, MONTH, YEAR. By default it is ONCE.
     * @param id The id of the event
     * @param userID The id of the user that created the event
     */
    public Event(int id, int userID, String eventName, String description, String start, String end,
        String location, String occurrence) {
        this.ID = id;
        this.userID = userID;
        this.eventName = eventName;
        this.description = description;
        setDateTime(start, end);
        this.location = location;
        setFrequency(occurrence);
    }

    /**
     * Copy Constructor
     * @param event the event to copy
     */
    Event(Event event){
    	this.ID = event.getID();
    	this.userID = event.getUserID();
    	this.eventName = event.getName();
    	this.description = event.getDescription();
    	this.startTime = event.getStartTime();
    	this.endTime = event.getEndTime();
    	this.location = event.getLocation();
        this.frequency = event.getFrequency();
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
     * Convert a start and end time of type String and set it as the event time
     * to a LocalDateTime type.
     * @param start the start time of the event
     * @param end end time of the event
     */
    public void setDateTime(String start, String end) throws IllegalArgumentException {
    	startTime = LocalDateTime.parse(start,formatter);
    	endTime = LocalDateTime.parse(end,formatter);
    }

    public void setDateTime(LocalDateTime start, LocalDateTime end){
        startTime = start;
        endTime = end;
    }
    
    
    /**
     * Converts start time of type String to LocalDateTime format.
     * the end time is set to one hour after the start time by default.
     * @param start the start time of event
     */
    public void setDateTime(String start) throws IllegalArgumentException {
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
     * Set the frequency of the event of a certain type
     * @param occurrence the type of frequency
     */
    public void setFrequency(String occurrence){
            boolean contains = Arrays.stream(freqArray).anyMatch(occurrence :: equals);
            if (contains){
                this.frequency = occurrence;
            }
        
        // throw an exception later
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
     * Get the frequency of the event
     * @return frequency of the event
     */
    public String getFrequency(){
        return frequency;
    }

    /**
     * Check if the interval of the event is valid
     * @return <code>true</code> if the start time of the event if before end time, <code>false</code> otherwise
     */
    public boolean isValidInterval(){
        return startTime.isBefore(endTime);
    }

    /**
    * get the user's ID
    * @return the user's ID number
    */
    public int getUserID(){
        return userID;
    }

    /**
     * Get all information about the event as a string
     * @return String containing information of the instance
     */
    @Override
    public String toString() {
        return "Event name: " + eventName + "\n" +
                "Description: " + description + "\n" +
                "Event Location: " + location + "\n" +
                "Starts at: " + getStartTimeString() + "\n" +
                "Ends at: " + getEndTimeString() + "\n" +
                "Occurrence: " + frequency + "\n";
    }
}
