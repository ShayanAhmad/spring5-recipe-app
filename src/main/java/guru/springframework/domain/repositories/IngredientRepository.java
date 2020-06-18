package guru.springframework.domain.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.domain.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
    List<Ingredient> findByRecipe_Id(Long recipeId);
}
