/*
* Alarm test class to create a new alarm and check if it is time
*/
public class AlarmTest {
  
  /* 
  * main class to create new alarm and call the checkAlarm method
  */
	public static void main(String[] args) {
		
    /*
    * creating a new Alarm object
    */
	Alarm a = new Alarm();
    
    /*
    * calling the checkAlarm function to start the alarm
    * takes 3 paramaeters, hours, minutes and a message for the alarm
    */
	a.start(9,18,"Work Time");
	}

}
