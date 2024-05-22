package commands.setAndGet;


import commands.Command;

public class Set implements Command {
    SetReceiver setGetReceiver;


    public Set(SetReceiver setGetReceiver) {
        this.setGetReceiver = setGetReceiver;
    }

    @Override
    public String execute() {
        return setGetReceiver.execute();
    }
}
