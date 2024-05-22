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
        String value = KeyValueStore.keyValueStore.get(key);
        return "$"+ value.length()+"\r\n"+value+"\r\n";
    }
}
