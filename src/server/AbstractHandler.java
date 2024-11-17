package server;

public abstract class AbstractHandler {
    
    protected KeyValueStore keyValueStore;

    public AbstractHandler(KeyValueStore keyValueStore) {
        this.keyValueStore = keyValueStore;
    }

    // Processes incoming requests and returns the corresponding response
    public String handleRequest(String request) {
        String[] requestParts = request.split(" ", 3);
        if (requestParts.length < 2) {
            return new Response("ERROR", "Malformed request.").toString();
        }

        String command = requestParts[0].toUpperCase();
        String key = requestParts[1];

        switch (command) {
            case "PUT":
                return handlePutCommand(requestParts, key);
            case "GET":
                return handleGetCommand(key);
            case "DELETE":
                return handleDeleteCommand(key);
            default:
                return new Response("ERROR", "Unknown command \"" + command + "\".").toString();
        }
    }

    // Handles the PUT command
    private String handlePutCommand(String[] requestParts, String key) {
        if (requestParts.length == 3) {
            keyValueStore.put(key, requestParts[2]);
            return new Response("OK", "Key-value pair added.").toString();
        } else {
            return new Response("ERROR", "PUT needs a value.").toString();
        }
    }

    // Handles the GET command
    private String handleGetCommand(String key) {
        String value = keyValueStore.get(key);
        if (value.startsWith("ERROR")) {
            return new Response("ERROR", value).toString();
        } else {
            return new Response("OK", value).toString();
        }
    }

    // Handles the DELETE command
    private String handleDeleteCommand(String key) {
        String deleteResult = keyValueStore.delete(key);
        if (deleteResult.startsWith("ERROR")) {
            return new Response("ERROR", deleteResult).toString();
        } else {
            return new Response("OK", "Key-value pair deleted.").toString();
        }
    }
}
