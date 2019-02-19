public class Main {
    private Account myAccount;

    public static void main(String[] args){
        DatabaseConn conn = new DatabaseConn();
        conn.startConnection();
        if(args[0].equals("console")) {
            ConsolePrompt prompt = new ConsolePrompt(conn);
            prompt.start();
        }
    }
}
