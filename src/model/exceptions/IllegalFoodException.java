package model.exceptions;

public class IllegalFoodException extends IllegalArgumentException {
    public IllegalFoodException(String errorMessage) {
        super(errorMessage);
    }
}
