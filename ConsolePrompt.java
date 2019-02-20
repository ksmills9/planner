import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.TimeZone;

public class ConsolePrompt {
    private DatabaseConn conn;
    private Scanner input = new Scanner(System.in);
    private Account userAccount;
    private final String[] DaysofWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

    ConsolePrompt(DatabaseConn conn){
        this.conn = conn;
    }

    public void start(){
        short startChoice = loginPrompt();
        if(startChoice == 1) accountLogin();
        else if (startChoice == 2) accountSignUp();
        else if (startChoice == 3) System.exit(0);
    }

    public short loginPrompt(){
        short retval = 0;
        boolean validCMD = false;
        while (!validCMD){
            System.out.println("Login [1] | Signup [2]");
            try {
                retval = input.nextShort();
                if(retval == 1 || retval == 2 || retval == 3) validCMD = true;
                else throw new InputMismatchException();
            } catch (Exception ex){
                System.out.println("Invalid command!"); retval = 0;
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
            setUserAccount(userAccount);
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
        printCalender(LocalDateTime.now());
        System.out.println("Main Menu");
        int cmd = 0;
        String[] mainmenu = {"My Events", "My Tasks", "My Profile", "Others", "LogOut"};
        for(int i = 0; i < mainmenu.length; i++) System.out.print("["+i+"] "+mainmenu[i]+ " \t");
        System.out.println();
        boolean vaildCMD = false;
        while (!vaildCMD){
            try {
                cmd = input.nextShort();
                if(cmd < 0 || cmd >= mainmenu.length) throw new InputMismatchException();
                else vaildCMD = true;
            } catch (InputMismatchException ex){
                System.out.println("Invalid Input, Try again!");
                cmd = 0; input.next();
            }
        }

        if(cmd == 0) consoleEventMenu();
        else if(cmd == 1) consoleTasksMenu();
        else if(cmd == 2) consoleProfileMenu();
        else if(cmd == 3) consoleOthersMenu();
        else if(cmd == mainmenu.length - 1) logout();

    }

    public void consoleProfileMenu(){
        viewProfile();
        System.out.println("My Profile");
        int cmd = 0; boolean validCMD = false;
        String[] profileMenu = {"Change Name", "Change Password", "Change TimeZone", "Main Menu"};
        for(int i = 0; i < profileMenu.length; i++) System.out.print("["+i+"] "+profileMenu[i]+ " \t");
        System.out.println();
        while (!validCMD){
            try {
                cmd = input.nextShort();
                if(cmd < 0 || cmd >= profileMenu.length) throw new IndexOutOfBoundsException();
                else validCMD = true;
            } catch (Exception ex){
                System.out.println("Invalid Input, Try again!");
                input.next(); cmd = 0;
            }
        }
        if(cmd == 0){
            changeAccountName();
        }
        else if(cmd == 1){
            changeAccountPassword();
        }
        else if(cmd == 2){
            changeAccountTimeZone();
        }
        else if(cmd == profileMenu.length-1) consoleMainMenu();
    }

    void changeAccountPassword(){
        System.out.print("Enter old password: ");
        String oldPW = input.next();
        System.out.print("Enter new password: ");
        String newPW = input.next();
        System.out.print("Confirm new password: ");
        String confirmPW = input.next();
        if(!newPW.equals(confirmPW)) {
            System.out.println("Passwords do not match");
        }
        else {
            try {
                if (conn.changeUserPass(userAccount.getName(), oldPW, newPW)) System.out.println("Password changed successfully!");
                else System.out.println("Incorrect password, Try again.");
            } catch (SQLException ex){
                System.out.println("An error occurred, please try again");
            }
        }
        consoleProfileMenu();
    }

    void changeAccountName(){
        System.out.print("Enter new name: ");
        String newName = input.next();
        conn.changeUser(userAccount.getName(), newName, "account_name");
        System.out.println("Name changed successfully!");
        userAccount.setName(newName);
        consoleProfileMenu();
    }

    void changeAccountTimeZone(){
        System.out.print("Enter new timezone: ");
        String newTZ = input.next();
        if(!isValidTimeZone(newTZ)) System.out.println("Invalid input, unable to change TimeZone");
        else {
            userAccount.setTimeZone(newTZ);
            conn.changeUser(userAccount.getName(), newTZ, "account_timezone");
        }
        consoleProfileMenu();
    }

    void viewProfile(){
        System.out.println("My Name: " + userAccount.getName()+
                "\nMy TimeZone: " + userAccount.getTimeZone());
    }

    public void consoleTasksMenu(){

    }

    public void consoleOthersMenu(){

    }

    public void consoleEventMenu(){
        String[] eventMenu = {"Create Event","Upcoming Event", "All Events", "Main Menu", "LogOut"};
    }

    void logout(){
        setUserAccount(null); start();
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

        System.out.print("Enter TimeZone: ");
        timezone = input.next();
        if(isValidTimeZone(timezone)) newAccount = new Account(accountName, timezone);
        else {
            System.out.println("Invalid input, local TimeZone used");
            newAccount = new Account(accountName);
        }

        if(conn.addAccount(newAccount, password)) {
            System.out.println("Account created Successfully!");
            setUserAccount(newAccount);
            printCalender(LocalDateTime.now());
            consoleMainMenu();
        }
        else {
            System.out.println("An error occurred. Please try again!");
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

    public void setUserAccount(Account account){
        userAccount = account;
    }

    boolean isValidTimeZone(String stingTZ){
        String[] validIDs = TimeZone.getAvailableIDs();
        boolean validZone = false;
        for(int i = 0; i < validIDs.length && !validZone; i++) if(stingTZ.equals(validIDs[i])) validZone = true;
        return validZone;
    }
}
