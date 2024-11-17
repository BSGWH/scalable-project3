# Execution of the code

### Clean up existing resources, build the images, create the network, and run the server

```bash
./deploy.sh
```

### Run the client to perform 5 PUTs, 5 GETs, 5 DELETEs

Example:

```bash
./run_client.sh
```

### Example of five of each operation and user interaction

```bash
----------Running the client container----------
Pre-populating the Key-Value store:
Store populated with 5 keys.

Performing 5 GET operations:
key1: value1
key2: value2
key3: value3
key4: value4
key5: value5

Performing 5 DELETE operations:
Deleted keys: key1, key2, key3, key4, key5

Verifying deletions:
key1: null
key2: null
key3: null
key4: null
key5: null
```

### Run the multi client test to test multiple outstanding client requests at once

Example:

```bash
./run_multi_client.sh
```

### Example output

```bash
----------Running the multi-threaded client container----------
Starting MultiClientTest with 10 threads performing concurrent PUT and GET operations.
Thread 21 performing PUT operation: Key = k21, Value = v21
Thread 17 performing PUT operation: Key = k17, Value = v17
Thread 20 performing PUT operation: Key = k20, Value = v20
Thread 13 performing PUT operation: Key = k13, Value = v13
Thread 19 performing PUT operation: Key = k19, Value = v19
Thread 15 performing PUT operation: Key = k15, Value = v15
Thread 16 performing PUT operation: Key = k16, Value = v16
Thread 12 performing PUT operation: Key = k12, Value = v12
Thread 14 performing PUT operation: Key = k14, Value = v14
Thread 18 performing PUT operation: Key = k18, Value = v18
Thread 21 performing GET operation: Key = k21
Thread 20 performing GET operation: Key = k20
Thread 12 performing GET operation: Key = k12
Thread 19 performing GET operation: Key = k19
Thread 16 performing GET operation: Key = k16
Thread 18 performing GET operation: Key = k18
Thread 14 performing GET operation: Key = k14
Thread 17 performing GET operation: Key = k17
Thread 19 received from GET operation: Value = v19
Thread 21 received from GET operation: Value = v21
Thread 18 received from GET operation: Value = v18
Thread 17 received from GET operation: Value = v17
Thread 15 performing GET operation: Key = k15
Thread 13 performing GET operation: Key = k13
Thread 15 received from GET operation: Value = v15
Thread 12 received from GET operation: Value = v12
Thread 20 received from GET operation: Value = v20
Thread 14 received from GET operation: Value = v14
Thread 13 received from GET operation: Value = v13
Thread 16 received from GET operation: Value = v16
```
