package app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MultiThreadedServer {
    private static MultiThreadedServer uniqueInstance = null;

    private boolean listen = false;
    private Logger logger = null;

    private ServerSocket serverSocket = null;
    private ArrayList<Session> sessions = new ArrayList<>();
    private ResourceBundle rb;

    public static MultiThreadedServer getInstance(ResourceBundle resourceBundle) {
        if (uniqueInstance == null) {
            uniqueInstance = new MultiThreadedServer(resourceBundle);
        }

        return uniqueInstance;
    }

    private MultiThreadedServer(ResourceBundle resourceBundle) {
        this.rb = resourceBundle;
    }

    public void launch(int port, Logger logger) throws Exception {
        if (listen)
            throw new Exception(rb.getString("ERROR_SERVER_ALREADY_STARTED"));
        this.logger = logger;

        try {
            serverSocket = new ServerSocket(port);
            logger.logMessage(rb.getString("CREATED_SERVER_SOCKET_ON_PORT") + ": " + port);
        } catch (IOException e) {
            throw new Exception(rb.getString("ERROR_ON_SERVER_START"), e);
        }

        Runnable runnableServerLauncher = new Runnable() {
            public void run() {
                startListening();
            }
        };
        Thread serverLauncherThread = new Thread(runnableServerLauncher);
        serverLauncherThread.start();
    }

    private void startListening() {
        listen = true;
        try {
            while (listen) {
                logger.logMessage(rb.getString("SOCKET_AWAIT") + ": " + serverSocket.getLocalPort()); // TODO: EDT
                Socket socket = serverSocket.accept();
                logger.logMessage(rb.getString("CLIENT_CONNECT") + ": " + socket.getInetAddress());

                Session session = new Session(socket, this, logger, rb);
                sessions.add(session);
                logger.logMessage(rb.getString("SESSION_CREATED"));

                Thread thread = new Thread(session);
                thread.start();

                logger.logMessage(rb.getString("THREAD_CREATED") + ": " + thread.getName());
            }
        } catch (SocketException se) {
            logger.logMessage(rb.getString("SOCKET_CLOSED"));
        } catch (IOException e) {
            logger.logMessage(rb.getString("ERROR_SERVER_FAIL") + ": " + e.getMessage());
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
            logger.logMessage(rb.getString("ERROR_SERVER_CLOSE_FAIL") + ": " + e.getMessage());
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
            logger.logMessage(rb.getString("ERROR_SESSION_REMOVE_FAIL") + ": " + ex.getMessage());
        }
    }
}
