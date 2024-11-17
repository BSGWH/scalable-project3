package client;

import java.io.IOException;

public abstract class AbstractClient {
  
    protected String host;
    protected int port;

    // Constructor to set up server hostname and port
    public AbstractClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public abstract void connect() throws IOException;
    public abstract void sendRequest(String request) throws IOException;
    public abstract String getResponse() throws IOException;
    public abstract void disconnect();

    public void launchInteractiveMode() throws IOException {
        throw new UnsupportedOperationException("Interactive mode is not available.");
    }
}
