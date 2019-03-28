import java.util.ArrayList;
import java.time.LocalDateTime;


public class AllReminders(){

	private ArrayList<Reminder> allReminders = new ArrayList<Reminder>();


	AllReminders(){

	} 


	AllReminders(AllReminders reminders){
		for(AllReminders reminder : reminders){
			allReminders.add(new Reminder())
		}
	}


	public ArrayList<Reminder> getReminders(){
		ArrayList<Reminder> retval = new ArrayList<Reminder>(allReminders);
		return retval;
	}

	public void addReminder(Reminder reminder){
		LocalDateTime now = LocalDateTime.now();
		if(reminder.getStart().isafter(now)){
			allReminders.add(reminder);
		}
	}

	public void removeReminder(Reminder reminder){
		if(allReminders.contains(reminder)){
			allReminders.remove(reminder);
		}
	}


}