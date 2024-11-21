package global;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface KeyValueStoreInterface extends Remote {
    String put(String key, String value) throws RemoteException;
    String get(String key) throws RemoteException;
    String delete(String key) throws RemoteException;

    // Add the following methods for the Two-Phase Commit protocol
    boolean prepare(int coordinatorId, String operation, String key, String value) throws RemoteException;
    void commit(int coordinatorId, String operation, String key, String value) throws RemoteException;
    void rollback(int coordinatorId, String operation, String key) throws RemoteException;
}
