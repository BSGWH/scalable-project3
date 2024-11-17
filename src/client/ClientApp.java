package client;

// ClientApp.java
import global.KeyValueStoreInterface;
import java.rmi.Naming;

public class ClientApp {
    public static void main(String[] var0) {
        try {
            // Step 1: Look up the remote KeyValueStore object from the RMI registry
            KeyValueStoreInterface var1 = (KeyValueStoreInterface) Naming.lookup("rmi://server-container:1099/KeyValueStore");
            
            // Step 2: Pre-populate the key-value store with 5 keys (PUT operations)
            System.out.println("Pre-populating the Key-Value store:");
            var1.put("key1", "value1");
            var1.put("key2", "value2");
            var1.put("key3", "value3");
            var1.put("key4", "value4");
            var1.put("key5", "value5");
            System.out.println("Store populated with 5 keys.");

            // Step 3: Perform 5 GET operations to retrieve the values
            System.out.println("\nPerforming 5 GET operations:");
            System.out.println("key1: " + var1.get("key1"));  // Expected: value1
            System.out.println("key2: " + var1.get("key2"));  // Expected: value2
            System.out.println("key3: " + var1.get("key3"));  // Expected: value3
            System.out.println("key4: " + var1.get("key4"));  // Expected: value4
            System.out.println("key5: " + var1.get("key5"));  // Expected: value5

            // Step 4: Perform 5 DELETE operations
            System.out.println("\nPerforming 5 DELETE operations:");
            var1.delete("key1");
            var1.delete("key2");
            var1.delete("key3");
            var1.delete("key4");
            var1.delete("key5");
            System.out.println("Deleted keys: key1, key2, key3, key4, key5");

            // Step 5: Verify the deletions by attempting to GET the deleted keys
            System.out.println("\nVerifying deletions:");
            System.out.println("key1: " + var1.get("key1"));  // Expected: null or appropriate message
            System.out.println("key2: " + var1.get("key2"));  // Expected: null or appropriate message
            System.out.println("key3: " + var1.get("key3"));  // Expected: null or appropriate message
            System.out.println("key4: " + var1.get("key4"));  // Expected: null or appropriate message
            System.out.println("key5: " + var1.get("key5"));  // Expected: null or appropriate message

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}