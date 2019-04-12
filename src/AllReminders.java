package src;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class AllReminders{
	private ArrayList<Reminder> allReminders = new ArrayList<Reminder>();


	AllReminders(){

	}

	AllReminders(AllReminders reminders){
		for(Reminder reminder : reminders.getAllReminders()){
			allReminders.add(reminder);
		}
	}


	public void addReminder(Reminder reminder){
		allReminders.add(reminder);
	}

	public void removeReminder(Reminder reminder){
		allReminders.remove(reminder);
	}

	public ArrayList<Reminder> getUpcomingReminders(){
		ArrayList<Reminder> upcoming = new ArrayList<>();

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime cutOff = now.plusDays(1);
		

		for(Reminder reminder : allReminders){
			if(reminder.getStartTime().isAfter(now) && reminder.getStartTime().isBefore(cutOff)){
				upcoming.add(reminder);
			}
		}

		return upcoming;
	}

	public ArrayList<Reminder> getAllReminders() {
		return allReminders;
	}
}
