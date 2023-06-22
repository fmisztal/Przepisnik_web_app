package pw.paint.exception;

public class DataConflictException extends RuntimeException {
    public DataConflictException(String message) {
        super(message);
    }
    public DataConflictException() {
        super("Such an object already exists");
    }
}
