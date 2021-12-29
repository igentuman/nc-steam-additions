package igentuman.ncsteamadditions.recipes;

import java.util.ArrayList;
import java.util.List;

import igentuman.ncsteamadditions.processors.SteamTransformer;
import nc.recipe.BasicRecipeHandler;
import nc.recipe.ingredient.EmptyItemIngredient;
import nc.util.FluidStackHelper;

public class SteamTransformerRecipes extends BasicRecipeHandler
{

	public SteamTransformerRecipes()
	{
		super(SteamTransformer.code, SteamTransformer.inputItems, SteamTransformer.inputFluids, SteamTransformer.outputItems, SteamTransformer.outputFluids);
	}

	@Override
	public void addRecipes()
	{
		addRecipe("dustRedstone",new EmptyItemIngredient(),new EmptyItemIngredient(),new EmptyItemIngredient(),
				fluidStack("steam", FluidStackHelper.INGOT_VOLUME),
				oreStack("dustIron", 1)
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
