package src;
import java.time.LocalDateTime;
import java.time.format.*;
import java.util.*;

public class Reminder{
	
	private int id;
	private int userID;
	private String reminderName;
	private LocalDateTime startTime;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	
	
	/**
	*@PARAM id the id assigned to this instance of reminder.
	*@PARAM userID the userID assoicated with this reminder.
	*@PARAM reminderName the name of the reminder.
	*@PARAM startTime the time of the reminder in String format of "yyyy-MM-dd HH:mm".
	*/
	Reminder(int id, int userID, String reminderName, String startTime){
		this.id = id;
		this.userID = userID;
		this.reminderName = reminderName;
		setDateTime(startTime);
	}
	
	/**
	*PARAM reminder a Reminder object to copy.
	*/
	Reminder(Reminder reminder){
		this.id = reminder.getID();
		this.userID = reminder.getUserID();
		this.reminderName = reminder.getReminderName();
		this.startTime = reminder.getStartTime();
	}
	
	/**
	*PARAM id the new id for the reminder to be set to.
	*/
	public void setID(int id){
		this.id = id;
	}
	
	/**
	*PARAM userID the new userID for the reminder to be set to.
	*/
	public void setUserID(int userID){
		this.userID = userID;
	}
	
	/** 
	*PARAM name the new reminderName to be set to.
	*/
	public void setReminderName(String name){
		reminderName = name;
	}
	
	
	/**
	*@param start the start time of the reminder in String format. converts the string to LocalDateTime format.
	*/
	public void setDateTime(String start) throws IllegalArgumentException {
    	startTime = LocalDateTime.parse(start,formatter);
	}
	
	/**
	*@return the id of the event as an integer.  
	*/
	public int getID(){
    	      return id;
    	}
	
	/**
	*@return the userID of the event as an integer.
	*/
	public int getUserID(){
    		return userID;
    	}
	
	/**
	*@return the name of the event as a String.
	*/
	public String getName(){
    		return reminderName;
    	}
	
	/**
	*@return startTime of the reminder in LocalDateTime format.
	*/
	public LocalDateTime getStartTime(){
    		return startTime;
    	}

	public String getReminderName() {
		return reminderName;
	}
}
