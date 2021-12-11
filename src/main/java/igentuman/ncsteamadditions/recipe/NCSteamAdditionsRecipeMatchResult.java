package igentuman.ncsteamadditions.recipe;

import java.util.List;

public class NCSteamAdditionsRecipeMatchResult
{

	public static final NCSteamAdditionsRecipeMatchResult FAIL = new NCSteamAdditionsRecipeMatchResult(false, AbstractNCSteamAdditionsRecipeHandler.INVALID,
			AbstractNCSteamAdditionsRecipeHandler.INVALID, AbstractNCSteamAdditionsRecipeHandler.INVALID,
			AbstractNCSteamAdditionsRecipeHandler.INVALID);

	private final boolean match;

	final List<Integer> itemIngredientNumbers, fluidIngredientNumbers, itemInputOrder,
			fluidInputOrder;

	public NCSteamAdditionsRecipeMatchResult(boolean match, List<Integer> itemIngredientNumbers,
											 List<Integer> fluidIngredientNumbers, List<Integer> itemInputOrder,
											 List<Integer> fluidInputOrder)
	{
		this.match = match;
		this.itemIngredientNumbers = itemIngredientNumbers;
		this.fluidIngredientNumbers = fluidIngredientNumbers;
		this.itemInputOrder = itemInputOrder;
		this.fluidInputOrder = fluidInputOrder;
	}

	public boolean matches()
	{
		return match;
	}
}
