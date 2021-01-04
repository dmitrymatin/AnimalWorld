package app;

import data.StorageManager;

import java.io.IOException;
import java.time.LocalDateTime;

public class GeneralController {
    private static final StorageManager storageManager = StorageManager.getInstance();
    private static MultiThreadedServer server = null;
    private static ServerForm serverForm = null;
    private static ServerFormListener serverFormListener = null;
    private static Logger logger = null;

    public static void startApp() {
        String startStatus = "";
        try {
            storageManager.load();
            startStatus += "содержимое хранилища загружено";
        } catch (Exception ex) {
            startStatus += "хранилище пусто";
        }

        server = MultiThreadedServer.getInstance();
        serverForm = new ServerForm("Сервер");
        logger = new FormLogger(serverForm.getTextArea());
        serverFormListener = new ServerFormListener(serverForm, logger);
        logger.logMessage(startStatus);
    }

    public static void startServer(int port) {
        try {
            logger.logMessage("-------");
            server.launch(port, logger);
            logger.logMessage("-------");
        } catch (Exception e) {
            logger.logMessage(e.getMessage());
            server.stop();
        }
    }

    public static void stopServer() {
        server.stop();
        logger.logMessage("сервер остановлен " + LocalDateTime.now());
    }

    public static void persistData() {
        storageManager.save();
    }
}
