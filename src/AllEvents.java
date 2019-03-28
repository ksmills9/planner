package src;

import java.util.ArrayList;
import java.time.LocalDateTime;

/**
 * The Allevents class represents all events associated with a user.
 * @author Nandy
 *
 */
public class AllEvents {
    private ArrayList<Event> eventList  = new ArrayList<Event>();

	/**
	 * Default constructor
	 */
	AllEvents(){}

	/**
	 * Copy constructor
	 * @param allEvents instance to copy
	 */
	AllEvents (AllEvents allEvents){
		for(Event event : allEvents.getEvents()){
			eventList.add(new Event(event));
		}
	}

    /**
     * get an event object by its name 
     * @param name In string format
     * @return ArrayList of Event object with the given name.
     */
    public ArrayList<Event> getEventByName(String name) {
    	ArrayList<Event> eventByName = new ArrayList<Event>();
    	for (Event event : eventList) {
    		if (name == event.getName()) {
    			eventByName.add(event);
    		}
    	}
    	return new ArrayList<Event>(eventByName);
    }
    
    /**
     * Gets event objects that starts at a paticular time
     * @param time the time  in LocalDateTime format.
     * @return ArrayList of events that start at the time given.
     */
    public Event getEventbyTime(LocalDateTime time) {
    	Event event = null;
    	for (Event e : eventList) {
    		if (time.equals(e.getStartTime())) {
    			event = e;
    		}
    	}
    	
    	if (event != null) {
    		return new Event(event);
    	}
    	else {return null;}
    }
    
    /**
     * get events by location
     * @param location The location of the event.
     * @return Events at that location.
     */
    public ArrayList<Event> getEventbyLocation(String location) {
    	ArrayList<Event> eventByLocation = new ArrayList<Event>();
    	for (Event event : eventList) {
    		if(location == event.getLocation()) {
    			eventByLocation.add(event);
    		}
    	}
    	return new ArrayList<Event>(eventByLocation);
    }
    
    /**
	* adds an event to the eventList. if the event is reccuring the method will add
    * the future events aswell.
	* @param event the event to be added to the eventList
	 */
    public void addEvent(Event event){
    	if (event.getFrequency().equals("Once"))
            eventList.add(event);
    	else if (event.getFrequency().equals("Everyday")){
    		addDaily(event);
    	}
		else if(event.getFrequency().equals("Week")){
			addWeekly(event);
		}
		else if(event.getFrequency().equals("Month")){
			addMonthly(event);
		}
		else if(event.getFrequency().equals("Year")) {
			addYearly(event);
		}
    }


    
    public void addRecurringEvent(Event event){

    }

    /**
     * Checks if event is during another event.
     * @param event The event to be checked
     * @return false if the event is during another event time and true otherwise.
     */
    public boolean isAvailable(Event event){
		boolean retval = true;
		for (Event e : eventList) {
			if (event.getStartTime().isAfter(e.getStartTime()) && event.getEndTime().isBefore(e.getStartTime())) {
				retval = false;
			}
		}
		return retval;
	}

	
    /**
     * Get events that are upcoming.
     * @return ArrayList of events that are upcoming.
     */
    public ArrayList<Event> getEvents(LocalDateTime start, LocalDateTime end){
    	ArrayList<Event> retval = new ArrayList<>();
    	for(Event e : eventList){
    		if (e.getStartTime().isAfter(start) && e.getStartTime().isBefore(end)){
    			retval.add(e);
			}
		}
    	return retval;
	}

	/**
	 * Get a list of all events starting after a given time
	 * @param start the start time of the query
	 * @return a list of all events after the given time
	 */
	public ArrayList<Event> getEvents(LocalDateTime start){
		ArrayList<Event> retval = new ArrayList<>();
		for(Event e : eventList){
			if (e.getStartTime().isAfter(LocalDateTime.now())){
				retval.add(e);
			}
		}
		return retval;
	}

	/**
	 * Get a list of all events
	 * @return a list containing all events
	 */
	public ArrayList<Event> getEvents(){
		ArrayList<Event> retval = new ArrayList<>();
		for(Event e : eventList) retval.add(new Event(e));
		return retval;
	}


	/**
     * Removes event from the eventList
     * @param event The event to be removed.
     * @return true if the event was remoed and false otherwise.
     */
    public boolean removeEvent(Event event) {
    	if (eventList.contains(event)) {
    		eventList.remove(event);
    		return true;
    	}
    	else {
    		return false;
    	}
    }

    private void addDaily(Event event){
        int eventsToAdd = 365;
        for(int i=0; i<eventsToAdd; i++){
            Event toAdd = new Event(event);
            LocalDateTime start = toAdd.getStartTime().plusDays(i);
            LocalDateTime end = toAdd.getEndTime().plusDays(i);
            toAdd.setDateTime(start, end);
            eventList.add(toAdd);
        }
    }

    private void addWeekly(Event event){
        for(int i=0; i<52; i++){
            Event toAdd = new Event(event);
            LocalDateTime start = toAdd.getStartTime().plusWeeks(i);
            LocalDateTime end = toAdd.getEndTime().plusWeeks(i);
            toAdd.setDateTime(start, end);
            eventList.add(toAdd);
        }
    }

    private void addMonthly(Event event){
        for(int i=0; i<12; i++){
            Event toAdd = new Event(event);
            LocalDateTime start = toAdd.getStartTime().plusMonths(i);
            LocalDateTime end = toAdd.getEndTime().plusMonths(i);
            toAdd.setDateTime(start, end);
            eventList.add(toAdd);
        }

    }

    private void addYearly(Event event){
        for(int i=0; i<4; i++){
            Event toAdd = new Event(event);
            LocalDateTime start = toAdd.getStartTime().plusYears(i);
            LocalDateTime end = toAdd.getEndTime().plusYears(i);
            toAdd.setDateTime(start, end);
            eventList.add(toAdd);
        }
    }
}