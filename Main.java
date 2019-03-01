public class Main {
    private ConsolePrompt prompt;
    private DatabaseConn conn;
    
    /**
    * Creates new database connection
    */
    Main(){
        conn = new DatabaseConn();
    }
    
    /**
    * Main method that connects to the database and initiates the prompts
    */
    public static void main(String[] args){
        Main main = new Main();
        if (main.conn.connected()){
            if(args.length > 0) {
                if(args[0].equals("console")){
                    main.prompt = new ConsolePrompt(main.conn);
                    main.prompt.start();
                }
            }
            else System.out.println("Maybe you forgot something?");
        }
    }
}
