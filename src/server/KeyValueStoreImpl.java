package server;

// KeyValueStoreImpl.java
import global.*;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentHashMap;

public class KeyValueStoreImpl extends UnicastRemoteObject implements KeyValueStoreInterface {
    private ConcurrentHashMap<String, String> store;

    protected KeyValueStoreImpl() throws RemoteException {
        store = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized String put(String key, String value) {
        return store.put(key, value);
    }

    @Override
    public synchronized String get(String key) {
        return store.get(key);
    }

    @Override
    public synchronized String delete(String key) {
        return store.remove(key);
    }
}
