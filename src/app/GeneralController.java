package app;

import data.StorageManager;

import java.io.IOException;

public class GeneralController {
    private static final StorageManager storageManager = StorageManager.getInstance();
    private static MultiThreadedServer server = null;
    private static ServerForm serverForm = null;
    private static ServerFormListener serverFormListener = null;

    private static Logger logger = null;

    public static void startApp() {
        try {
            storageManager.load();
        } catch (Exception ex) {
            // todo: добавить создание окна с сообщением, что не получилось запустить сервер
            return;
        }

        server = MultiThreadedServer.getInstance();

        serverForm = new ServerForm("Сервер");
        logger = new FormLogger(serverForm.getTextArea());
        serverFormListener = new ServerFormListener(serverForm, logger);
    }

    public static void startServer(int port) /*throws Exception*/ {
        Runnable runnableServerLauncher = new Runnable() {
            public void run() {
                try {
                    logger.logMessage("-------");
                    server.launch(port, logger);
                    logger.logMessage("-------");
                } catch (Exception e) {
                    logger.logMessage(e.getMessage());
                    server.stop();
                }
            }
        };
        Thread serverLauncherThread = new Thread(runnableServerLauncher);
        serverLauncherThread.start();

        // todo: notify if server has been stopped
    }

    public static void stopServer() {
        server.stop();
    }

    public static void persistData() {
        storageManager.save();
    }
}
