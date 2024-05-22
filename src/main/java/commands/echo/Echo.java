package commands.echo;


import commands.Command;

public class Echo implements Command {
    EchoReceiver echoReceiver;


    public Echo(EchoReceiver echoReceiver) {
        this.echoReceiver = echoReceiver;
    }

    @Override
    public String execute() {
        return echoReceiver.execute();
    }
}

