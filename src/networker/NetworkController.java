package networker;

import app.GeneralController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.CompositeKey;
import model.Food;
import model.Grass;
import model.Herbivore;
import model.Predator;

import java.util.ArrayList;
import java.util.Map;

public class NetworkController {
    public static Response prepareResponse(Request request) {
        ArrayList<String> args = request.getArgs();
        switch (request.getCommand()) {
            case "get":
                // getting
                if (args.size() > 0) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    String jsonString;
                    switch (args.get(0)) {
                        case "all":
                            Map<CompositeKey, Food> allFoods = GeneralController.getAllFoods();
//                            String responseString = "";
//                            for (CompositeKey compositeKey : allFoods.keySet()){
//                                responseString += compositeKey.foodType + "&" + compositeKey.key + "&" + allFoods.get(compositeKey).getInfo();
//                            }

                            try {
                                jsonString = objectMapper.writeValueAsString(allFoods);
                                return new Response(false, jsonString);
                            } catch (JsonProcessingException e) {
                                break;
                            }
                        case "pdt":
                            Map<Integer, Predator> predators = GeneralController.getPredators();

                            try {
                                jsonString = objectMapper.writeValueAsString(predators);
                                return new Response(false, jsonString);
                            } catch (JsonProcessingException e) {
                                break;
                            }
                        case "hbv":
                            Map<Integer, Herbivore> herbivores = GeneralController.getHerbivores();
                            try {
                                jsonString = objectMapper.writeValueAsString(herbivores);
                                return new Response(false, jsonString);
                            } catch (JsonProcessingException e) {
                                break;
                            }
                        case "grs":
                            Map<Integer, Grass> grasses = GeneralController.getGrasses();
                            try {
                                jsonString = objectMapper.writeValueAsString(grasses);
                                return new Response(false, jsonString);
                            } catch (JsonProcessingException e) {
                                break;
                            }
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
