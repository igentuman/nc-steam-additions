package igentuman.ncsteamadditions.recipes;


import nc.recipe.BasicRecipeHandler;
import java.util.ArrayList;
import java.util.List;

public abstract class ProcessorRecipeHandler extends BasicRecipeHandler
{

	public ProcessorRecipeHandler(String code, int inputItems, int inputFluids, int outputItems, int outputFluids)
	{
		super(code, inputItems, inputFluids, outputItems, outputFluids);
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

	public abstract void addRecipes();
}
