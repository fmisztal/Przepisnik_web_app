package pw.paint.exception;

public class RecipeAlreadyInFolder extends RuntimeException{
    public RecipeAlreadyInFolder() {
        super("Recipe Already In Folder");
    }
}
