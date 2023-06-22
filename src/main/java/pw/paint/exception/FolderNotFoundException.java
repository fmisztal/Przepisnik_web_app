package pw.paint.exception;

public class FolderNotFoundException extends RuntimeException {
    public FolderNotFoundException(String message) {
        super(message);
    }
    public FolderNotFoundException() {
        super("Folder not found");
    }
}
