package server;

public class Response {
    
    private final String status;
    private final String details;

    public Response(String status, String details) {
        this.status = status;
        this.details = details;
    }

    public String getStatus() {
        return status;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return status + " - " + details;
    }
}
