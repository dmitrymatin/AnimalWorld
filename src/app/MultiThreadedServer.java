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
    ArrayList<ThreadedLogic> sessions = new ArrayList<>();

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

        startListening(port);
    }

    private void startListening(int port) throws IOException {
        listen = true;
        try {
            while (listen) {
                logger.logMessage("ServerSocket waiting for incoming requests on port " + port);
                //System.out.println("\nВ цикле ServerSocket ожидает входящие запросы от клиентов на порт " + port);

                Socket socket = serverSocket.accept();

                ThreadedLogic threadedLogic = new ThreadedLogic(socket);
                sessions.add(threadedLogic);

                logger.logMessage("ThreadedLogic instance created");
                //System.out.println("Создан экземпляр класса ThreadedLogic");

                Thread thread = new Thread(threadedLogic);
                thread.start();

                logger.logMessage("Server created new thread");
                //System.out.println("Сервер создал новый поток");
            }
        } catch (IOException e) {
            listen = false;
            throw e;
        }
    }

    public void stop() {
        try {
            for (ThreadedLogic s : sessions) {
                s.getSocket().close();

                logger.logMessage("server closed client socket");
                //System.out.println("Закрыт клиентский сокет на стороне сервера\n");
            }

            serverSocket.close();
            listen = false;

            logger.logMessage("ServerSocket closed");
            //System.out.println("Закрыт серверный сокет (ServerSocket)");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}