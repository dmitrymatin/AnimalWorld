package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class Session implements Runnable {
    private final Socket clientSocket;
    private final Logger logger;
    private PrintWriter out;
    private BufferedReader in;
    private int data = 50;

    public Session(Socket clientSocket, Logger logger) {
        this.clientSocket = clientSocket;
        this.logger = logger;
    }

    public void run() {
        try {
            //heavyComputation();

            // todo: для сессий добавить беск. цикл
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if ("stp".equals(inputLine)){
                    logger.logMessage("disconnecting client"); // todo: EDT
                }

                switch (inputLine){
                    case "cmd1":
                        out.print("response to cmd1");
                        break;
                    case "cmd2":
                        out.print("response to cmd2");
                        break;
                    default:
                        out.print("unknown command");
                        break;
                }

                // ~ NetListener
                // распарсить запрос, general controller.dosmth(...)

                //data = new Random().nextInt();
                //clientSocket.getOutputStream().write(data);
                //System.out.println("В OutputStream клиентского сокета записано: " + data);
                logger.logMessage("responded to client " + clientSocket.toString());
            }

            in.close();
            out.close();
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    private void heavyComputation() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Поток " + Thread.currentThread().getName() + " закончил выполнение сложных вычислений");
    }
}
