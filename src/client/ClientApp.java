package client;

import global.KeyValueStoreInterface;

import java.rmi.Naming;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ClientApp {
    public static void main(String[] args) {
        try {
            // Step 1: Define a list of server URLs
            List<String> serverUrls = Arrays.asList(
                "rmi://localhost:1099/KeyValueStore",
                "rmi://localhost:1100/KeyValueStore",
                "rmi://localhost:1101/KeyValueStore",
                "rmi://localhost:1102/KeyValueStore",
                "rmi://localhost:1103/KeyValueStore"
            );

            // For testing with one server
            System.out.println("\n*****Testing with one server*****\n");
            // Select a server randomly
            Random random = new Random();
            String selectedServerUrl = serverUrls.get(random.nextInt(serverUrls.size()));
            KeyValueStoreInterface kvStore = (KeyValueStoreInterface) Naming.lookup(selectedServerUrl);

            System.out.println("Connected to server: " + selectedServerUrl);

            // Perform operations
            // Pre-populate the Key-Value store
            System.out.println("Pre-populating the Key-Value store:");
            kvStore.put("key1", "value1");
            kvStore.put("key2", "value2");
            kvStore.put("key3", "value3");
            kvStore.put("key4", "value4");
            kvStore.put("key5", "value5");
            System.out.println("Store populated with 5 keys.");

            // Perform GET operations
            System.out.println("\nPerforming 5 GET operations:");
            System.out.println("key1: " + kvStore.get("key1"));
            System.out.println("key2: " + kvStore.get("key2"));
            System.out.println("key3: " + kvStore.get("key3"));
            System.out.println("key4: " + kvStore.get("key4"));
            System.out.println("key5: " + kvStore.get("key5"));

            // Perform DELETE operations
            System.out.println("\nPerforming 5 DELETE operations:");
            kvStore.delete("key1");
            kvStore.delete("key2");
            kvStore.delete("key3");
            kvStore.delete("key4");
            kvStore.delete("key5");
            System.out.println("Deleted keys: key1, key2, key3, key4, key5");

            // Verify deletions
            System.out.println("\nVerifying deletions:");
            System.out.println("key1: " + kvStore.get("key1"));
            System.out.println("key2: " + kvStore.get("key2"));
            System.out.println("key3: " + kvStore.get("key3"));
            System.out.println("key4: " + kvStore.get("key4"));
            System.out.println("key5: " + kvStore.get("key5"));

            // Add a gap
            System.out.println("\n*****Resting*****\n");
            Thread.sleep(2000);

            // For testing with multiple servers
            System.out.println("\n*****Testing with multiple servers*****\n");
            // Perform PUT operations using Server 1 and Server 2
            KeyValueStoreInterface server1 = (KeyValueStoreInterface) Naming.lookup(serverUrls.get(0));
            KeyValueStoreInterface server2 = (KeyValueStoreInterface) Naming.lookup(serverUrls.get(1));

            System.out.println("Using Server 1 for PUT operations:");
            server1.put("key1", "value1");
            server1.put("key2", "value2");
            System.out.println("Added key1 and key2 using Server 1.");

            System.out.println("Using Server 2 for additional PUT operations:");
            server2.put("key3", "value3");
            server2.put("key4", "value4");
            System.out.println("Added key3 and key4 using Server 2.");

            // Perform GET operations using Server 3
            KeyValueStoreInterface server3 = (KeyValueStoreInterface) Naming.lookup(serverUrls.get(2));
            System.out.println("\nUsing Server 3 for GET operations:");
            System.out.println("key1: " + server3.get("key1"));  // Expected: value1
            System.out.println("key2: " + server3.get("key2"));  // Expected: value2
            System.out.println("key3: " + server3.get("key3"));  // Expected: value3
            System.out.println("key4: " + server3.get("key4"));  // Expected: value4

            // Perform DELETE operations using Server 4
            KeyValueStoreInterface server4 = (KeyValueStoreInterface) Naming.lookup(serverUrls.get(3));
            System.out.println("\nUsing Server 4 for DELETE operations:");
            server4.delete("key1");
            server4.delete("key2");
            server4.delete("key3");
            server4.delete("key4");
            System.out.println("Deleted keys: key1, key2, key3, key4 using Server 4.");

            // Verify deletions using Server 5
            KeyValueStoreInterface server5 = (KeyValueStoreInterface) Naming.lookup(serverUrls.get(4));
            System.out.println("\nUsing Server 5 to verify deletions:");
            System.out.println("key1: " + server5.get("key1"));  // Expected: ERROR: Key not found.
            System.out.println("key2: " + server5.get("key2"));  // Expected: ERROR: Key not found.
            System.out.println("key3: " + server5.get("key3"));  // Expected: ERROR: Key not found.
            System.out.println("key4: " + server5.get("key4"));  // Expected: ERROR: Key not found.

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
