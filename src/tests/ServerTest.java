package tests;

import app.Logger;
import app.MultiThreadedServer;
import data.StorageManager;
import model.Grass;
import model.Herbivore;
import model.Predator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;

public class ServerTest {
    private static MultiThreadedServer server = MultiThreadedServer.getInstance();
    private static Logger logger = null;

    @BeforeAll
    static void beforeAll() {
        logger = new Logger() {
            @Override
            public void logMessage(String message) {
                System.out.println(message);
            }

            @Override
            public void clearLog() {
            }
        };
    }

    @Test
    void connecting_is_successful() {
        try {
            server.launch(1234, logger);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            server.stop();
        }
    }

    @Test
    void sending_request_is_successful() {
        try {
            server.launch(1234, logger);

            Socket socket = new Socket("localhost", 1234);
            logger.logMessage("CLIENT using socket @" + socket.hashCode() + " " + socket.toString());

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            logger.logMessage("клиент должен получить сообщение");
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            logger.logMessage("доступно для чтения: " + dis.available());
            var res = dis.readUTF();
            logger.logMessage("доступно для чтения: " + dis.available());
            //var r = in.readLine();
            logger.logMessage("сервер прислал сообщение");
            logger.logMessage(res);

            dos.writeUTF("cmd1");
            String response = dis.readUTF();
            logger.logMessage("сервер прислал сообщение");
            logger.logMessage(response);

            dos.writeUTF("cmd2");
            response = dis.readUTF();
            logger.logMessage("сервер прислал сообщение");
            logger.logMessage(response);

            dos.writeUTF("stp");
            response = dis.readUTF();
            logger.logMessage("сервер прислал сообщение");
            logger.logMessage(response);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            server.stop();
        }
    }

    @Test
    void sendingProperCommandSuccessful() {
        StorageManager storageManager = StorageManager.getInstance();
        storageManager.addFood(new Grass("клевер", .4f));
        storageManager.addFood(new Predator("лиса Алиса", 1.5f));
        storageManager.addFood(new Predator("волк Вася", 2.8f));
        storageManager.addFood(new Herbivore("заяц Петя", .8f));

        try {
            server.launch(1234, logger);

            Socket socket = new Socket("localhost", 1234);
            logger.logMessage("CLIENT using socket @" + socket.hashCode() + " " + socket.toString());

            logger.logMessage("клиент должен получить сообщение");
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            logger.logMessage("доступно для чтения: " + dis.available());
            var result = dis.readUTF();
            logger.logMessage("доступно для чтения: " + dis.available());
            logger.logMessage("сервер прислал сообщение");
            logger.logMessage(result);

            dos.writeUTF("get?all");
            String response = dis.readUTF();
            logger.logMessage("сервер прислал сообщение");
            logger.logMessage(response);

            dos.writeUTF("stp");
            response = dis.readUTF();
            logger.logMessage("сервер прислал сообщение");
            logger.logMessage(response);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            server.stop();
        }


    }
}
