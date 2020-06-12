package guru.springframework.services.impl;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.repositories.RecipeRepository;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeToRecipeCommand recipeToRecipeCommandConverter;
    private final RecipeCommandToRecipe recipeCommandToRecipeConverter;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeToRecipeCommand recipeToRecipeCommandConverter, RecipeCommandToRecipe recipeCommandToRecipeConverter) {
        this.recipeRepository = recipeRepository;
        this.recipeToRecipeCommandConverter = recipeToRecipeCommandConverter;
        this.recipeCommandToRecipeConverter = recipeCommandToRecipeConverter;
    }

    @Override
    public Set<Recipe> getAllRecipes() {
        Set<Recipe> recipes = new HashSet<>();

        recipeRepository.findAll()
                .iterator()
                .forEachRemaining(recipes::add);

        return recipes;
    }

    @Override
    public Recipe findById(Long ID) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ID);

        if (!recipeOptional.isPresent()) {
            throw new RuntimeException("Recipe does not exist!");
        }

        return recipeOptional.get();
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe detachedRecipe = recipeCommandToRecipeConverter.convert(recipeCommand);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Recipe has been saved!");

        return recipeToRecipeCommandConverter.convert(savedRecipe);
    }

    @Override
    @Transactional
    public RecipeCommand findRecipeCommandById(Long id) {
        if (Objects.isNull(id)) {
            throw new RuntimeException("ID does not exist!");
        }

        Recipe recipe = findById(id);
        return recipeToRecipeCommandConverter.convert(recipe);
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }
}
