package app;

import networker.Request;
import networker.Response;

import java.io.*;
import java.net.Socket;
import java.util.ResourceBundle;

public class Session implements Runnable {
    private final Socket clientSocket;
    private final MultiThreadedServer parent;
    private final Logger logger;
    private DataOutputStream out;
    private DataInputStream in;
    private final ResourceBundle rb;

    public Session(Socket clientSocket, MultiThreadedServer parent, Logger logger, ResourceBundle resourceBundle) {
        this.clientSocket = clientSocket;
        this.parent = parent;
        this.logger = logger;
        this.rb = resourceBundle;
        try {
            out = new DataOutputStream(this.clientSocket.getOutputStream());
            in = new DataInputStream(this.clientSocket.getInputStream());
        } catch (IOException ex) {
            this.logger.logMessage(rb.getString("ERROR_SESSION_CREATE_FAIL") + ": " + ex.getMessage());
        }
    }

    public void run() {
        try {
            out.writeUTF(rb.getString("SUCCESS_CONNECT"));
            while (true) {
                String inputLine = in.readUTF();

                Request request = Request.parseRequest(inputLine);
                Response response = GeneralController.prepareResponse(request);

                out.writeUTF(response.getMessage());
                logger.logMessage(rb.getString("RESPONSE_SENT"));

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
        logger.logMessage(rb.getString("SESSION_CLOSING") + this.hashCode());
        in.close();
        out.close();
        clientSocket.close();
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
}
