package tests;

import app.Logger;
import app.MultiThreadedServer;
import data.StorageManager;
import model.Grass;
import model.Herbivore;
import model.Predator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class ServerTest {
    private static MultiThreadedServer server = MultiThreadedServer.getInstance();
    private static StorageManager storageManager = StorageManager.getInstance();
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

        initialiseData();
        initialiseServer();
    }

    @AfterAll
    static void afterAll(){
        server.stop();
    }

    private static void initialiseData() {
        storageManager.addFood(new Grass("клевер", .4f));
        storageManager.addFood(new Predator("лиса Алиса", 1.5f));
        storageManager.addFood(new Predator("волк Вася", 2.8f));
        storageManager.addFood(new Herbivore("заяц Петя", .8f));
    }

    private static void initialiseServer() {
        try {
            server.launch(1234, logger);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void sendingRequestIsSuccessful() {
        try {
            Socket socket = new Socket("localhost", 1234);
            logger.logMessage("CLIENT using socket @" + socket.hashCode() + " " + socket.toString());

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
        }
    }

    @Test
    void sendingProperCommandSuccessful() {
        try {
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

            String response;
            dos.writeUTF("get?all");
            logger.logMessage("запрос на получение всей еды");
            response = dis.readUTF();
            logger.logMessage("сервер прислал сообщение");
            logger.logMessage(response);

            dos.writeUTF("get?pdt");
            logger.logMessage("запрос на получение хищников");
            response = dis.readUTF();
            logger.logMessage("сервер прислал сообщение");
            logger.logMessage(response);

            dos.writeUTF("get?hbv");
            logger.logMessage("запрос на получение травоядных");
            response = dis.readUTF();
            logger.logMessage("сервер прислал сообщение");
            logger.logMessage(response);

            dos.writeUTF("get?grs");
            logger.logMessage("запрос на получение травы");
            response = dis.readUTF();
            logger.logMessage("сервер прислал сообщение");
            logger.logMessage(response);

            dos.writeUTF("crt?0&медведь Миша&20.5");
            logger.logMessage("запрос на создание хищника (0)");
            response = dis.readUTF();
            logger.logMessage("сервер прислал сообщение");
            logger.logMessage(response);

            dos.writeUTF("feed?0&2&1&1");
            logger.logMessage("запрос на кормление хищника (0) с id=3 травоядным(1) с id=1 ");
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
        }
    }
}
