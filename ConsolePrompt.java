import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.TimeZone;

public class ConsolePrompt {
    private DatabaseConn conn;
    private Scanner input = new Scanner(System.in);
    private final String[] DaysofWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

    ConsolePrompt(DatabaseConn conn){
        this.conn = conn;
    }

    public void start(){
        short startChoice = loginPrompt();
        if(startChoice == 1) accountLogin();
        else if (startChoice == 2) accountSignUp();
    }

    public short loginPrompt(){
        short retval = 0;
        boolean validCMD = false;
        while (!validCMD){
            System.out.println("Login [1] | Signup [2]");
            try {
                retval = input.nextShort();
                if(retval == 1 || retval == 2) validCMD = true;
                else System.out.println("Please Enter 1 or 2");
            } catch (Exception ex){
                System.out.println("Please Enter 1 or 2"); retval = 0;
                input.next();
            }
        }
        return retval;
    }

    public void accountLogin(){
        String accountName = "", password = "";
        System.out.print("Enter your Name: ");
        accountName = input.next();
        System.out.print("Enter password: ");
        password = input.next();
        Account userAccount = conn.login(accountName, password);
        if(userAccount != null) {
            System.out.println("Login Successful!");
            consoleMainMenu();
        }
        else {
            System.out.println("Username/password incorrect. Try again? [Y]/[N]");
            String cmd = input.next();
            if(cmd.equals("Y") || cmd.equals("y")) accountLogin();
            else start();
        }
    }

    public void consoleMainMenu(){
        System.out.println("Main Menu");
        int cmd = 0;
        String[] mainmenu = {"My Events", "My To-Do List", "My Profile", "LogOut"};
        for(int i = 0; i < mainmenu.length; i++) System.out.print("["+i+"] "+mainmenu[i]+ " \t");
        System.out.println();
        try {
            cmd = input.nextShort();
            if(cmd < 0 || cmd >= mainmenu.length) throw new IndexOutOfBoundsException();
        } catch (Exception ex){
            System.out.println("Invalid Input, Try again!");
            input.next(); consoleMainMenu();
        }
        switch (cmd){
            case 0:
                consoleEventMenu(); break;
            case 1:
                consoleToDoListMenu(); break;
            case 2:
                consoleProfileMenu(); break;
            case 3:
                start(); break;
        }
    }

    public void consoleProfileMenu(){
        System.out.println("Profile Menu");
        int cmd = 0;
        String[] mainmenu = {"Change Name", "Change Password", "Change TimeZone", "Main Menu", "LogOut"};
        for(int i = 0; i < mainmenu.length; i++) System.out.print("["+i+"] "+mainmenu[i]+ " \t");
        System.out.println();
        try {
            cmd = input.nextShort();
            if(cmd < 0 || cmd >= mainmenu.length) throw new IndexOutOfBoundsException();
        } catch (Exception ex){
            System.out.println("Invalid Input, Try again!");
            input.next(); consoleMainMenu();
        }

    }

    public void consoleToDoListMenu(){

    }

    public void consoleEventMenu(){

    }

    public void accountSignUp(){
        String accountName = "", password = "", timezone=""; Account newAccount;

        boolean notValideName = true, notValidPass = true, notValidZone = true;
        while (notValideName){
            System.out.print("Enter your Name: ");
            accountName = input.next();
            if(accountName.length() < 3 ) System.out.println("Name must be longer than 2 characters.");
            else if(accountName.length() > 25) System.out.println("Name should not exceed 25 characters.");
            else notValideName = false;
        }

        while (notValidPass){
            System.out.print("Enter password: ");
            password = input.next();
            if(password.length() > 24 || password.length() < 8) System.out.println("Password must be between 8 to 24" +
                    " characters");
            else notValidPass = false;
        }

        System.out.print("Enter TimeZone): ");
        timezone = input.next();
        String[] validIDs = TimeZone.getAvailableIDs();
        for(int i = 0; i < validIDs.length && notValidZone; i++) if(timezone.equals(validIDs[i])) notValidZone = false;
        if(!notValidZone) newAccount = new Account(accountName, timezone);
        else if (timezone.length() == 0) {
            newAccount = new Account(accountName);
        }
        else {
            System.out.println("Invalid input, local TimeZone used");
            newAccount = new Account(accountName);
        }

        if(conn.addAccount(newAccount, password)) System.out.println("Account created Successfully!");
        else {
            System.out.println("An error occured. Please try again!");
            start();
        }
    }

    void printCalender(LocalDateTime dateTime) {
        System.out.format("%20s\n", dateTime.getMonth());
        for (String day2 : DaysofWeek) System.out.format("%5s", day2);
        System.out.println();
        LocalDateTime curMonth = LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), 1, 0, 0);
        LocalDateTime nextMonth = curMonth.plusMonths(1);
        nextMonth = nextMonth.minusDays(1);
        int DaysInMonth = nextMonth.compareTo(curMonth) + 1;
        int padding = curMonth.getDayOfWeek().getValue();
        boolean endPad = false;
        for (int i = 0; i < Math.ceil((DaysInMonth + padding) / 7.0); i++) {
            for (int j = 1; j < 8; j++) {
                int count = i * 7 + j - padding;
                if (count == dateTime.getDayOfMonth()) System.out.format("%4s ", "[" + count + "]");
                else if (count > 0 && count <= DaysInMonth) System.out.format("%4d ", count);
                else System.out.format("%4s ", " ");
            }
            System.out.println();
        }
    }
}
