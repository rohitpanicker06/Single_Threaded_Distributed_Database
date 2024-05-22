package commands.factory;

import commands.Command;
import commands.echo.Echo;
import commands.echo.EchoReceiver;
import commands.ping.Ping;
import commands.ping.PingReceiver;
import commands.setAndGet.Get;
import commands.setAndGet.GetReceiver;
import commands.setAndGet.Set;
import commands.setAndGet.SetReceiver;

public class CommandFactory {

    public static Command getCommand(String [] commandArray) {
        if (commandArray[0].equalsIgnoreCase("ping")) {
            PingReceiver pingReceiver = new PingReceiver();
            return new Ping(pingReceiver);
        }else if(commandArray[0].equalsIgnoreCase("echo"))
        {
            EchoReceiver echoReceiver = new EchoReceiver(commandArray[1]);
            return new Echo(echoReceiver);
        }else if(commandArray[0].equalsIgnoreCase("SET"))
        {
            SetReceiver setGetReceiver = new SetReceiver(commandArray[1], commandArray[2]);
            return new Set(setGetReceiver);
        }else if(commandArray[0].equalsIgnoreCase("GET"))
        {
            GetReceiver getReceiver = new GetReceiver(commandArray[1]);
            return new Get(getReceiver);
        }
        else {

            return null;
        }
    }

}
