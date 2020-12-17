package app;

import data.StorageManager;

import java.io.IOException;

public class GeneralController {
    private static final StorageManager storageManager = StorageManager.getInstance();
    private static final MultiThreadedServer server = MultiThreadedServer.getInstance();
    private static ServerForm serverForm = null;
    private static ServerFormListener serverFormListener = null;

    public static void startApp() {
        try {
            storageManager.load();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        serverForm = new ServerForm("Сервер");
        serverFormListener = new ServerFormListener(serverForm);
    }

    public static boolean startServer(int port) {
        try {
            server.launch(port);
            return true;
        } catch (IOException exception) {
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
