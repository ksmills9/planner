import javafx.application.Application;
import src.prompt.ConsolePrompt;
import src.prompt.GUIPrompt;

public class Main {
    /**
    * Main method that connects to the database and initiates the prompts
    */
    public static void main(String[] args){
        if(args.length > 0) {
            if(args[0].equals("console")){
                ConsolePrompt cPrompt = new ConsolePrompt();
                cPrompt.start();
            }
            else if(args[0].equals("gui")){
                Application.launch(GUIPrompt.class);
            }
        }
        else System.out.println("Maybe you forgot something?");
    }
}
