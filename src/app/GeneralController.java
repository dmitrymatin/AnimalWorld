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

    public static void startServer(int port) /*throws Exception*/ {
        Runnable runnableServerLauncher = new Runnable() {
            public void run() {
                try {
                    logger.logMessage("-------");// TODO: EDT
                    server.launch(port, logger);
                    logger.logMessage("-------");// TODO: EDT
                } catch (Exception e) {
                    logger.logMessage(e.getMessage());// TODO: EDT
                    server.stop();
                }
            }
        };
        Thread serverLauncherThread = new Thread(runnableServerLauncher);
        serverLauncherThread.start();
    }

    public static void stopServer() {
        server.stop();
        logger.logMessage("сервер остановлен " + LocalDateTime.now()); //TODO: EDT
    }

    public static void persistData() {
        storageManager.save();
    }
}
