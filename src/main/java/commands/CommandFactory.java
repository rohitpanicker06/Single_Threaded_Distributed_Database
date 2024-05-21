package commands;

public class CommandFactory {

    public static Command getCommand(String commandName) {
        if (commandName.equalsIgnoreCase("ping")) {
            PingReceiver pingReceiver = new PingReceiver();
            return new Ping(pingReceiver);
        } else {

            return null;
        }
    }

}
