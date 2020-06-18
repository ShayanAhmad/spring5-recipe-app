package guru.springframework.services;

import java.util.Set;

import guru.springframework.commands.IngredientCommand;

/**
 * Created by yaguar.
 */
public interface IngredientService {
    Set<IngredientCommand> getAllIngredientsOfRecipe(Long recipeId);

    IngredientCommand getIngredientById(Long ingredientId);
}
