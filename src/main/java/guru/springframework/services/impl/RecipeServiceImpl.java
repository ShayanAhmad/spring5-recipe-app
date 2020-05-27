package guru.springframework.services.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import guru.springframework.domain.Recipe;
import guru.springframework.domain.repositories.RecipeRepository;
import guru.springframework.services.RecipeService;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getAllRecipes() {
        Set<Recipe> recipes = new HashSet<>();

        recipeRepository.findAll()
                .iterator()
                .forEachRemaining(recipes::add);

        return recipes;
    }
}
