package guru.springframework.controllers;

import java.util.HashSet;
import java.util.Set;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.services.impl.RecipeServiceImpl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by Shayan Ahmad (v-shahmad) on 05-Jun-20.
 */
@RunWith(SpringRunner.class)
public class RecipeControllerTest {

    @Mock
    private RecipeServiceImpl recipeService;

    @InjectMocks
    private RecipeController recipeController;

    private MockMvc mockMvc;

    private Set<Recipe> recipes = new HashSet<>();

    @Before
    public void setUp() {
        recipes.add(new Recipe(1L, "First Recipe"));
        recipes.add(new Recipe(2L, "Second Recipe"));
        mockMvc = MockMvcBuilders
                .standaloneSetup(recipeController)
                .build();
    }

    @Test
    public void testGetRecipeById() throws Exception {
        final Long ID = 1L;
        Recipe returningRecipe = new Recipe(ID, "First Recipe");
        when(recipeService.findById(ID)).thenReturn(returningRecipe);

        mockMvc.perform(get("/recipe/" + ID + "/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attribute("recipe", Matchers.is(returningRecipe)));
    }

    @Test
    public void testUpdateRecipeById() throws Exception {
        final Long ID = 1L;
        final String UPDATE_URL = "/recipe/" + ID + "/update";
        RecipeCommand returningRecipe = RecipeCommand.builder().id(1L).build();

        when(recipeService.findRecipeCommandById(ID)).thenReturn(returningRecipe);

        mockMvc.perform(get(UPDATE_URL))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attribute("recipe", Matchers.is(returningRecipe)));
    }

    @Test
    public void testDeleteRecipeById() throws Exception {
        final Long ID = 1L;
        final String DELETE_URL = "/recipe/" + ID + "/delete";

        mockMvc.perform(get(DELETE_URL))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }
}