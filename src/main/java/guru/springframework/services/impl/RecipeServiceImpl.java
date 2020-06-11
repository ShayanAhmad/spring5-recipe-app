package guru.springframework.services.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import guru.springframework.domain.Recipe;
import guru.springframework.domain.repositories.RecipeRepository;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

    @Override
    public Recipe findById(Long ID) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ID);

        if (!recipeOptional.isPresent()) {
            throw new RuntimeException("Recipe does not exist!");
        }

        return recipeOptional.get();
    }
}