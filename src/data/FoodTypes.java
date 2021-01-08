package data;

public enum FoodTypes {
    Predator,
    Herbivore,
    Grass;

    public static FoodTypes parseFoodType(int foodTypeOrdinal) {
        if (foodTypeOrdinal < FoodTypes.values().length) {
            return FoodTypes.values()[foodTypeOrdinal];
        } else {
            throw new IllegalArgumentException("food type was out of range");
        }
    }
}
