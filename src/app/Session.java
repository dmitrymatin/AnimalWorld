package app;

import networker.NetworkController;
import networker.Request;
import networker.Response;

import java.io.*;
import java.net.Socket;

public class Session implements Runnable {
    private final Socket clientSocket;
    private final Logger logger;
    private DataOutputStream out;
    private DataInputStream in;

    public Session(Socket clientSocket, Logger logger) {
        this.clientSocket = clientSocket;
        this.logger = logger;
    }

    public void run() {
        try {
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new DataInputStream(clientSocket.getInputStream());

            logger.logMessage("server prepared in/out streams");

            String inputLine = null;
            out.writeUTF("вы подключены!");
            logger.logMessage("отправлено сообщение клиенту");
            while (true) {
                logger.logMessage("server entered while loop for current session");
                inputLine = in.readUTF();

                Request request = NetworkController.parseQueryString(inputLine);
                Response response = NetworkController.prepareResponse(request);

                out.writeUTF(response.getMessage());
                logger.logMessage("responded to client " + clientSocket.toString());

                if (response.isClosureStatus()){
                    closeSession();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void closeSession() throws IOException {
        logger.logMessage("closing session @" + this.hashCode());
        in.close();
        out.close();
        clientSocket.close();
        // todo: a way to delete session upon completion
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
}
