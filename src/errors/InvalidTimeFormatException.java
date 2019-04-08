package src.errors;

public class InvalidTimeFormatException extends PlannerException {
    public InvalidTimeFormatException(){
        super("Time must be entered in HH:mm format (24 hour)");
    }

    public InvalidTimeFormatException(String message){
        super(message);
    }

}
