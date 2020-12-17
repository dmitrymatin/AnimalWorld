package app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MultiThreadedServer {
    private static MultiThreadedServer uniqueInstance = null;

    private int port;
    private boolean listen = false;

    private ServerSocket serverSocket = null;
    ArrayList<ThreadedLogic> sessions = new ArrayList<>();

    public static MultiThreadedServer getUniqueInstance(){
        if (uniqueInstance == null) {
            uniqueInstance = new MultiThreadedServer();
        }

        return uniqueInstance;
    }

    private MultiThreadedServer() { }

    public void launch(int port) throws IOException {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Создан ServerSocket, port: " + port);
        } catch (IOException e) {
            throw e;
        }

        listen = true;
        try {
            while (listen) {
                Socket socket = serverSocket.accept();
                System.out.println("\nВ цикле ServerSocket ожидает входящие запросы от клиентов на порт " + port);

                ThreadedLogic threadedLogic = new ThreadedLogic(socket);
                sessions.add(threadedLogic);
                System.out.println("Создан экземпляр класса ThreadedLogic");

                Thread thread = new Thread(threadedLogic);
                thread.start();
                System.out.println("Сервер создал новый поток");
            }


        } catch (IOException e) {
            listen = false;
            throw e;
        }
    }

    public void stop() {
        try {
            for (ThreadedLogic s: sessions) {
                s.getSocket().close();
                System.out.println("Закрыт клиентский сокет на стороне сервера\n");
            }

            serverSocket.close();
            listen = false;
            System.out.println("Закрыт серверный сокет (ServerSocket)");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
