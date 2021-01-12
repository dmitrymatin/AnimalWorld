package app;

import data.FoodTypes;
import data.StorageManager;
import model.*;

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

    public static void startServer(String portString) {
        try {
            if (portString.isBlank()) {
                throw new IllegalArgumentException("порт не должен быть пустой");
            }

            portString = portString.trim();
            int port;
            try {
                port = Integer.parseInt(portString);
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("введен неверный порт");
            }

            server.launch(port, logger);
            logger.logMessage("Сервер успешно запущен " + LocalDateTime.now());
        } catch (Exception ex) {
            logger.logMessage("Произошла ошибка при запуске сервера: " + ex.getMessage()); // todo: некорректные данные на форму, DONE
        }
    }

    public static void stopServer() {
        server.stop();
        logger.logMessage("сервер остановлен " + LocalDateTime.now());
    }

    public static void persistData() {
        logger.logMessage("сохранение данных ...");
        storageManager.save();
    }

    public static String createFood(String foodTypeString, String name, String mass) {
        String creationStatus;
        try {
            int foodTypeInt = Integer.parseInt(foodTypeString);
            float massFloat = Float.parseFloat(mass);
            FoodTypes foodType = FoodTypes.parseFoodType(foodTypeInt);

            Food food = null;

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
            creationStatus = "еда успешно создана";
        } catch (Exception ex) {
            creationStatus = "еда не была создана: переданы неверные параметры";
        }
        return creationStatus;
    }

    public static String feed(String feedFoodTypeString, String animalIdString, String preyFoodTypeString, String foodIdString) {
        String feedStatus = "не удалось покормить животное: ";
        int feedFoodTypeInt;
        int animalId;
        int preyFoodTypeInt;
        int foodId;
        try {
            feedFoodTypeInt = Integer.parseInt(feedFoodTypeString);
            animalId = Integer.parseInt(animalIdString);
            preyFoodTypeInt = Integer.parseInt(preyFoodTypeString);
            foodId = Integer.parseInt(foodIdString);
        } catch (NumberFormatException e) {
            feedStatus += "переданы неверные параметры";
            return feedStatus;
        }

        FoodTypes feedFoodType = FoodTypes.parseFoodType(feedFoodTypeInt);
        FoodTypes preyFoodType = FoodTypes.parseFoodType(preyFoodTypeInt);

        Animal animalToFeed = storageManager.getAnimals().get(animalId);
        Food prey = storageManager.getAll().get(foodId);

        if (animalToFeed == null) {
            feedStatus += "не найдено животное для кормления";
            return feedStatus;
        }

        try {
            boolean preyEaten = animalToFeed.seeFood(prey);
            if (preyEaten)
                feedStatus = "животное успешно покормлено";
            else
                feedStatus = "животное не съело добычу";
        } catch (IllegalStateException | IllegalArgumentException e) {
            feedStatus += e.getMessage();
        }

        return feedStatus;
    }

    public static Map<Integer, Predator> getPredators() {
        return storageManager.getPredators();
    }

    public static Map<Integer, Herbivore> getHerbivores() {
        return storageManager.getHerbivores();
    }

    public static Map<Integer, Grass> getGrasses() {
        return storageManager.getGrasses();
    }

    public static Map<Integer, Food> getAllFoods() {
        return storageManager.getAll();
    }

    public static Map<Integer, Animal> getAnimals() {
        return storageManager.getAnimals();
    }

    public static Map<Integer, String> getFoodTypes() {
        return FoodTypes.getLocalisedNames();
    }
}
