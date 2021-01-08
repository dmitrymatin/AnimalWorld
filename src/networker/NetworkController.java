package networker;

import app.GeneralController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.CompositeKey;
import model.Food;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class NetworkController {
    public static Request parseQueryString(String queryString) {
        Request request = null;

        // query format ::= <command>?[<arg1>][&<arg2>]...[&<argN>]
        String command;
        String argsString;

        String[] queryParts = queryString.split("\\?");
        String[] args = null;
        if (queryParts.length > 0) {
            command = queryParts[0];
            if (queryParts.length > 1) {
                argsString = queryParts[1];
                args = argsString.split("&");
            }
            request = new Request(command, args);
        }
        return request;
    }

    public static Response prepareResponse(Request request) throws IOException {
        ArrayList<String> args = request.getArgs();
        switch (request.getCommand()) {
            case "get":
                // getting
                if (args.size() > 0) {
                    switch (args.get(0)) {
                        case "all":
                            Map<CompositeKey, Food> allFoods = GeneralController.getAllFoods();
//                            String responseString = "";
//                            for (CompositeKey compositeKey : allFoods.keySet()){
//                                responseString += compositeKey.foodType + "&" + compositeKey.key + "&" + allFoods.get(compositeKey).getInfo();
//                            }

                            ObjectMapper objectMapper = new ObjectMapper();
                            String jsonString;
                            try {
                                jsonString = objectMapper.writeValueAsString(allFoods);
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
                    try {
                        GeneralController.createFood(args.get(0), args.get(1), args.get(2));
                        return new Response(false, "еда успешно создана");
                    } catch (IllegalArgumentException ex) {
                        return new Response(false, "еда не была создана: переданы неверные параметры");
                    }
                }
                break;
            case "feed":
                // feeding
                break;

            case "stp":
                return new Response(true, "сессия завершена");
            default:
                return new Response(false, "несуществующая операция");
        }
        return new Response(false, "");
    }
}
