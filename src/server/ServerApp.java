package server;

// ServerApp.java
import global.*;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class ServerApp {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099); // Start RMI registry
            KeyValueStoreInterface kvStore = new KeyValueStoreImpl(); // Implement this class
            Naming.rebind("rmi://localhost:1099/KeyValueStore", kvStore); // Bind the RMI object
            System.out.println("RMI server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

