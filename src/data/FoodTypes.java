package data;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public enum FoodTypes {
    Predator("Хищник"),
    Herbivore("Травоядное"),
    Grass("Трава");

    private final String label;
    private static final Map<Integer, String> labels = new HashMap<>();
    private static final ResourceBundle rb;

    static {
        for (FoodTypes e : values()) {
            labels.put(e.ordinal(), e.label);
        }
        rb = ResourceBundle.getBundle("ResourceBundle", Locale.getDefault());
    }

    FoodTypes(String label) {
        this.label = label;
    }

    public static FoodTypes parseFoodType(int foodTypeOrdinal) throws IllegalArgumentException {
        if (foodTypeOrdinal >=0 && foodTypeOrdinal < FoodTypes.values().length) {
            return FoodTypes.values()[foodTypeOrdinal];
        } else {
            throw new IllegalArgumentException(rb.getString("FOODTYPE_OUT_RANGE"));
        }
    }

    public static Map<Integer, String> getLocalisedNames() {
        return labels;
    }
}
