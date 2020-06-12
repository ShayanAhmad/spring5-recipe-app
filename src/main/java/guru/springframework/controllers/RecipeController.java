package guru.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.RecipeService;

/**
 * Index controller.
 */
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/recipe/{id}/show")
    public String showById(@PathVariable(value = "id") Long recipeId, Model model) {
        model.addAttribute("recipe", recipeService.findById(recipeId));
        return "recipe/show";
    }

    @RequestMapping("/recipe/new")
    public String saveNewRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeform";
    }

    @PostMapping
    @RequestMapping("recipe")
    public String saveOrUpdateRecipe(@ModelAttribute RecipeCommand recipeCommand) {
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);
        return "redirect:/recipe/" + savedRecipeCommand.getId() + "/show";
    }

    @RequestMapping("/recipe/{id}/update")
    public String updateRecipeById(@PathVariable(value = "id") Long id, Model model) {
        RecipeCommand recipeCommand = recipeService.findRecipeCommandById(id);
        model.addAttribute("recipe", recipeCommand);
        return "recipe/recipeform";
    }

    @RequestMapping("/recipe/{id}/delete")
    public String deleteRecipeById(@PathVariable(value = "id") Long id) {
        recipeService.deleteById(id);
        return "redirect:/";
    }
}
