package networker;

import app.GeneralController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

public class NetworkController {
    public static Response prepareResponse(Request request) {
        ArrayList<String> args = request.getArgs();
        switch (request.getCommand()) {
            case "get":
                // getting
                if (args.size() > 0) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    Object value = null;
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
                            return new Response(false, "Ошибка: Неверный аргумент " + args.get(0) + " команды " + request.getCommand());
                    }
                    try {
                        jsonString = objectMapper.writeValueAsString(value);
                        return new Response(false, jsonString);
                    } catch (JsonProcessingException e) {
                        break;
                    }
                }
                break;
            case "crt":
                // creating
                if (args.size() > 2) {
                    String status = GeneralController.createFood(args.get(0), args.get(1), args.get(2));
                    return new Response(false, status);
                }
                break;
            case "feed":
                // feeding
                if (args.size() > 3) {
                    String feedStatus = GeneralController.feed(args.get(0), args.get(1), args.get(2), args.get(3));
                    return new Response(false, feedStatus);
                }
                break;

            case "stp":
                return new Response(true, "сессия завершена");
            default:
                return new Response(false, "несуществующая операция");
        }
        return new Response(false, "");
    }
}
