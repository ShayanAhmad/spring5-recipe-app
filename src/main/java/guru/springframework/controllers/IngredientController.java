package guru.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import guru.springframework.services.IngredientService;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by yaguar.
 */
@Slf4j
@Controller
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/recipe/{id}/ingredients")
    public String getAllIngredientsOfRecipe(@PathVariable(value = "id") Long id, Model model) {

        model.addAttribute("ingredients", ingredientService.getAllIngredientsOfRecipe(id));
        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String getSpecificIngredient(@PathVariable(value = "recipeId") Long recipeId,
                                        @PathVariable(value = "ingredientId") Long ingredientId,
                                        Model model) {
        ingredientService.getIngredientById()
    }

}
