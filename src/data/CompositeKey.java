package data;

import com.fasterxml.jackson.annotation.JsonValue;

public class CompositeKey {
    public final FoodTypes foodType;
    public final int key;

    public CompositeKey(final FoodTypes foodType, final int key) {
        this.foodType = foodType;
        this.key = key;
    }

    @Override
    @JsonValue
    public String toString() {
        return "<" + foodType.ordinal() + "," + key + ">";
    }

    public boolean equals (final Object O) {
        if (!(O instanceof CompositeKey)) return false;
        if (((CompositeKey) O).foodType != foodType) return false;
        if (((CompositeKey) O).key != key) return false;
        return true;
    }

    public int hashCode() {
        return (foodType.ordinal() << 16) + key;
    }
}
