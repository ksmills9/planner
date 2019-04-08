package src.errors;

public class DuplicateAccountNameException extends PlannerException {
    public DuplicateAccountNameException(String message){
        super("There is already an account with that name");
    }
}
