import java.time.LocalDateTime;
import java.time.format.*;
import java.util.*;


public class Reminder extends Event(){
	private int ID;
	private int userID;
	private String name;
	private LocalDateTime start;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


	Reminder(int id, int userID, String name, String start){
		this.ID = id;
		this.ID = userID;
		this.name = name;
		setDateTime(start);
	}


	public void setID(int id){
		this.ID = id;
	}

	public void setName(String name){
		this.name = name;
	}

	public void setDateTime(String start) throws IllegalArgumentException {
    	this.start = LocalDateTime.parse(start,formatter);
    }

    public int getID(){
    	return ID;
    }

    public int getUserID(){
    	return userID;
    }

    public String getName(){
    	return name
    }

    public LocalDateTime getStart(){
    	return start;
    }

    public boolean isUpcoming(){
    	LocalDateTime currentTime = LocalDateTime.now();
    	if (currentTime.plusDays(1) == start){
    		return true;
    	}
    	else return false;
    }





}
