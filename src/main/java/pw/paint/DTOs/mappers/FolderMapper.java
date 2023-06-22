package pw.paint.DTOs.mappers;

import pw.paint.DTOs.model.FolderDto;
import pw.paint.model.Folder;
import pw.paint.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class FolderMapper {
    public static FolderDto toFolderDto (Folder folder) {
        if(folder.getRecipes() == null) {
            return FolderDto.builder()
                    .name(folder.getName())
                    .build();
        }

        List<String> recipeIds = new ArrayList<>();
        for (Recipe recipe : folder.getRecipes()) {
            recipeIds.add(recipe.getId().toString());
        }

        return FolderDto.builder()
                .name(folder.getName())
                .recipes(recipeIds)
                .build();
    }
}
