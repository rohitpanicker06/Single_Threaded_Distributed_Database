package commands.setAndGet;

import keyValueStore.KeyValueStore;

public class GetReceiver {

    private String key;

    public GetReceiver(String key)
    {
        this.key = key;

    }

    public String execute()
    {
        String value = KeyValueStore.getInstance().get(key);
        if(value == null)
        {
            return "$-1\r\n";
        }
        return "$"+ value.length()+"\r\n"+value+"\r\n";
    }
}
