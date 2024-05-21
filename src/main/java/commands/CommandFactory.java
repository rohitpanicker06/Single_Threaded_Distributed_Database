package commands;

public class CommandFactory {

    public static Command getCommand(String [] commandArray) {
        if (commandArray[0].equalsIgnoreCase("ping")) {
            PingReceiver pingReceiver = new PingReceiver();
            return new Ping(pingReceiver);
        }else if(commandArray[0].equalsIgnoreCase("echo"))
        {
         EchoReceiver echoReceiver = new EchoReceiver(commandArray[1]);
         return new Echo(echoReceiver);
        }
        else {

            return null;
        }
    }

}
