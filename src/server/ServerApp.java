package server;

import global.*;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.net.InetAddress;
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

            // Get the server's hostname or IP address dynamically
            String hostname = InetAddress.getLocalHost().getHostAddress();

            // Define replica URLs using Docker service names
            List<String> replicaUrls = new ArrayList<>();
            replicaUrls.add("rmi://server1:1099/KeyValueStore");
            replicaUrls.add("rmi://server2:1100/KeyValueStore");
            replicaUrls.add("rmi://server3:1101/KeyValueStore");
            replicaUrls.add("rmi://server4:1102/KeyValueStore");
            replicaUrls.add("rmi://server5:1103/KeyValueStore");

            // Remove the current server's URL from the replica list
            String currentServerUrl = "rmi://" + hostname + ":" + port + "/KeyValueStore";
            replicaUrls.removeIf(url -> url.contains("server" + serverId));

            // Create RMI registry and bind the server object
            LocateRegistry.createRegistry(port);
            KeyValueStoreInterface kvStore = new KeyValueStoreImpl(serverId, replicaUrls);
            Naming.rebind(currentServerUrl, kvStore);

            System.out.println("Server " + serverId + " is running on host " + hostname + " and port " + port + "...");

            // Add shutdown hook to unbind RMI object
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    Naming.unbind(currentServerUrl);
                    System.out.println("Server " + serverId + " unbound from RMI registry.");
                } catch (Exception e) {
                    System.err.println("Error during server shutdown: " + e.getMessage());
                    e.printStackTrace();
                }
            }));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
