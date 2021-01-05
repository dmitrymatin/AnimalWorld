package networker;

import java.util.ArrayList;
import java.util.List;

public class Request {
    private String command;
    private ArrayList<String> args = null;

    public Request(String command, String... args) {
        this.command = command;
        if (args != null)
            this.args = new ArrayList<String>(List.of(args));
    }

    public String getCommand() {
        return command;
    }

    public ArrayList<String> getArgs() {
        return args;
    }
}
