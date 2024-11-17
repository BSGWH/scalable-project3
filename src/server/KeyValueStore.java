package server;

import java.util.concurrent.ConcurrentHashMap;

public class KeyValueStore {
    
    private final ConcurrentHashMap<String, String> keyValueMap = new ConcurrentHashMap<>();

    // Stores the key-value pair in the store
    public synchronized String put(String key, String value) {
        keyValueMap.put(key, value);
        return "OK";
    }

    // Retrieves the value associated with the key
    public synchronized String get(String key) {
        return keyValueMap.getOrDefault(key, "ERROR: Key not found.");
    }

    // Deletes the key-value pair from the store
    public synchronized String delete(String key) {
        if (keyValueMap.containsKey(key)) {
            keyValueMap.remove(key);
            return "OK";
        }
        return "ERROR: Key not found.";
    }
}
