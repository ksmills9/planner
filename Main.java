public class Main {
    private ConsolePrompt prompt;
    private DatabaseConn conn;

    Main(){
        conn = new DatabaseConn();
    }

    public static void main(String[] args){
        Main main = new Main();
        main.conn.startConnection();
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
