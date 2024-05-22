package commands.setAndGet;


import keyValueStore.KeyValueStore;

import java.util.AbstractMap;
import java.util.Map;

public class SetReceiver {
    private final Map.Entry<String, String> keyValue;

    public SetReceiver(String key, String value)
    {
       this.keyValue = new AbstractMap.SimpleEntry<>(key, value);
    }
    public String execute()
    {
        KeyValueStore.keyValueStore.put(keyValue.getKey(), keyValue.getValue());
        return "+OK\r\n";
    }
}

