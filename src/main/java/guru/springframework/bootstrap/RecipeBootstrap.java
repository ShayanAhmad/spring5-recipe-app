package guru.springframework.bootstrap;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import guru.springframework.domain.Category;
import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Notes;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.domain.repositories.CategoryRepository;
import guru.springframework.domain.repositories.RecipeRepository;
import guru.springframework.domain.repositories.UnitOfMeasureRepository;

@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private UnitOfMeasureRepository unitOfMeasureRepository;
    private CategoryRepository categoryRepository;
    private RecipeRepository recipeRepository;

    public RecipeBootstrap(UnitOfMeasureRepository unitOfMeasureRepository, CategoryRepository categoryRepository, RecipeRepository recipeRepository) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadData();
    }

    private void loadData() {
        Set<Recipe> recipes = new HashSet<>();
        Recipe perfectGuacamole = preparePerfecGuacamole();
        recipes.add(perfectGuacamole);

        recipeRepository.saveAll(recipes);
    }

    private Recipe preparePerfecGuacamole() {
        Recipe perfectGuacamole = new Recipe();
        Set<Ingredient> ingredientSet = new HashSet<>();

        Category americanCategory = getAndCheckCategoryFromRepository("American");
        Category mexicanCategory = getAndCheckCategoryFromRepository("Mexican");

        UnitOfMeasure teaspoon = getAndCheckUnitOfMeasureFromRepository("teaspoon");
        UnitOfMeasure tablespoon = getAndCheckUnitOfMeasureFromRepository("tablespoon");
        UnitOfMeasure cup = getAndCheckUnitOfMeasureFromRepository("cup");
        UnitOfMeasure ripe = getAndCheckUnitOfMeasureFromRepository("ripe");
        UnitOfMeasure minced = getAndCheckUnitOfMeasureFromRepository("minced");
        UnitOfMeasure chopped = getAndCheckUnitOfMeasureFromRepository("chopped");
        UnitOfMeasure garnish = getAndCheckUnitOfMeasureFromRepository("garnish");
        UnitOfMeasure dash = getAndCheckUnitOfMeasureFromRepository("dash");

        Ingredient avocados = new Ingredient("Avocados", BigDecimal.valueOf(2), ripe);
        Ingredient salt = new Ingredient("Salt", BigDecimal.valueOf(0.25), teaspoon);
        Ingredient freshLimeJuice = new Ingredient("Fresh Lime Juice", BigDecimal.valueOf(1), tablespoon);
        Ingredient redOnions = new Ingredient("Red onions", BigDecimal.valueOf(0.25), cup);
        Ingredient serannoChiles = new Ingredient("Seranno Chiles - stems and seeds removed", BigDecimal.valueOf(1), minced);
        Ingredient cilantro = new Ingredient("Cilantro", BigDecimal.valueOf(2), chopped);
        Ingredient blackPepper = new Ingredient("Black Pepper", BigDecimal.valueOf(0), dash);
        Ingredient tomato = new Ingredient("Tomato", BigDecimal.valueOf(0.5), chopped);
        Ingredient redRadish = new Ingredient("Red Radish", BigDecimal.valueOf(0), garnish);
        Ingredient tortilla = new Ingredient("Tortilla Chips", BigDecimal.valueOf(0), garnish);

        perfectGuacamole.setDescription("American Perfect Guacamole");
        perfectGuacamole.setPrepTime(10);
        perfectGuacamole.setCookTime(0);
        perfectGuacamole.setServings(4);
        perfectGuacamole.setDifficulty(Difficulty.EASY);
        perfectGuacamole.setSource("Simply Recipes");
        perfectGuacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        perfectGuacamole.setIngredients(ingredientSet);
        perfectGuacamole.setDirections("" +
                "1. Cut the avocado, remove flesh: Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon." +
                "\n2. Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
                "\n3. Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown." +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness." +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste." +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving." +
                "\n4. Serve: Serve immediately, or if making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.");

        Notes guacamoleNotes = new Notes();
        guacamoleNotes.setRecipeNotes("Guacamole is best eaten right after it’s made. Like apples, avocados start to oxidize and turn brown once they’ve been cut. That said, the acid in the lime juice you add to guacamole can help slow down that process, and if you store the guacamole properly, you can easily make it a few hours ahead if you are preparing for a party.\n" +
                "\n" +
                "The trick to keeping guacamole green is to make sure air doesn’t touch it! Transfer it to a container, cover with plastic wrap, and press down on the plastic wrap to squeeze out any air pockets. Make sure any exposed surface of the guacamole is touching the plastic wrap, not air. This will keep the amount of browning to a minimum.\n" +
                "\n" +
                "You can store the guacamole in the fridge this way for up to three days.\n" +
                "\n" +
                "If you leave the guacamole exposed to air, it will start to brown and discolor. " +
                "That browning isn’t very appetizing, but the guacamole is still good. " +
                "You can either scrape off the brown parts and discard, or stir them into the rest of the guacamole.");

        perfectGuacamole.setNotes(guacamoleNotes);

        perfectGuacamole.addIngredient(avocados);
        perfectGuacamole.addIngredient(salt);
        perfectGuacamole.addIngredient(freshLimeJuice);
        perfectGuacamole.addIngredient(redOnions);
        perfectGuacamole.addIngredient(serannoChiles);
        perfectGuacamole.addIngredient(cilantro);
        perfectGuacamole.addIngredient(blackPepper);
        perfectGuacamole.addIngredient(tomato);
        perfectGuacamole.addIngredient(redRadish);
        perfectGuacamole.addIngredient(tortilla);

        perfectGuacamole.getCategories().add(americanCategory);
        perfectGuacamole.getCategories().add(mexicanCategory);

        return perfectGuacamole;
    }

    private UnitOfMeasure getAndCheckUnitOfMeasureFromRepository(String description) {
        Optional<UnitOfMeasure> optionalUnitOfMeasure = unitOfMeasureRepository.findByDescription(description);
        if (!optionalUnitOfMeasure.isPresent()) {
            throw new RuntimeException("UOM does not exist in DB for: " + description);
        }
        return optionalUnitOfMeasure.get();
    }

    private Category getAndCheckCategoryFromRepository(String description) {
        Optional<Category> optionalCategory = categoryRepository.findByDescription(description);
        if (!optionalCategory.isPresent()) {
            throw new RuntimeException("Category does not exist in DB for: " + description);
        }
        return optionalCategory.get();
    }
}
