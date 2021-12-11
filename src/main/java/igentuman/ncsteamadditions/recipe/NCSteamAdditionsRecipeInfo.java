package igentuman.ncsteamadditions.recipe;

import java.util.List;

import javax.annotation.Nonnull;

public class NCSteamAdditionsRecipeInfo<T extends INCSteamAdditionsRecipe>
{

	private final T recipe;

	private final NCSteamAdditionsRecipeMatchResult matchResult;

	public NCSteamAdditionsRecipeInfo(@Nonnull T recipe, NCSteamAdditionsRecipeMatchResult matchResult)
	{
		this.recipe = recipe;
		this.matchResult = matchResult;
	}

	public @Nonnull T getRecipe()
	{
		return recipe;
	}

	/** Already takes item input order into account! */
	public List<Integer> getItemIngredientNumbers()
	{
		return matchResult.itemIngredientNumbers;
	}

	/** Already takes fluid input order into account! */
	public List<Integer> getFluidIngredientNumbers()
	{
		return matchResult.fluidIngredientNumbers;
	}


	public List<Integer> getItemInputOrder()
	{
		return matchResult.itemInputOrder;
	}

	public List<Integer> getFluidInputOrder()
	{
		return matchResult.fluidInputOrder;
	}

}