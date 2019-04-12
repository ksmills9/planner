package src;

public class userAlarm {
	String alarmName;
	String alarmTime;
	String repeatingDays;
	boolean isActive;
	
	public userAlarm(String aName, String aTime, String days, int isAct) {
		this.alarmName = aName;
		this.alarmTime = aTime;
		this.repeatingDays = days;
		if(isAct==1)this.isActive=true;
		else this.isActive=false;
	}
	
	public String getAName() {
		return this.alarmName;
	}
	
	public String getATime() {
		return this.alarmTime;
	}
	
	public String getDays() {
		return this.repeatingDays;
	}
}
