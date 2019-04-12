
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Alarm class to create new alarms for the user
 * @author sunkwa-mills
 *
 */
public class Alarm {
	private int currHour;
	private String currMin;
	
	private int alarmHour;
	private int alarmMin;
	private String alarmMsg;
	
	private boolean hasStarted = false;
	
	/**
	* creating the timer itself and its TimerTask
	*/
	Timer timer = new Timer();
	TimerTask task = new TimerTask() {
		/**
		 * run method starts the timer and sets the current time on the computer
		 * the method then checks if it is time for the alarm using the checkAlarm method
		 */
		public void run() {
			Calendar c = new GregorianCalendar();
			currHour = c.get(Calendar.HOUR);
			currMin = String.valueOf(c.get(Calendar.MINUTE));
			if(currMin.length()==1) {
				currMin = "0"+currMin;
			}
			checkAlarm();
		}
	};
	
	/**
	 * checkAlarm method used to see if the current time is equal to the alarm time
	 * prints out the current time and the alarm message when it is time
	 */
	private void checkAlarm() {
		if(this.alarmHour==this.currHour && this.alarmMin==Integer.valueOf(this.currMin)) {
			timer.cancel();
			System.out.println(this.alarmMsg+"\nThe time is "+this.currHour+":"+this.currMin);
			String time;
			if(String.valueOf(alarmHour).length()==1) {
				time= "0"+alarmHour+":";
			}
			else {
				time=alarmHour+":";
			}
			if(String.valueOf(alarmMin).length()==1) {
				time+="0"+alarmMin;
			}else {
				time+=alarmMin;
			}
			JOptionPane.showMessageDialog(null, alarmMsg + " - "+ time+"\n"+"The time is "+ time);
		}
	}
	/**
	 * start method that initiates the timer schedule with the task
	 * @param h This is the hour of the alarm
	 * @param m This is the minute of the alarm
	 * @param ms This is the message for the alarm when it rings
	 */
	public void start(int h, int m, String ms) {
		this.alarmHour = h;
		this.alarmMin = m;
		this.alarmMsg = ms;
		timer.scheduleAtFixedRate(task, 0, 1000);
		this.hasStarted = true;
	}
	
	/**
	 * cancel method to cancel a running alarm
	 */
	public void cancel() {
		if(hasStarted) {
			timer.cancel();
		}
		else {
			System.out.println("An alarm has not been started");
		}
	}

}
