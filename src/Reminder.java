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

	Reminder(int id, int userID, String reminderName, String startTime){
		this.id = id;
		this.userID = userID;
		this.reminderName = reminderName;
		setDateTime(startTime);
	}

	Reminder(Reminder reminder){
		this.id = reminder.getID();
		this.userID = reminder.getUserID();
		this.reminderName = reminder.getReminderName();
		this.startTime = reminder.getStartTime();
	}

	public void setID(int id){
		this.id = id;
	}

	public void setUserID(int userID){
		this.userID = userID;
	}

	public void setReminderName(String name){
		reminderName = name;
	}

	public void setDateTime(String start) throws IllegalArgumentException {
    	startTime = LocalDateTime.parse(start,formatter);
    }

    public int getID(){
    	return id;
    }

    public int getUserID(){
    	return userID;
    }

    public String getName(){
    	return reminderName;
    }

    public LocalDateTime getStartTime(){
    	return startTime;
    }

	public String getReminderName() {
		return reminderName;
	}
}