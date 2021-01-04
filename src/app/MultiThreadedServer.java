package app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MultiThreadedServer {
    private static MultiThreadedServer uniqueInstance = null;

    private boolean listen = false;
    private Logger logger = null;

    private ServerSocket serverSocket = null;
    ArrayList<Session> sessions = new ArrayList<>();

    public static MultiThreadedServer getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new MultiThreadedServer();
        }

        return uniqueInstance;
    }

    private MultiThreadedServer() {
    }

    public void launch(int port, Logger logger) throws Exception {
        if (listen)
            throw new Exception("server has already been started");

        this.logger = logger;

        try {
            serverSocket = new ServerSocket(port);

            logger.logMessage("created ServerSocket, port: " + port);
            //System.out.println("Создан ServerSocket, port: " + port);
        } catch (IOException e) {
            throw new Exception("Error occurred while trying to launch server", e);
        }

        Runnable runnableServerLauncher = new Runnable() {
            public void run() {
                try {
                    startListening(port);
                } catch (Exception e) {
                    logger.logMessage("Произошла ошибка во время работы сервера: " + e.getMessage());// TODO: EDT
                    stop();
                }
            }
        };
        Thread serverLauncherThread = new Thread(runnableServerLauncher);
        serverLauncherThread.start();
    }

    private void startListening(int port) throws IOException {
        listen = true;
        try {
            while (listen) {
                logger.logMessage("ServerSocket waiting for incoming requests on port " + port); // TODO: EDT
                //System.out.println("\nВ цикле ServerSocket ожидает входящие запросы от клиентов на порт " + port);

                Socket socket = serverSocket.accept();

                Session session = new Session(socket, logger);
                sessions.add(session);

                logger.logMessage("Session instance created " + session.hashCode()); // TODO: EDT
                //System.out.println("Создан экземпляр класса Session");

                Thread thread = new Thread(session);
                thread.start();

                logger.logMessage("Server created new thread" + thread.getName()); // TODO: EDT
                //System.out.println("Сервер создал новый поток");
            }
        } catch (IOException e) {
            listen = false;
            throw e;
        }
    }

    public void stop() {
        try {
            for (Session s : sessions) {
                s.getClientSocket().close();

                logger.logMessage("server closed client socket"); // TODO: EDT
                //System.out.println("Закрыт клиентский сокет на стороне сервера\n");
            }

            serverSocket.close();
            listen = false;

            logger.logMessage("ServerSocket closed"); // TODO: EDT
            //System.out.println("Закрыт серверный сокет (ServerSocket)");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
