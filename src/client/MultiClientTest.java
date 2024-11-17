package client;

import global.*;
import java.rmi.Naming;

public class MultiClientTest {
  public static void main(String[] args) {
      System.out.println("Starting MultiClientTest with 10 threads performing concurrent PUT and GET operations.");

      for (int i = 0; i < 10; i++) {
          new Thread(() -> {
              try {
                  // Get the current thread ID to make each key-value pair unique
                  long threadId = Thread.currentThread().getId();
                  KeyValueStoreInterface kvStore = (KeyValueStoreInterface) Naming.lookup("rmi://server-container:1099/KeyValueStore");

                  // PUT operation with descriptive output
                  String key = "k" + threadId;
                  String value = "v" + threadId;
                  System.out.println("Thread " + threadId + " performing PUT operation: Key = " + key + ", Value = " + value);
                  kvStore.put(key, value);

                  // GET operation with descriptive output
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
