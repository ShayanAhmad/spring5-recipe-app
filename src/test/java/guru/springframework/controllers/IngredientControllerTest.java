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

import guru.springframework.commands.IngredientCommand;
import guru.springframework.services.IngredientService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by Shayan Ahmad (v-shahmad) on 13/06/2020.
 */
@RunWith(SpringRunner.class)
public class IngredientControllerTest {

    private final Set<IngredientCommand> ingredientSet = new HashSet<>();

    @Mock
    private IngredientService ingredientService;

    @InjectMocks
    private IngredientController controllerUnderTest;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        ingredientSet.add(IngredientCommand.builder().id(1L).build());
        ingredientSet.add(IngredientCommand.builder().id(2L).build());

        mockMvc = MockMvcBuilders
                .standaloneSetup(controllerUnderTest)
                .build();
    }

    @Test
    public void testGetIngredientListReturnsAllList() throws Exception {
        final Long ID = 1L;
        final String URL = "/recipe/" + ID + "/ingredients";

        when(ingredientService.getAllIngredientsOfRecipe(ID)).thenReturn(ingredientSet);

        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(model().attribute("ingredients", Matchers.hasSize(2)))
                .andExpect(view().name("recipe/ingredient/list"));
    }

    @Test
    public void testGetSpecificIngredientShouldReturnIngredient() throws Exception {

        // GIVEN
        final Long INGREDIENT_ID = 11L;
        final String URL = "/recipe/1/ingredient/" + INGREDIENT_ID + "/show";
        final IngredientCommand returningIngredientCommand = IngredientCommand.builder().id(11L).build();

        // WHEN
        when(ingredientService.getIngredientById(INGREDIENT_ID)).thenReturn(returningIngredientCommand);

        // THEN
        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(model().attribute("ingredient", Matchers.is(returningIngredientCommand)))
                .andExpect(view().name("recipe/ingredient/show"));
    }
}