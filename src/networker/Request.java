package networker;

import java.util.ArrayList;
import java.util.List;

public class Request {
    private final String command;
    private final ArrayList<String> args = new ArrayList<>();

    public Request(String command, String... args) {
        this.command = command;
        if (args != null)
            this.args.addAll(List.of(args));
    }

    public String getCommand() {
        return command;
    }

    public ArrayList<String> getArgs() {
        return args;
    }
}
