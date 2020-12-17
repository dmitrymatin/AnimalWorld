package app;

import data.StorageManager;

import java.io.IOException;

public class GeneralController {
    private static MultiThreadedServer server = MultiThreadedServer.getUniqueInstance();
    private static StorageManager storageManager = StorageManager.getInstance();

    public static boolean startServer(int port) {
        try{
            server.launch(port);
            return true;
        }
        catch (IOException exception){
            return false;
        }
    }

    public static void stopServer() {
        server.stop();
    }

    public static void persistData() {
        storageManager.save();
    }
}
