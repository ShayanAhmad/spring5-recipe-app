package guru.springframework.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.repositories.IngredientRepository;
import guru.springframework.services.RecipeService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Shayan Ahmad (v-shahmad) on 13/06/2020.
 */
public class IngredientServiceImplTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private IngredientToIngredientCommand ingredientToIngredientCommand;

    @Mock
    private RecipeServiceImpl recipeService;

    @InjectMocks
    private IngredientServiceImpl ingredientService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllIngredientsOfRecipe() {
        final Long REQUESTED_RECIPE_ID = 3L;

        List<Ingredient> dbIngredientList = new ArrayList<>();
        dbIngredientList.add(Ingredient.builder().id(1L).recipe(Recipe.builder().id(3L).build()).build());
        dbIngredientList.add(Ingredient.builder().id(2L).recipe(Recipe.builder().id(3L).build()).build());

        when(ingredientToIngredientCommand.convert(any(Ingredient.class)))
                .thenReturn(IngredientCommand.builder().id(1L).recipeId(3L).build())
                .thenReturn(IngredientCommand.builder().id(2L).recipeId(3L).build());

        when(ingredientRepository.findByRecipe_Id(REQUESTED_RECIPE_ID))
                .thenReturn(dbIngredientList);

        Set<IngredientCommand> resultSet = ingredientService.getAllIngredientsOfRecipe(REQUESTED_RECIPE_ID);

        assertNotNull(resultSet);
        assertFalse(resultSet.isEmpty());
        assertThat(resultSet, Matchers.hasSize(2));

        verify(ingredientRepository).findByRecipe_Id(REQUESTED_RECIPE_ID);
        verifyNoMoreInteractions(ingredientRepository);
    }

    @Test
    public void testGetAllIngredientsOfRecipeShouldReturnEmptySetWhenRecipeIdDoesNotExist() {
        final Long REQUESTED_RECIPE_ID = 1L;
        when(ingredientRepository.findByRecipe_Id(REQUESTED_RECIPE_ID)).thenReturn(Collections.emptyList());
        Set<IngredientCommand> resultSet = ingredientService.getAllIngredientsOfRecipe(REQUESTED_RECIPE_ID);

        assertNotNull(resultSet);
        assertTrue(resultSet.isEmpty());

        verify(ingredientRepository).findByRecipe_Id(REQUESTED_RECIPE_ID);
        verifyNoMoreInteractions(ingredientRepository);
    }

    @Test(expected = RuntimeException.class)
    public void testGetAllIngredientsOfRecipeShouldThrowExceptionWhenRecipeIdIsNull() {
        ingredientService.getAllIngredientsOfRecipe(null);
    }


    @Test
    public void testGetIngredientByIdShouldReturnIngredientCommand() {
        // given
        final Long RECIPE_ID = 22L;
        final Long INGREDIENT_ID = 1L;
        final Ingredient DB_INGREDIENT = Ingredient.builder()
                .id(INGREDIENT_ID)
                .recipe(Recipe.builder()
                        .id(RECIPE_ID)
                        .build())
                .build();

        final IngredientCommand INGREDIENT_COMMAND = IngredientCommand.builder()
                .id(INGREDIENT_ID)
                .recipeId(RECIPE_ID)
                .build();

        // when
        when()
        when(ingredientRepository.findById(INGREDIENT_ID)).thenReturn(Optional.of(DB_INGREDIENT));
        when(ingredientToIngredientCommand.convert(DB_INGREDIENT)).thenReturn(INGREDIENT_COMMAND);

        // then
        IngredientCommand result = ingredientService.getIngredientById(INGREDIENT_ID);

        // assert
        assertNotNull(result);
        assertEquals(RECIPE_ID, result.getRecipeId());
        assertEquals(INGREDIENT_ID, result.getId());

        // verify
        verify(ingredientRepository).findById(INGREDIENT_ID);
        verifyNoMoreInteractions(ingredientRepository);

        verify(ingredientToIngredientCommand).convert(DB_INGREDIENT);
        verifyNoMoreInteractions(ingredientToIngredientCommand);
    }

    @Test(expected = RuntimeException.class)
    public void testGetIngredientByIdShouldWhenGivenIdDoesNotExist() {
        //given
        final Long RECIPE_ID = 2L;
        final Long INGREDIENT_ID = 1L;

        //when
        when(ingredientRepository.findById(INGREDIENT_ID)).thenReturn(Optional.empty());

        //then
        ingredientService.getIngredientById(INGREDIENT_ID);
    }

}