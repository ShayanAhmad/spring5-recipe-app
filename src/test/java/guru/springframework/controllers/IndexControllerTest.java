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

import guru.springframework.domain.Recipe;
import guru.springframework.services.impl.RecipeServiceImpl;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by Shayan Ahmad (v-shahmad) on 05-Jun-20.
 */
@RunWith(SpringRunner.class)
//@WebMvcTest(IndexController.class)
public class IndexControllerTest {

    @Mock
    private RecipeServiceImpl recipeService;

    @InjectMocks
    private IndexController indexController;

    private MockMvc mockMvc;

    private Set<Recipe> recipes = new HashSet<>();

    @Before
    public void setUp() throws Exception {
        recipes.add(new Recipe(1L, "First Recipe"));
        recipes.add(new Recipe(2L, "Second Recipe"));
        mockMvc = MockMvcBuilders
                .standaloneSetup(indexController)
                .build();
    }

    @Test
    public void testIndexPageWithNoPath() throws Exception {
        when(recipeService.getAllRecipes()).thenReturn(recipes);
        mockMvc.perform(get(""))
                .andExpect(status().isOk())
                .andExpect(view().name("index-page"))
                .andExpect(model().attribute("recipes", Matchers.hasSize(2)));
    }

    @Test
    public void testGetAllRecipesWithIndexURL() throws Exception {
        when(recipeService.getAllRecipes()).thenReturn(recipes);
        mockMvc.perform(get("/index"))
                .andExpect(status().isOk())
                .andExpect(view().name("index-page"))
                .andExpect(model().attribute("recipes", Matchers.hasSize(2)));
    }

}