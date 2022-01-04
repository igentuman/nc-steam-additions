package igentuman.ncsteamadditions.recipes;

import igentuman.ncsteamadditions.processors.SteamBoiler;
import nc.recipe.BasicRecipeHandler;
import nc.recipe.ingredient.EmptyItemIngredient;
import nc.util.FluidStackHelper;

import java.util.ArrayList;
import java.util.List;

public class SteamBoilerRecipes extends BasicRecipeHandler
{

	public SteamBoilerRecipes()
	{
		super(SteamBoiler.code, SteamBoiler.inputItems, SteamBoiler.inputFluids, SteamBoiler.outputItems, SteamBoiler.outputFluids);
	}

	@Override
	public void addRecipes()
	{
		addRecipe(

				"coal",
				fluidStack("water", FluidStackHelper.BUCKET_VOLUME*4),
				fluidStack("steam", FluidStackHelper.BUCKET_VOLUME*10)
				);
		
	}

	@Override
	public List fixExtras(List extras) 
	{
		List fixed = new ArrayList(3);
		fixed.add(extras.size() > 0 && extras.get(0) instanceof Double ? (double) extras.get(0) : 1D);
		fixed.add(extras.size() > 1 && extras.get(1) instanceof Double ? (double) extras.get(1) : 1D);
		fixed.add(extras.size() > 2 && extras.get(2) instanceof Double ? (double) extras.get(2) : 0D);
		return fixed;
	}

}
