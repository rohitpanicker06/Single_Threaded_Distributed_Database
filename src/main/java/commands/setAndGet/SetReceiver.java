package commands.setAndGet;


import keyValueStore.KeyValueStore;

import java.util.AbstractMap;
import java.util.Map;

public class SetReceiver {
    private final Map.Entry<String, String> keyValue;
    private Long expiryTime = null;

    public SetReceiver(String[] commandArray)
    {
       this.keyValue = new AbstractMap.SimpleEntry<>(commandArray[1], commandArray[2]);
    }

    public SetReceiver(String[] commandArray, boolean setExpiry)
    {
        this.keyValue = new AbstractMap.SimpleEntry<>(commandArray[1], commandArray[2]);
        this.expiryTime = Long.valueOf(commandArray[4]);
    }

    public String execute()
    {
        if(expiryTime == null) {
            KeyValueStore.getInstance().add(keyValue.getKey(), keyValue.getValue());
        }else{
            KeyValueStore.getInstance().add(keyValue.getKey(), keyValue.getValue(), expiryTime);
        }
        return "+OK\r\n";
    }
}

