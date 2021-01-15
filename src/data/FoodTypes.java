package data;

import java.util.HashMap;
import java.util.Map;

public enum FoodTypes {
    Predator("Хищник"),
    Herbivore("Травоядное"),
    Grass("Трава");

    private final String label;

    private static final Map<Integer, String> labels = new HashMap<>();

    static {
        for (FoodTypes e : values()) {
            labels.put(e.ordinal(), e.label);
        }
    }

    FoodTypes(String label) {
        this.label = label;
    }

    public static FoodTypes parseFoodType(int foodTypeOrdinal) throws IllegalArgumentException {
        if (foodTypeOrdinal >=0 && foodTypeOrdinal < FoodTypes.values().length) {
            return FoodTypes.values()[foodTypeOrdinal];
        } else {
            throw new IllegalArgumentException("food type was out of range");
        }
    }

    public static Map<Integer, String> getLocalisedNames() {
        return labels;
    }
}
