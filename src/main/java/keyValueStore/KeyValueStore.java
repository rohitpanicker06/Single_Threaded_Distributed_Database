package keyValueStore;

import java.util.HashMap;
import java.util.Map;

public class KeyValueStore {
    private static final Map<String, String> map = new HashMap<>();
    private static final Map<String, Long> expiryMap = new HashMap<>();

    private static KeyValueStore keyValueStore = null;


    private KeyValueStore(){
    }


    public static KeyValueStore getInstance()
    {
        if(keyValueStore == null) {
            keyValueStore = new KeyValueStore();
        }
        return keyValueStore;
    }

    public void add(String key, String value, long expiryInMillis)
    {
        map.put(key, value);
        long expiryTime = System.currentTimeMillis() + expiryInMillis;
        expiryMap.put(key, expiryTime);
    }

    public void add(String key, String value)
    {
        map.put(key, value);
    }

    public String get(String key)
    {
        Long expiryTime = expiryMap.get(key);
        if (expiryTime != null && System.currentTimeMillis() > expiryTime) {
            map.remove(key);
            expiryMap.remove(key);
            return null;
        }

        return map.get(key);
    }
}
