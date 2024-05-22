package commands.setAndGet;

import commands.Command;

public class Get implements Command {

    GetReceiver getReceiver;


    public Get(GetReceiver getReceiver) {
        this.getReceiver = getReceiver;
    }

    @Override
    public String execute() {
        return getReceiver.execute();
    }
}
