package app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.FoodTypes;
import data.StorageManager;
import model.*;
import networker.Request;
import networker.Response;

import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.util.*;

public class GeneralController {
    private static MultiThreadedServer server = null;
    private static ServerForm serverForm = null;
    private static Logger logger = null;
    private static ResourceBundle rb = ResourceBundle.getBundle("ResourceBundle", new Locale("ru"));
    private static Properties properties = new Properties();
    private final static String PROPS_FILENAME = "settings.properties";
    private static final StorageManager storageManager = StorageManager.getInstance();

    public static void startApp(ResourceBundle rb) {
        GeneralController.rb = rb;
        String startStatus = "";
        try {
            GeneralController.properties.load(new FileInputStream(PROPS_FILENAME));
            storageManager.load();
            startStatus += rb.getString("STORAGE_LOADED");
        } catch (Exception ex) {
            startStatus += rb.getString("STORAGE_EMPTY");
        }

        server = MultiThreadedServer.getInstance(rb);
        serverForm = new ServerForm(rb.getString("FORM_TITLE"), rb);
        logger = new FormLogger(serverForm.getTextArea());
        new ServerFormListener(serverForm, logger);
        logger.logMessage(startStatus);
    }

    public static void startServer(String portString) {
        try {
            if (portString.isBlank()) {
                throw new IllegalArgumentException(rb.getString("ERROR_PORT_EMPTY"));
            }

            int port = Integer.parseInt(portString.trim());
            if (!(port >= 0 && port <= 65535))
                throw new IllegalArgumentException(rb.getString("ERROR_PORT_INVALID"));
            server.launch(port, logger);
            logger.logMessage(rb.getString("SERVER_START") + ": " + LocalDateTime.now());

            serverForm.onStartServer();
        } catch (Exception ex) {
            logger.logMessage(rb.getString("ERROR_SERVER_NOT_START") + ": " + ex.getMessage());
        }
    }

    public static void stopServer() {
        server.stop();
        logger.logMessage(rb.getString("SERVER_STOP") + ": " + LocalDateTime.now());
        serverForm.onStopServer();
    }

    public static void persistData() {
        logger.logMessage(rb.getString("DATA_SAVE") + "...");
        storageManager.save();
    }

    public static Response prepareResponse(Request request) {
        ArrayList<String> args = request.getArgs();
        switch (request.getCommand()) {
            case "get":
                // getting
                if (args.size() >= 1) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    Object value;
                    String jsonString;
                    switch (args.get(0)) {
                        case "all":
                            value = GeneralController.getAllFoods();
                            break;
                        case "anim":
                            value = GeneralController.getAnimals();
                            break;
                        case "pdt":
                            value = GeneralController.getPredators();
                            break;
                        case "hbv":
                            value = GeneralController.getHerbivores();
                            break;
                        case "grs":
                            value = GeneralController.getGrasses();
                            break;
                        case "foodTypes":
                            value = GeneralController.getFoodTypes();
                            break;
                        default:
                            return new Response(false, true,
                                    rb.getString("ERROR_INVALID_ARG") + " " + args.get(0) + " " + rb.getString("CURRENT_COMMAND") + ": " + request.getCommand());
                    }
                    try {
                        jsonString = objectMapper.writeValueAsString(value);
                        return new Response(false, false, jsonString);
                    } catch (JsonProcessingException e) {
                        break;
                    }
                }
                break;
            case "crt":
                // creating
                if (args.size() >= 3) {
                    String status = GeneralController.createFood(args.get(0), args.get(1), args.get(2));
                    return new Response(false, false, status);
                }
                break;
            case "feed":
                // feeding
                if (args.size() >= 2) {
                    String feedStatus = GeneralController.feed(args.get(0), args.get(1));
                    return new Response(false, false, feedStatus);
                }
                break;

            case "stp":
                return new Response(true, false, rb.getString("SESSION_STOP"));
            default:
                return new Response(false, true, rb.getString("INVALID_OPERATION"));
        }
        return new Response(false, true, rb.getString("INVALID_COMMAND"));
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
            creationStatus = rb.getString("FOOD_CREATED");
        } catch (Exception ex) {
            creationStatus = rb.getString("FOOD_NOT_CREATED");
        }
        return creationStatus;
    }

    public static String feed(String animalIdString, String foodIdString) {
        String feedStatus = rb.getString("ERROR_FEED_FAIL_GENERAL");
        int animalId;
        int foodId;
        try {
            animalId = Integer.parseInt(animalIdString);
            foodId = Integer.parseInt(foodIdString);
        } catch (NumberFormatException e) {
            feedStatus += ": " + rb.getString("ERROR_FEED_INVALID_ARGS");
            return feedStatus;
        }

        Animal animalToFeed = storageManager.getAnimals().get(animalId);
        Food prey = storageManager.getAll().get(foodId);

        if (animalToFeed == null) {
            feedStatus += rb.getString("ERROR_ANIMAL_TO_FEED_NOT_FOUND");
            return feedStatus;
        }

        try {
            boolean preyEaten = animalToFeed.seeFood(prey);
            if (preyEaten)
                feedStatus = rb.getString("ANIMAL_FEED_SUCCESS");
            else
                feedStatus = rb.getString("ANIMAL_DID_NOT_EAT");
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

    public static Properties getProperties(){
        return properties;
    }
}
