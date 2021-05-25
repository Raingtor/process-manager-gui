package sample.exception;

public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(String className) {
        super(className + " not found.");
    }
}