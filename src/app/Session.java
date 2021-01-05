package app;

import java.io.*;
import java.net.Socket;
import java.util.Random;

public class Session implements Runnable {
    private final Socket clientSocket;
    private final Logger logger;
    private OutputStreamWriter out;
    private InputStreamReader in;

    public Session(Socket clientSocket, Logger logger) {
        this.clientSocket = clientSocket;
        this.logger = logger;
    }

    public void run() {
        try {
            out = new OutputStreamWriter(clientSocket.getOutputStream());
            in = new InputStreamReader(clientSocket.getInputStream());

            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

            logger.logMessage("server prepared in/out streams");

            String inputLine;
            //out.write("вы подключены");
            dos.writeUTF("вы подключены!");
            logger.logMessage("отправлено сообщение клиенту");
            while ((inputLine = dis.readUTF()) != null) {
                logger.logMessage("server entered while loop for current session");

                if ("stp".equals(inputLine)) {
                    logger.logMessage("disconnecting client"); // todo: EDT
                    dos.writeUTF("завершение сессии");
                    break;
                }

                switch (inputLine) {
                    case "cmd1":
                        dos.writeUTF("response to cmd1");
                        break;
                    case "cmd2":
                        dos.writeUTF("response to cmd2");
                        break;
                    default:
                        dos.writeUTF("unknown command");
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
