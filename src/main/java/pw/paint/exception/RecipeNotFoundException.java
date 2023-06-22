package pw.paint.exception;

public class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException(String message) {
        super(message);
    }
    public RecipeNotFoundException() {
        super("Recipe not found");
    }
}
