package model.exceptions;

public class NegativeMassException extends IllegalArgumentException {
    public NegativeMassException(String errorMessage) {
        super(errorMessage);
    }
}
