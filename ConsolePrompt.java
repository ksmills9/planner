import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.TimeZone;

public class ConsolePrompt {
    private DatabaseConn conn;
    private Account userAccount;
    private final String[] DaysofWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

    ConsolePrompt(DatabaseConn conn){
        this.conn = conn;
    }

    public void start(){
        System.out.println("Welcome to your personal Planner!");
        String[] startMenu = {"Login", "Signup", "Exit"};
        short startChoice = validCMDLoop(startMenu);
        if(startChoice == 0) accountLogin();
        else if (startChoice == 1) accountSignUp();
        else if (startChoice == 2) {
            try {
                conn.closeConnection();
            } catch (SQLException ex){
                ex.printStackTrace();
            }
            System.exit(0);
        }
    }

    public void accountLogin(){
        Scanner input = new Scanner(System.in);
        String accountName = "", password = "";
        System.out.print("Enter your Name: ");
        accountName = input.next();
        System.out.print("Enter password: ");
        password = input.next();
        Account userAccount = null;
        try {
            userAccount = conn.login(accountName, password);
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        if(userAccount != null) {
            System.out.println("Login Successful!");
            setUserAccount(userAccount);
            userAccount.setAllEvents(conn.loadEvents(userAccount));
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
        String[] mainmenu = {"My Events", "My Profile", "Others", "LogOut"};
        short userInp = validCMDLoop(mainmenu);

        if(userInp == 0) consoleEventMenu();
        else if(userInp == 1) consoleProfileMenu();
        else if(userInp == 2) consoleOthersMenu();
        else if(userInp == mainmenu.length - 1) logout();
    }

    public void consoleProfileMenu(){
        System.out.println("My Profile");
        viewProfile();
        String[] profileMenu = {"Change Name", "Change Password", "Change TimeZone", "Main Menu"};
        short userInp = validCMDLoop(profileMenu);
        if(userInp == 0)changeAccountName();
        else if(userInp == 1) changeAccountPassword();
        else if(userInp == 2) changeAccountTimeZone();
        else if(userInp == profileMenu.length-1) consoleMainMenu();
    }

    void changeAccountPassword(){
        Scanner input = new Scanner(System.in);
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
        Scanner input = new Scanner(System.in);
        System.out.print("Enter new name: ");
        String newName = input.next();
        try {
            conn.changeUser(userAccount.getName(), newName, "account_name");
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        System.out.println("Name changed successfully!");
        userAccount.setName(newName);
        consoleProfileMenu();
    }

    void changeAccountTimeZone(){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter new timezone: ");
        String newTZ = input.next();
        if(!isValidTimeZone(newTZ)) System.out.println("Invalid input, unable to change TimeZone");
        else {
            userAccount.setTimeZone(newTZ);
            try {
                conn.changeUser(userAccount.getName(), newTZ, "account_timezone");
            } catch (SQLException ex){
                ex.printStackTrace();
            }
            System.out.println("Successfully changed TimeZone!");
        }
        consoleProfileMenu();
    }

    void viewProfile(){
        System.out.println("My Name: " + userAccount.getName() +
                "\nMy ID: " + userAccount.getID() +
                "\nMy TimeZone: " + userAccount.getTimeZone());
    }

    public void consoleOthersMenu(){
        System.out.println("Others Menu");
        String[] otherMenu = {"Timer", "View Calendar", "Main Menu"};
        short cmd = validCMDLoop(otherMenu);
        if(cmd == 0) startTimer();
        else if(cmd == 1) viewCalendar();
        else if(cmd == otherMenu.length - 1) consoleMainMenu();
    }

    void viewCalendar(){
        String[] viewCalendarMenu = {"Previous Month", "Next Month", "Others Menu"};
        long change = 0;
        short cmd = 0;
        while (cmd != viewCalendarMenu.length-1){
            printCalender(LocalDateTime.now().plusMonths(change));
            cmd = validCMDLoop(viewCalendarMenu);
            if (cmd == 0) change--;
            else if(cmd == 1) change++;
            else if(cmd == viewCalendarMenu.length-1) consoleOthersMenu();
        }
    }

    void startTimer(){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter hour: ");
        int hour = 0, min = 0, sec = 0;
        try {
            hour = input.nextInt();
        } catch (InputMismatchException ex){
            timerErrorHandle();
        }
        System.out.print("Enter minute: ");
        try {
            min = input.nextInt();
        } catch (InputMismatchException ex){
            timerErrorHandle();
        }
        System.out.print("Enter second: ");
        try {
            sec = input.nextInt();
        } catch (InputMismatchException ex){
            timerErrorHandle();
        }

        myTimer timer = new myTimer();
        timer.start(hour, min, sec);
        if (input.next().equals("0")) timer.cancel();
        else System.out.println("didn't work");
        consoleOthersMenu();
    }

    void timerErrorHandle(){
        System.out.print("Invalid input");
        consoleOthersMenu();
    }

    public void consoleEventMenu(){
        System.out.println("My Events");
        String[] eventMenu = {"Upcoming Events", "All Events", "Create Event", "Main Menu"};
        short userInp = validCMDLoop(eventMenu);
        if(userInp == 0) upcomingEvents();
        if(userInp == 1) allEvents();
        else if(userInp == 2) createEvent();
        else if(userInp == eventMenu.length-1) consoleMainMenu();
    }

    void upcomingEvents(){
        ArrayList<Event> events = userAccount.getAllEvents().getEvents(LocalDateTime.now());
        if (events.size() == 0) {
            System.out.println("You have no upcoming events!");
            consoleEventMenu();
        }
        else {
            eventCMDTemplate(events);
        }
    }

    int eventCMDTemplate(ArrayList<Event> events){
        Scanner input = new Scanner(System.in);
        int cmd = events.size();
        for(int i = 0; i < events.size(); i++) System.out.println("["+i+"] "+ events.get(i).getName() + " at " + events.get(i).getStartTimeString());
        System.out.println("To view an event, enter the corresponding number.");
        System.out.println("Enter [" + events.size() + "] to return to My Events");
        boolean validCMD = false;
        while (!validCMD){
            try {
                cmd = input.nextShort();
                if(cmd == events.size()){
                    validCMD = true; consoleEventMenu();
                } else if(cmd < events.size() && cmd >= 0){
                    validCMD = true; eventView(events.get(cmd));
                }
                else throw new InputMismatchException();
            } catch (InputMismatchException ex){
                System.out.println("Invalid Input Try again");
            }
        }
        return cmd;
    }

    void allEvents(){
        Scanner input = new Scanner(System.in);
        ArrayList<Event> events = userAccount.getAllEvents().getEvents(LocalDateTime.now());
        if (events.size() == 0) {
            System.out.println("You have no events!");
            consoleEventMenu();
        } else {
            eventCMDTemplate(events);
        }
    }

    void eventView(Event event){
        String[] eventViewMenu = {"Name", "Description", "Location"};
        System.out.println(
                "Name: " + event.getName() + "\nDescription: " + event.getDescription() +
                "\nLocation: " + event.getLocation() + "\nStarts at: " + event.getStartTimeString() +
                "\nEnds At: " + event.getEndTimeString());
        consoleEventMenu();
    }

    void createEvent(){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the name of the event: ");
        String eventName = input.nextLine();
        System.out.print("Enter a small description of the event: ");
        String eventDesc = input.nextLine();
        System.out.print("Enter the start date of the event (yyyy-mm-dd): ");
        String eventStartDate = input.next(); input.nextLine();
        System.out.print("Enter the start time of the event (HH:mm): ");
        String eventStartTime = input.next(); input.nextLine();
        System.out.print("Enter the end date of the event (yyyy-mm-dd): ");
        String eventEndDate = input.next(); input.nextLine();
        System.out.print("Enter the end time of the event (HH:mm): ");
        String eventEndTime = input.next(); input.nextLine();
        System.out.print("Enter the location of the event: ");
        String eventLocation = input.nextLine();

        Event newEvent = new Event(0, userAccount.getID(), eventName, eventDesc,
                eventStartDate + " " + eventStartTime, eventEndDate + " " + eventEndTime, eventLocation);
        if (newEvent.isValidInterval()){
            if (userAccount.getAllEvents().isAvailable(newEvent)){
                int event_ID = conn.addEvent(newEvent, userAccount);
                newEvent.setID(event_ID);
                userAccount.getAllEvents().addEvent(newEvent);
                System.out.println("Event created successfully!");
            } else System.out.println("Unfortunately you are not available at that time!");
        } else System.out.println("Invalid start and end time");
        consoleEventMenu();
    }

    void logout(){
        setUserAccount(null); start();
    }

    public void accountSignUp(){
        Scanner input = new Scanner(System.in);
        String accountName = "", password = "", timezone=""; Account newAccount;

        boolean notValideName = true, notValidPass = true;
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


        int ID = conn.addAccount(newAccount, password);
        if(ID != -1) {
            System.out.println("Account created Successfully!");
            newAccount.setID(ID);
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
        System.out.format("%s - %s \n", dateTime.getMonth(), dateTime.getYear());
        for (String day2 : DaysofWeek) System.out.format("%5s", day2);
        System.out.println();
        LocalDateTime curMonth = LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), 1, 0, 0);
        LocalDateTime nextMonth = curMonth.plusMonths(1);
        nextMonth = nextMonth.minusDays(1);
        int DaysInMonth = nextMonth.compareTo(curMonth) + 1;
        int padding = curMonth.getDayOfWeek().getValue();
        for (int i = 0; i < Math.ceil((DaysInMonth + padding) / 7.0); i++) {
            for (int j = 1; j < 8; j++) {
                int count = i * 7 + j - padding;
                if (count == dateTime.getDayOfMonth() && dateTime.getMonth() == LocalDateTime.now().getMonth()) System.out.format("%4s ", "[" + count + "]");
                else if (count > 0 && count <= DaysInMonth) System.out.format("%4d ", count);
                else System.out.format("%4s ", " ");
            }
            System.out.println();
        }
    }

    public void setUserAccount(Account account){
        userAccount = account;
    }

    short validCMDLoop(String[] menuList){
        Scanner input = new Scanner(System.in);
        boolean validCMD = false;
        short cmd = 0;
        for(int i = 0; i < menuList.length; i++) System.out.print("["+i+"] "+ menuList[i]+ " \t");
        System.out.println();

        while (!validCMD){
            try {
                cmd = input.nextShort();
                if(cmd < 0 || cmd >= menuList.length) throw new InputMismatchException();
                else validCMD = true;
            } catch (Exception ex){
                System.out.println("Invalid Input, Try again!");
                input.next(); cmd = 0;
            }
        }
        return cmd;
    }

    boolean isValidTimeZone(String stingTZ){
        String[] validIDs = TimeZone.getAvailableIDs();
        boolean validZone = false;
        for(int i = 0; i < validIDs.length && !validZone; i++) if(stingTZ.equals(validIDs[i])) validZone = true;
        return validZone;
    }
}