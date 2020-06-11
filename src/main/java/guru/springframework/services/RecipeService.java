package guru.springframework.services;

import java.util.Set;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;

public interface RecipeService {
    Set<Recipe> getAllRecipes();

    Recipe findById(Long ID);

    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);
}
