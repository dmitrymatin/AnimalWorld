package app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
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
        } catch (IOException e) {
            throw new Exception("Error occurred while trying to launch server", e);
        }

        Runnable runnableServerLauncher = new Runnable() {
            public void run() {
                startListening(port);
            }
        };
        Thread serverLauncherThread = new Thread(runnableServerLauncher);
        serverLauncherThread.start();
    }

    private void startListening(int port) {
        listen = true;
        try {
            while (listen) {
                logger.logMessage("ServerSocket waiting for incoming requests on port " + port); // TODO: EDT
                Socket socket = serverSocket.accept();
                logger.logMessage("server received client request using socket @" + socket.hashCode() + " " + socket.toString());

                Session session = new Session(socket, this, logger);
                sessions.add(session);
                logger.logMessage("Session instance created " + session.hashCode()); // TODO: EDT

                Thread thread = new Thread(session);
                thread.start();

                logger.logMessage("Server created new thread " + thread.getName()); // TODO: EDT
            }
        } catch (SocketException se) {
            logger.logMessage("Сокет закрыт");
        } catch (IOException e) {
            logger.logMessage("Произошла ошибка во время работы сервера: " + e.getMessage());
            stop();
        } finally {
            terminateSessions();
        }
    }

    private void terminateSessions() {
        for (Session s : sessions) {
            removeSession(s);
        }
    }

    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            logger.logMessage("Произошла ошибка при закрытии сервера " + e.getMessage());
        }
        finally {
            listen = false;
        }
    }

    public void removeSession(Session session) {
        try {
            session.closeSession();
            sessions.remove(session);
        } catch (IOException ex) {
            logger.logMessage("Произошла ошибка во время удаления сессии: " + ex.getMessage());
        }
    }
}
