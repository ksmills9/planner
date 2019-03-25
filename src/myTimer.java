package src;

import java.util.Timer;
import java.util.TimerTask;

/**
* myTimer class used to create a timer for the user
* has instance variables secondsPassed which is integer to keep track of the seconds passed
*  and timeInSeconds which is an integer showing how long the timer should run for
*/
public class myTimer{

	/**
	* instance variables
	*/
	int secondsPassed = 0;
	int timeInSeconds;
	boolean running = false;
	int[] timeLeftArr = new int[3];

	/**
	* creating the timer itself and its TimerTask
	*/
	static Timer timer = new Timer();
	TimerTask task = new TimerTask() {
		//method to start the timer and increment the secondsPassed variable everytime, checks for finishing as well
		public void run() {
			secondsPassed++;
			int temp = timeInSeconds - secondsPassed;
			timeLeftArr[0]= temp/3600;
			timeLeftArr[1] = (temp%3600)/60;
			timeLeftArr[2] = (temp%3600)%60;
			stopCheck(timeInSeconds);
		}
	};

	/**
	* checker method to see if the timer is done
	*/
	private void stopCheck(int end) {
		if(secondsPassed == end) {
			running=false;
			task.cancel();
			timer.purge();
			//should have an interrupt
			System.out.println("Timer finished!");
		}
	}

	/**
	* method to initiate the timer with all the necessary variables
	*/
	public void start(int hours, int minutes, int seconds) {
		running = true;
		this.timeInSeconds = seconds +(minutes*60) + (hours*60*60);
		timer.scheduleAtFixedRate(task,0,1000);
	}
	
	/**
	 * method to get the remaining time array
	 */
	public int[] getTimeLeft() {
		int[] time = new int[3];
		time[0]=this.timeLeftArr[0];
		time[1]=this.timeLeftArr[1];
		time[2]=this.timeLeftArr[2];
		
		return time;
	}
	
	public boolean isRunning() {
		return this.running;
	}
	
	/**
	* method to stop the timer
	*/
	public void cancel(){
		task.cancel();
		timer.purge();
		System.out.println("Timer stopped");
		this.secondsPassed =0;
		this.running=false;
	}	

}
