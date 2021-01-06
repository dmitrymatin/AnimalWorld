package app;

import data.CompositeKey;
import data.Foods;
import data.StorageManager;
import model.Food;
import model.Grass;
import model.Herbivore;
import model.Predator;

import java.time.LocalDateTime;
import java.util.Map;

public class GeneralController {
    private static final StorageManager storageManager = StorageManager.getInstance();
    private static MultiThreadedServer server = null;
    private static ServerForm serverForm = null;
    private static ServerFormListener serverFormListener = null;
    private static Logger logger = null;

    public static void startApp() {
        String startStatus = "";
        try {
            storageManager.load();
            startStatus += "содержимое хранилища загружено";
        } catch (Exception ex) {
            startStatus += "хранилище пусто";
        }

        server = MultiThreadedServer.getInstance();
        serverForm = new ServerForm("Сервер");
        logger = new FormLogger(serverForm.getTextArea());
        serverFormListener = new ServerFormListener(serverForm, logger);
        logger.logMessage(startStatus);
    }

    public static void startServer(int port) {
        try {
            logger.logMessage("-------");
            server.launch(port, logger);
            logger.logMessage("-------");
        } catch (Exception e) {
            logger.logMessage(e.getMessage());
            server.stop();
        }
    }

    public static void stopServer() {
        server.stop();
        logger.logMessage("сервер остановлен " + LocalDateTime.now());
    }

    public static Map<CompositeKey, Food> getAllFoods() {
        return storageManager.getAll();
    }

    public static void persistData() {
        logger.logMessage("сохранение данных ...");
        storageManager.save();
    }

    public static void createFood(String foodTypeString, String name, String mass) throws IllegalArgumentException {
        try {
            int foodTypeInt = Integer.parseInt(foodTypeString);
            float massFloat = Float.parseFloat(mass);
            Foods foodType;
            Food food = null;

            if (foodTypeInt < Foods.values().length) {
                foodType = Foods.values()[foodTypeInt];
            } else {
                throw new IllegalArgumentException("food type was out of range");
            }

            switch (foodType) {
                case Predator:
                    food = new Predator(name, massFloat);
                    break;
                case Herbivore:
                    food = new Herbivore(name, massFloat);
                    break;
                case Grass:
                    food = new Grass(name, massFloat);
                    break;
            }
            storageManager.addFood(food);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Could not parse number from string", ex);
        }
    }
}
