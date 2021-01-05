package networker;

public class NetworkController {
    public static Request parseQueryString(String queryString) {
        Request request = null;

        // query format ::= <command>?[<arg1>][&<arg2>]...[&<argN>]
        String command;
        String argsString;

        String[] queryParts = queryString.split("\\?");
        String[] args = null;
        if (queryParts.length > 0) {
            command = queryParts[0];
            if (queryParts.length > 1) {
                argsString = queryParts[1];
                args = argsString.split("&");
            }
            request = new Request(command, args);
        }
        return request;
    }

    public static void prepareResponse(Request request) {
//        switch (request.getCommand()){
//            case ""
//        }
    }
}
