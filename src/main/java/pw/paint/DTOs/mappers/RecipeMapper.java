package pw.paint.DTOs.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pw.paint.DTOs.model.RecipeDto;
import pw.paint.DTOs.model.ShortRecipeDto;
import pw.paint.DTOs.requests.NewRecipeRequest;
import pw.paint.model.Recipe;
import pw.paint.model.Tag;
import pw.paint.model.User;
import pw.paint.repository.TagRepository;
import pw.paint.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RecipeMapper {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TagRepository tagRepository;

    public static RecipeDto toRecipeDto(Recipe recipe) {
        return RecipeDto.builder()
                .id(recipe.getId().toString())
                .name(recipe.getName())
                .ingredients(recipe.getIngredients())
                .steps(recipe.getSteps())
                .status(recipe.getStatus())
                .timeMinutes(recipe.getTimeMinutes())
                .author(recipe.getAuthor().getUsername())
                .tags(getTags(recipe.getTags()))
                .image(recipe.getImage())
                .build();
    }

    private static List<String> getTags(List<Tag> tags) {
        List<String> tagNames = new ArrayList<>();
        for (Tag tag : tags) {
            tagNames.add(tag.getName());
        }
        return tagNames;
    }

    public static ShortRecipeDto toShortRecipeDto(Recipe recipe) {
        return ShortRecipeDto.builder()
                .id(recipe.getId().toString())
                .name(recipe.getName())
                .author(recipe.getAuthor().getUsername())
                .tags(getTags(recipe.getTags()))
                .image(recipe.getImage())
                .build();
    }

    public static List<ShortRecipeDto> toShortRecipeDto(List<Recipe> recipes) {
        List<ShortRecipeDto> shortRecipesDto = new ArrayList<>();
        for (Recipe recipe : recipes) {
            shortRecipesDto.add(toShortRecipeDto(recipe));
        }
        return shortRecipesDto;
    }

    public Recipe toModelRecipeObject (NewRecipeRequest request){
        Optional<User> author = userRepository.findByUsername(request.getAuthor());

        if (author.isEmpty()) {
            return null;
        }

        List<Tag> tags = new ArrayList<>();
        Optional<Tag> tag;
        for (String tagName : request.getTags()) {
            tag = tagRepository.findByName(tagName);
            tag.ifPresent(tags::add);
        }

        return Recipe.builder()
                .name(request.getName())
                .ingredients(request.getIngredients())
                .steps(request.getSteps())
                .status(request.getStatus())
                .timeMinutes(request.getTimeMinutes())
                .author(author.get())
                .tags(tags)
                .build();
    }
}
