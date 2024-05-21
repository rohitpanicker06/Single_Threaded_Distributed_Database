package commands;

public class EchoReceiver {
    private final String message;

    public EchoReceiver(String message)
    {
        this.message = message;
    }
    public String execute()
    {
        return "$"+ message.length()+"\r\n"+message+"\r\n";
    }
}
