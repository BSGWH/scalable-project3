package client;

import global.KeyValueStoreInterface;

import java.rmi.Naming;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MultiClientTest {
    public static void main(String[] args) {
        System.out.println("Starting MultiClientTest with 3 threads performing concurrent PUT and GET operations.");

        // List of server URLs
        List<String> serverUrls = Arrays.asList(
            "rmi://localhost:1099/KeyValueStore",
            "rmi://localhost:1100/KeyValueStore",
            "rmi://localhost:1101/KeyValueStore",
            "rmi://localhost:1102/KeyValueStore",
            "rmi://localhost:1103/KeyValueStore"
        );

        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                try {
                    // Randomly select a server
                    Random random = new Random();
                    String serverUrl = serverUrls.get(random.nextInt(serverUrls.size()));
                    KeyValueStoreInterface kvStore = (KeyValueStoreInterface) Naming.lookup(serverUrl);
                    // System.out.println("Connected to server: " + serverUrl);
                    // Get the current thread ID to make each key-value pair unique
                    long threadId = Thread.currentThread().getId();
                    String key = "k" + threadId;
                    String value = "v" + threadId;

                    // Perform PUT operation
                    System.out.println("Thread " + threadId + " performing PUT operation: Key = " + key + ", Value = " + value);
                    kvStore.put(key, value);

                    // Perform GET operation
                    System.out.println("Thread " + threadId + " performing GET operation: Key = " + key);
                    String result = kvStore.get(key);
                    System.out.println("Thread " + threadId + " received from GET operation: Value = " + result);

                } catch (Exception e) {
                    System.err.println("Exception in Thread " + Thread.currentThread().getId() + ":");
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
