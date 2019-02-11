import java.util.Date;

public class Todo{
	private int ID;
	private String name, description;
	private Date deadline;
	private boolean isFinished;
	
	public Todo(){}
	
	public Todo(String name, String description, Date deadline){
		//need to assign ID from database
		this.name=name;
		this.description=description;
		this.deadline=deadline;
		isFinished=false;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDescription(){
		return description;
	}
	
	public Date getDeadline(){
		return deadline;
	}
	
	public boolean getStatus(){
		return isFinished;
	}
	
	public void setStatus(boolean status){
		isFinished=status;
	}
}