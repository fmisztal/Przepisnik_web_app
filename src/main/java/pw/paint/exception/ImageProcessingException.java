package pw.paint.exception;

public class ImageProcessingException extends RuntimeException {
    public ImageProcessingException(String message) {
        super(message);
    }
    public ImageProcessingException() {
        super("Problem with image processing has occurred");
    }
}
