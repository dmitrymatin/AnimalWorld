package networker;

import java.util.Locale;
import java.util.ResourceBundle;

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
        ResourceBundle rb = ResourceBundle.getBundle("ResourceBundle", Locale.getDefault());
        boolean closureStatus = responseString.startsWith(rb.getString("RESPONSE_CLOSURE_STATUS_FLAG"));
        boolean errorStatus = responseString.startsWith(rb.getString("RESPONSE_ERROR_STATUS_FLAG"));

        return new Response(closureStatus, errorStatus, responseString);
    }

    public boolean isClosureStatus() {
        return closureStatus;
    }

    public boolean isErrorStatus() {
        return errorStatus;
    }

    public String getMessage() {
        return message;
    }
}
