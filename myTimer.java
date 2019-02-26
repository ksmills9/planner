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
	static int secondsPassed = 0;
	int timeInSeconds;

	/**
	* creating the timer itself and its TimerTask
	*/
	static Timer timer = new Timer();
	TimerTask task = new TimerTask() {
		//method to start the timer and increment the secondsPassed variable everytime, checks for finishing as well
		public void run() {
			secondsPassed++;
			int temp = timeInSeconds - secondsPassed;
			String make = "Time remaining: " + temp/3600 + ":" + (temp%3600)/60 + ":" + (temp%3600)%60+"\r";
			System.out.print(make);
			stopCheck(timeInSeconds);
		}
	};

	/**
	* checker method to see if the timer is done
	*/
	private void stopCheck(int end) {
		if(secondsPassed == end) {
			timer.cancel();
			System.out.println("Timer finished!");
		}
	}

	/**
	* method to initiate the timer with all the necessary variables
	*/
	public void start(int hours, int minutes, int seconds) {
		this.timeInSeconds = seconds +(minutes*60) + (hours*60*60);
		timer.scheduleAtFixedRate(task,1000,1000);
	}
	
	public void cancel(){
		timer.cancel();
		System.out.println("Timer stopped");
		this.secondsPassed =0;
	}	

}
