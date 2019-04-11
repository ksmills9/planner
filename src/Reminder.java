package src;


public class Reminder extends Event{

	/**
	*@param id The id of the reminder
	*@param userID id assoicated with the user
	*@param name The name of the reminder in string format
	*@param start The start time of the reminder in string format
	*/
	Reminder(int id, int userID, String name, String start){
		super(id, userID, name, start);
	}

	/**
	*@param id The id of the reminder
	*@param userID id assoicated with the user
	*@param name The name of the reminder in string format
	*@param start The start time of the reminder in string format
	*@param frequency How often the reminder occur. by default it is set to only occur once.
	*/
	Reminder(int id, int userID, String name, String start, String frequency){
		super(id, userID, name, start, frequency);
	}

}