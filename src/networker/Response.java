package networker;

public class Response {
    private final boolean closureStatus;
    private final String message;

    public Response(boolean closureStatus, String message) {
        this.closureStatus = closureStatus;
        this.message = message;
    }

    public static Response parseResponse(String responseString) {
        boolean closureStatus = responseString.contains("сессия завершена");
        String message = responseString;

        return new Response(closureStatus, message);
    }

    public boolean isClosureStatus() {
        return closureStatus;
    }

    public String getMessage() {
        return message;
    }
}
