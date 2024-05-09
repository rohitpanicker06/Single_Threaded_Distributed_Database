package commands;

public class CommandInvoker {

    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public String execute() {
        return command.execute();
    }
}
