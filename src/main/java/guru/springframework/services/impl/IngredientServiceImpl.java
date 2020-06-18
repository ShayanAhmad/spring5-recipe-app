package guru.springframework.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.repositories.IngredientRepository;
import guru.springframework.services.IngredientService;

/**
 * Created by yaguar.
 */
@Service
public class IngredientServiceImpl implements IngredientService {

    private IngredientRepository ingredientRepository;
    private IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Override
    public Set<IngredientCommand> getAllIngredientsOfRecipe(Long recipeId) {
        if (Objects.isNull(recipeId)) {
            throw new RuntimeException("Recipe ID cannot be null!");
        }

        Set<IngredientCommand> resultSet = new HashSet<>();
        List<Ingredient> ingredientList = ingredientRepository.findByRecipe_Id(recipeId);

        ingredientList
                .forEach(ingredient -> resultSet.add(ingredientToIngredientCommand.convert(ingredient)));

        return resultSet;
    }

    @Override
    public IngredientCommand getIngredientById(Long ingredientId) {

        Optional<Ingredient> dbIngredient = ingredientRepository.findById(ingredientId);
        if (!dbIngredient.isPresent()) {
            throw new RuntimeException("The ingredient does not exist in db.");
        }

        return ingredientToIngredientCommand.convert(dbIngredient.get());
    }
}
