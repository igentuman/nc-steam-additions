package igentuman.ncsteamadditions.recipes;

import igentuman.ncsteamadditions.processors.SteamFluidTransformer;
import nc.recipe.BasicRecipeHandler;
import nc.recipe.ingredient.EmptyFluidIngredient;
import nc.recipe.ingredient.EmptyItemIngredient;
import nc.util.FluidStackHelper;

import java.util.ArrayList;
import java.util.List;

public class SteamFluidTransformerRecipes extends BasicRecipeHandler
{

	public SteamFluidTransformerRecipes()
	{
		super(SteamFluidTransformer.code,
				SteamFluidTransformer.inputItems,
				SteamFluidTransformer.inputFluids,
				SteamFluidTransformer.outputItems,
				SteamFluidTransformer.outputFluids);
	}

	@Override
	public void addRecipes()
	{
		addRecipe(fluidStack("steam", FluidStackHelper.INGOT_VOLUME),
				fluidStack("water", FluidStackHelper.INGOT_VOLUME),
				new EmptyFluidIngredient(),
				new EmptyFluidIngredient(),
				fluidStack("steam", FluidStackHelper.INGOT_VOLUME*2)
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
