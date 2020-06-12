package guru.springframework.services.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.repositories.RecipeRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Shayan Ahmad (v-shahmad) on 05-Jun-20.
 */
public class RecipeServiceImplTest {
    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    private RecipeCommandToRecipe recipeCommandToRecipe;

    @InjectMocks
    private RecipeServiceImpl recipeService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getRecipesTest() throws Exception {

        Recipe recipe = new Recipe();
        HashSet receipesData = new HashSet();
        receipesData.add(recipe);

        when(recipeService.getAllRecipes()).thenReturn(receipesData);

        Set<Recipe> recipes = recipeService.getAllRecipes();

        assertEquals(recipes.size(), 1);
        verify(recipeRepository).findAll();
        verify(recipeRepository, never()).findById(anyLong());
    }

    @Test
    public void testFindByIdShouldReturnRecipe() {
        final Long ID = 1L;
        Recipe returningRecipe = new Recipe();
        returningRecipe.setId(ID);
        when(recipeRepository.findById(ID)).thenReturn(Optional.of(returningRecipe));

        Recipe result = recipeService.findById(ID);

        assertNotNull(result);
        assertEquals(Long.valueOf(1), result.getId());

        verify(recipeRepository).findById(ID);
        Mockito.verifyNoMoreInteractions(recipeRepository);
    }

    @Test(expected = RuntimeException.class)
    public void testFindByIdWhenIDNotExistShouldThrowException() {
        final Long ID = 1L;
        when(recipeRepository.findById(ID)).thenReturn(Optional.empty());

        recipeService.findById(ID);
    }

    @Test
    public void testFindRecipeCommandByIdShouldReturnValue() {
        final Long ID = 1L;
        Recipe returningRecipe = Recipe.builder().id(ID).build();
        RecipeCommand returningRecipeCommand = RecipeCommand.builder().id(ID).build();
        Optional<Recipe> optionalRecipe = Optional.of(Recipe.builder().id(ID).build());
        when(recipeRepository.findById(ID)).thenReturn(optionalRecipe);
        when(recipeToRecipeCommand.convert(returningRecipe)).thenReturn(returningRecipeCommand);

        RecipeCommand result = recipeService.findRecipeCommandById(ID);

        assertNotNull(result);
        assertEquals(ID, result.getId());

        verify(recipeToRecipeCommand).convert(returningRecipe);
        verifyNoMoreInteractions(recipeToRecipeCommand);
    }

    @Test(expected = RuntimeException.class)
    public void testFindRecipeCommandByIdIfNullShouldThrowException() {
        recipeService.findRecipeCommandById(null);
    }

    @Test
    public void testDeleteRecipeById() {
        // GIVEN
        final Long ID = 1L;

        // WHEN
        recipeService.deleteById(ID);

        // THEN
        verify(recipeRepository).deleteById(ID);
        verifyNoMoreInteractions(recipeRepository);
    }
}