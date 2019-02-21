import java.util.ArrayList;
import java.time.LocalDateTime;

public class AllEvents {
    private ArrayList<Event> eventList  = new ArrayList<Event>();
    
    //getters
    
    public ArrayList<Event> getEventByName(String name) {
    	ArrayList<Event> eventByName = new ArrayList<Event>();
    	for (Event event : eventList) {
    		if (name == event.getName()) {
    			eventByName.add(event);
    		}
    	}
    	return new ArrayList<Event>(eventByName);
    }
    
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
    
    public ArrayList<Event> getEventbyLocation(String location) {
    	ArrayList<Event> eventByLocation = new ArrayList<Event>();
    	for (Event event : eventList) {
    		if(location == event.getLocation()) {
    			eventByLocation.add(event);
    		}
    	}
    	return new ArrayList<Event>(eventByLocation);
    }
    
    public boolean addEvent(Event event){
    	boolean isAvailable = true;
    	for (Event e : eventList) {
    		if (event.getStartTime().equals(e.getStartTime()) || event.getEndTime().isAfter(e.getStartTime())) {
    			isAvailable = false;
    		}
    	}
    	
    	if (isAvailable) {
    		eventList.add(event);
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    public boolean removeEvent(Event event) {
    	if (eventList.contains(event)) {
    		eventList.remove(event);
    		return true;
    	}
    	else {
    		return false;
    	}
    	
    }
    
    
    
    
}
