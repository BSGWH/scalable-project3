package server;

import global.KeyValueStoreInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class KeyValueStoreImpl extends UnicastRemoteObject implements KeyValueStoreInterface {
    private final ConcurrentHashMap<String, String> store;
    private final List<String> replicaUrls; // List of other server URLs
    private final int serverId; // Unique ID for this server

    protected KeyValueStoreImpl(int serverId, List<String> replicaUrls) throws RemoteException {
        this.store = new ConcurrentHashMap<>();
        this.replicaUrls = replicaUrls;
        this.serverId = serverId;
    }

    @Override
    public synchronized String put(String key, String value) {
        try {
            // Initiate two-phase commit
            if (initiateTwoPhaseCommit("PUT", key, value)) {
                store.put(key, value);
                return "OK";
            } else {
                return "ERROR: PUT operation failed due to inconsistency.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR: Internal error during PUT.";
        }
    }

    @Override
    public synchronized String get(String key) {
        return store.getOrDefault(key, "ERROR: Key not found.");
    }

    @Override
    public synchronized String delete(String key) {
        try {
            // Initiate two-phase commit
            if (initiateTwoPhaseCommit("DELETE", key, null)) {
                return store.remove(key) != null ? "OK" : "ERROR: Key not found.";
            } else {
                return "ERROR: DELETE operation failed due to inconsistency.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR: Internal error during DELETE.";
        }
    }

    private boolean initiateTwoPhaseCommit(String operation, String key, String value) {
        try {
            // Phase 1: Prepare
            for (String replicaUrl : replicaUrls) {
                KeyValueStoreInterface replica = (KeyValueStoreInterface) java.rmi.Naming.lookup(replicaUrl);
                boolean ack = replica.prepare(serverId, operation, key, value);
                if (!ack) {
                    // If any replica rejects, rollback
                    for (String rollbackReplicaUrl : replicaUrls) {
                        KeyValueStoreInterface rollbackReplica = (KeyValueStoreInterface) java.rmi.Naming.lookup(rollbackReplicaUrl);
                        rollbackReplica.rollback(serverId, operation, key);
                    }
                    return false;
                }
            }
    
            // Phase 2: Commit
            for (String replicaUrl : replicaUrls) {
                KeyValueStoreInterface replica = (KeyValueStoreInterface) java.rmi.Naming.lookup(replicaUrl);
                replica.commit(serverId, operation, key, value);
            }
    
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                // Rollback in case of an exception
                for (String rollbackReplicaUrl : replicaUrls) {
                    KeyValueStoreInterface rollbackReplica = (KeyValueStoreInterface) java.rmi.Naming.lookup(rollbackReplicaUrl);
                    rollbackReplica.rollback(serverId, operation, key);
                }
            } catch (Exception rollbackException) {
                rollbackException.printStackTrace();
            }
            return false;
        }
    }
    

    @Override
    public synchronized boolean prepare(int coordinatorId, String operation, String key, String value) throws RemoteException {
        try {
            // Simulate preparation phase (e.g., lock resources)
            System.out.println("Server " + serverId + " preparing " + operation + " for key: " + key);
            TimeUnit.MILLISECONDS.sleep(100); // Simulate delay
            return true; // Always ready to commit
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false; // Fail preparation if interrupted
        }
    }

    @Override
    public synchronized void commit(int coordinatorId, String operation, String key, String value) throws RemoteException {
        System.out.println("Server " + serverId + " committing " + operation + " for key: " + key);
        if ("PUT".equalsIgnoreCase(operation)) {
            store.put(key, value);
        } else if ("DELETE".equalsIgnoreCase(operation)) {
            store.remove(key);
        }
    }

    @Override
    public synchronized void rollback(int coordinatorId, String operation, String key) throws RemoteException {
        System.out.println("Server " + serverId + " rolling back " + operation + " for key: " + key);
        // No specific rollback logic needed for this implementation
    }
}
