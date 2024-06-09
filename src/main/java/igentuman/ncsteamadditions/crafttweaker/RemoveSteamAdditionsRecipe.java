package igentuman.ncsteamadditions.crafttweaker;

import crafttweaker.*;
import crafttweaker.api.item.IIngredient;
import igentuman.ncsteamadditions.recipe.*;
import nc.integration.crafttweaker.CTHelper;
import nc.recipe.IngredientSorption;
import nc.recipe.ingredient.*;

import java.util.*;

public class RemoveSteamAdditionsRecipe implements IAction
{
	
	public static boolean hasErrored = false;
	
	public List<IItemIngredient> itemIngredients;
	public List<IFluidIngredient> fluidIngredients;
	public IngredientSorption type;
	public NCSteamAdditionsRecipe recipe;
	public boolean ingredientError, wasNull, wrongSize;
	public final NCSteamAdditionsRecipeHandler recipeHandler;

	public RemoveSteamAdditionsRecipe(NCSteamAdditionsRecipeHandler recipeHandler, IngredientSorption type, List<IIngredient> ctIngredients)
	{
		this.recipeHandler = recipeHandler;
		this.type = type;
		int itemSize = type == IngredientSorption.INPUT ? recipeHandler.getItemInputSize() : recipeHandler.getItemOutputSize();
		int fluidSize = type == IngredientSorption.INPUT ? recipeHandler.getFluidInputSize() : recipeHandler.getFluidOutputSize();
		if (ctIngredients.size() != itemSize + fluidSize)
		{
			CraftTweakerAPI.logError("A " + recipeHandler.getRecipeName() + " recipe was the wrong size");
			wrongSize = true;
			return;
		}
		List<IItemIngredient> itemIngredients = new ArrayList<>();
		List<IFluidIngredient> fluidIngredients = new ArrayList<>();
		for (int i = 0; i < itemSize; i++)
		{
			IItemIngredient ingredient = CTHelper.buildRemovalItemIngredient(ctIngredients.get(i));
			if (ingredient == null)
			{
				ingredientError = true;
				return;
			}
			itemIngredients.add(ingredient);
		}
		for (int i = itemSize; i < itemSize+fluidSize; i++)
		{
			IFluidIngredient ingredient = CTHelper.buildRemovalFluidIngredient(ctIngredients.get(i));
			if (ingredient == null)
			{
				ingredientError = true;
				return;
			}
			fluidIngredients.add(ingredient);
		}

		this.itemIngredients = itemIngredients;
		this.fluidIngredients = fluidIngredients;
		
		this.recipe = type == IngredientSorption.INPUT ? recipeHandler.getRecipeFromIngredients(itemIngredients, fluidIngredients) : null;
	
		if (recipe == null) wasNull = true;
	}
	
	@Override
	public void apply()
	{
		if (!ingredientError && !wasNull && !wrongSize)
		{
			boolean removed = recipeHandler.removeRecipe(recipe);
			while (removed) {
				
				recipe = type == IngredientSorption.INPUT ? recipeHandler.getRecipeFromIngredients(itemIngredients, fluidIngredients) : null;
				removed = recipeHandler.removeRecipe(recipe);
			}
		}
	}
	
	@Override
	public String describe()
	{
		if (ingredientError || wasNull || wrongSize)
		{
			if (ingredientError || wrongSize) callError();
			return String.format("Error: Failed to remove %s recipe with %s as the " + (type == IngredientSorption.INPUT ? "input" : "output"), recipeHandler.getRecipeName(), NCSteamAdditionsRecipeHelper.getAllIngredientNamesConcat(itemIngredients, fluidIngredients));
		}
		if (type == IngredientSorption.INPUT)
		{
			return String.format("Removing %s recipe: %s", recipeHandler.getRecipeName(), NCSteamAdditionsRecipeHelper.getRecipeString(recipe));
		}
		else return String.format("Removing %s recipes for: %s", recipeHandler.getRecipeName(), NCSteamAdditionsRecipeHelper.getAllIngredientNamesConcat(itemIngredients, fluidIngredients));
	}
	
	public static void callError()
	{
		if (!hasErrored)
		{
			CraftTweakerAPI.logError("At least one CraftTweaker recipe removal method has errored - check the CraftTweaker log for more details");
		}
		hasErrored = true;
	}
}
