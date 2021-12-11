package igentuman.ncsteamadditions.crafttweaker;

import crafttweaker.IAction;
import igentuman.ncsteamadditions.recipe.NCSteamAdditionsRecipeHandler;

public class RemoveAllNCSteamAdditionsRecipes implements IAction
{
	
	public static boolean hasErrored = false;
	public final NCSteamAdditionsRecipeHandler recipeHandler;
	
	public RemoveAllNCSteamAdditionsRecipes(NCSteamAdditionsRecipeHandler recipeHandler)
	{
		this.recipeHandler = recipeHandler;
	}
	
	@Override
	public void apply() 
	{
		recipeHandler.removeAllRecipes();
	}
	
	@Override
	public String describe() 
	{
		return String.format("Removing all %s recipes", recipeHandler.getRecipeName());
	}
}
