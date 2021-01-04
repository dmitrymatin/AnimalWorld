package app;

import java.io.*;
import java.net.Socket;
import java.util.Random;

public class Session implements Runnable {
    private final Socket clientSocket;
    private final Logger logger;
    private BufferedWriter out;
    private BufferedReader in;
    //private int data = 50;

    public Session(Socket clientSocket, Logger logger) {
        this.clientSocket = clientSocket;
        this.logger = logger;
    }

    public void run() {
        try {
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if ("stp".equals(inputLine)) {
                    logger.logMessage("disconnecting client"); // todo: EDT
                    break;
                }

                switch (inputLine) {
                    case "cmd1":
                        out.write("response to cmd1");
                        break;
                    case "cmd2":
                        out.write("response to cmd2");
                        break;
                    default:
                        out.write("unknown command");
                        break;
                }

                logger.logMessage("responded to client " + clientSocket.toString());
            }

            closeSession();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeSession() throws IOException {
        logger.logMessage("closing session " + this.hashCode());
        in.close();
        out.close();
        clientSocket.close();
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
}
