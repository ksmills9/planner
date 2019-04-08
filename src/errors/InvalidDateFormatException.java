package src.errors;

public class InvalidDateFormatException extends PlannerException {
    public InvalidDateFormatException(){
        super("Date must be entered in yyyy-MM-DD format");
    }
    public InvalidDateFormatException(String message){
        super(message);
    }
}
