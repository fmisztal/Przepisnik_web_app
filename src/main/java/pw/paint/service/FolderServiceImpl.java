package pw.paint.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import pw.paint.DTOs.mappers.RecipeMapper;
import pw.paint.DTOs.model.ShortRecipeDto;
import pw.paint.exception.*;
import pw.paint.model.Folder;
import pw.paint.model.Recipe;
import pw.paint.model.User;
import pw.paint.repository.RecipeRepository;
import pw.paint.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService {
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    @Override
    public List<String> getFoldersNames(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty())
            throw new UserNotFoundException();

        if (user.get().getFolders().isEmpty())
            throw new FolderNotFoundException();

        List<String> foldersNames = new ArrayList<>();
        for (Folder folder : user.get().getFolders()) {
            foldersNames.add(folder.getName());
        }
        return foldersNames;
    }

    @Override
    public List<ShortRecipeDto> getRecipesFromFolder(String username, String folderName) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty())
            throw new UserNotFoundException();

        if (user.get().getFolders().isEmpty())
            throw new FolderNotFoundException("User have no folders");

        List<Recipe> recipes = new ArrayList<>();
        for (Folder folder : user.get().getFolders()) {
            if (folder.getName().equals(folderName)) {
                if (folder.getRecipes() == null)
                    throw new RecipeNotFoundException();
                recipes = folder.getRecipes();
                return RecipeMapper.toShortRecipeDto(recipes);
            }
        }
        throw new FolderNotFoundException();
    }

    @Override
    public void createNewFolder(String username, String folderName) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty())
            throw new UserNotFoundException();

        Folder folder = user.get().findFolderByName(folderName);
        if (folder != null)
            throw new DataConflictException("A folder with that name already exists");

        user.get().getFolders().add(new Folder(folderName));
        userRepository.save(user.get());
    }

    @Override
    public String addRecipeToFolder(String username, String folderName, String recipeId) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty())
            throw new UserNotFoundException();

        Folder folder = user.get().findFolderByName(folderName);
        if (folder == null)
            throw new FolderNotFoundException();

        ObjectId id = new ObjectId(recipeId);
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if (recipe.isEmpty())
            throw new RecipeNotFoundException();

        if (folder.getRecipes() == null) {
            folder.setRecipes(new ArrayList<>());
            folder.getRecipes().add(recipe.get());
            userRepository.save(user.get());
            return recipeId;
        } else {
            for (Recipe recipeInFolder : folder.getRecipes()) {
                if (recipeInFolder.getId().equals(id)) {
                    throw new RecipeAlreadyInFolder();
                }
            }
        }

        folder.getRecipes().add(recipe.get());
        userRepository.save(user.get());
        return recipeId;
    }

    @Override
    public String deleteFolder(String username, String folderName) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty())
            throw new UserNotFoundException();

        Folder folder = user.get().findFolderByName(folderName);
        if (folder == null)
            throw new FolderNotFoundException();

        user.get().getFolders().remove(folder);
        userRepository.save(user.get());
        return "Usunięto folder";
    }

    @Override
    public String deleteRecipeFromFolder(String username, String folderName, String recipeId) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty())
            throw new UserNotFoundException();

        Folder folder = user.get().findFolderByName(folderName);
        if (folder == null)
            throw new FolderNotFoundException();

        ObjectId id = new ObjectId(recipeId);
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if (recipe.isEmpty())
            throw new RecipeNotFoundException();

        if (folder.getRecipes() == null) {
            throw new RecipeNotFoundException();
        } else {
            for (Recipe recipeInFolder : folder.getRecipes()) {
                if (recipeInFolder.getId().equals(id)) {
                    folder.getRecipes().remove(recipeInFolder);
                    userRepository.save(user.get());
                    return "success";
                }
            }
        }

        throw new RecipeNotFoundException();
    }
}
