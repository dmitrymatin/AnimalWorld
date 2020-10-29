package model.exceptions;

public class DeadAnimalException extends IllegalStateException {
    public DeadAnimalException(String errorMessage) {
        super(errorMessage);
    }
}
