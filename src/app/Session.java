package app;

import networker.NetworkController;
import networker.Request;

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
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream in = new DataInputStream(clientSocket.getInputStream());

            logger.logMessage("server prepared in/out streams");

            String inputLine;
            //out.write("вы подключены");
            out.writeUTF("вы подключены!");
            logger.logMessage("отправлено сообщение клиенту");
            while ((inputLine = in.readUTF()) != null) {
                logger.logMessage("server entered while loop for current session");

                Request request = NetworkController.parseQueryString(inputLine);
                NetworkController.prepareResponse(request);

                if ("stp".equals(inputLine)) {
                    logger.logMessage("disconnecting client"); // todo: EDT
                    out.writeUTF("завершение сессии");
                    break;
                }

                switch (inputLine) {
                    case "cmd1":
                        out.writeUTF("response to cmd1");
                        break;
                    case "cmd2":
                        out.writeUTF("response to cmd2");
                        break;
                    default:
                        out.writeUTF("unknown command");
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
        logger.logMessage("closing session @" + this.hashCode());
        in.close();
        out.close();
        clientSocket.close();
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
}
