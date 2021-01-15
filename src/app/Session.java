package app;

import networker.Request;
import networker.Response;

import java.io.*;
import java.net.Socket;

public class Session implements Runnable {
    private final Socket clientSocket;
    private final MultiThreadedServer parent;
    private final Logger logger;
    private DataOutputStream out;
    private DataInputStream in;

    public Session(Socket clientSocket, MultiThreadedServer parent, Logger logger) {
        this.clientSocket = clientSocket;
        this.parent = parent;
        this.logger = logger;
        try {
            out = new DataOutputStream(this.clientSocket.getOutputStream());
            in = new DataInputStream(this.clientSocket.getInputStream());
            this.logger.logMessage("server prepared in/out streams");
        } catch (IOException ex) {
            this.logger.logMessage("Ошибка при создании сессии: " + ex.getMessage());
        }
    }

    public void run() {
        try {
            out.writeUTF("вы подключены!");
            logger.logMessage("отправлено сообщение клиенту");
            while (true) {
                logger.logMessage("server entered while loop for current session");
                String inputLine = in.readUTF();

                Request request = Request.parseRequest(inputLine);
                Response response = GeneralController.prepareResponse(request);

                out.writeUTF(response.getMessage());
                logger.logMessage("responded to client " + clientSocket.toString());

                if (response.isClosureStatus()) {
                    parent.removeSession(this);
                    break;
                }
            }
        } catch (IOException e) { // todo: handle SocketException that states "Connection reset"
            e.printStackTrace();
        }
    }

    void closeSession() throws IOException {
        logger.logMessage("closing session @" + this.hashCode());
        in.close();
        out.close();
        clientSocket.close();
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
}
