package commands.info;

import commands.Command;

public class Info implements Command {
    InfoReceiver infoReceiver;


    public Info(InfoReceiver infoReceiver) {
        this.infoReceiver = infoReceiver;
    }

    @Override
    public String execute() {
        return infoReceiver.execute();
    }
}
