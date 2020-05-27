package guru.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.services.RecipeService;

/**
 * Index controller.
 */
@Controller
public class IndexController {

    private RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index"})
    public String indexPage(Model model) {
        model.addAttribute("recipes", recipeService.getAllRecipes());
        return "index";
    }

}
