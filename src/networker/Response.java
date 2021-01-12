package networker;

public class Response {
    private final boolean closureStatus;
    private final boolean errorStatus;
    private final String message;

    public Response(boolean closureStatus, boolean errorStatus, String message) {
        this.closureStatus = closureStatus;
        this.errorStatus = errorStatus;
        this.message = message;
    }

    public static Response parseResponse(String responseString) {
        boolean closureStatus = responseString.substring(0, 4).contains("стоп");
        boolean errorStatus = responseString.substring(0, 6).contains("ошибка");

        return new Response(closureStatus, errorStatus, responseString);
    }

    public boolean isClosureStatus() {
        return closureStatus;
    }

    public String getMessage() {
        return message;
    }
}
