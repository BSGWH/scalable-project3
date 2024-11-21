package server;


import global.*;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.List;

public class ServerApp {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java ServerApp <server_id> <port>");
            return;
        }
        try {
            int serverId = Integer.parseInt(args[0]);
            int port = Integer.parseInt(args[1]);

            // Define replica URLs
            List<String> replicaUrls = new ArrayList<>();
            replicaUrls.add("rmi://localhost:1099/KeyValueStore"); 
            replicaUrls.add("rmi://localhost:1100/KeyValueStore");
            replicaUrls.add("rmi://localhost:1101/KeyValueStore");
            replicaUrls.add("rmi://localhost:1102/KeyValueStore");
            replicaUrls.add("rmi://localhost:1103/KeyValueStore");

            // Remove the current server's URL from the replica list
            String currentServerUrl = "rmi://localhost:" + port + "/KeyValueStore";
            replicaUrls.remove(currentServerUrl);

            LocateRegistry.createRegistry(port);
            KeyValueStoreInterface kvStore = new KeyValueStoreImpl(serverId, replicaUrls);
            Naming.rebind(currentServerUrl, kvStore);

            System.out.println("Server " + serverId + " is running on port " + port + "...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
