package data;

public class CompositeKey {
    public final Foods foodType;
    public final int key;

    public CompositeKey(final Foods foodType, final int key) {
        this.foodType = foodType;
        this.key = key;
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