package igentuman.ncsteamadditions.recipe;

import nc.recipe.IngredientSorption;
import nc.recipe.ingredient.*;
import nc.tile.internal.fluid.Tank;
import net.minecraft.item.ItemStack;

import java.util.List;

public class NCSteamAdditionsRecipe implements INCSteamAdditionsRecipe
{
	protected List<IItemIngredient> itemIngredients, itemProducts;
	protected List<IFluidIngredient> fluidIngredients, fluidProducts;
	
	protected List extras;
	public boolean isShapeless;


	public NCSteamAdditionsRecipe(List<IItemIngredient> itemIngredientsList, List<IFluidIngredient> fluidIngredientsList, List<IItemIngredient> itemProductsList,
								  List<IFluidIngredient> fluidProductsList, List extrasList, boolean shapeless)
	{
		itemIngredients = itemIngredientsList;
		fluidIngredients = fluidIngredientsList;
		itemProducts = itemProductsList;
		fluidProducts = fluidProductsList;
		extras = extrasList;
		isShapeless = shapeless;
	}

	@Override
	public List<IItemIngredient> getItemIngredients()
	{
		return itemIngredients;
	}

	@Override
	public List<IFluidIngredient> getFluidIngredients()
	{
		return fluidIngredients;
	}

	@Override
	public List<IItemIngredient> getItemProducts()
	{
		return itemProducts;
	}

	@Override
	public List<IFluidIngredient> getFluidProducts()
	{
		return fluidProducts;
	}

	@Override
	public List getExtras()
	{
		return extras;
	}
	
	public boolean isShapeless()
	{
		return isShapeless;
	}
	

	@Override
	public NCSteamAdditionsRecipeMatchResult matchInputs(List<ItemStack> itemInputs, List<Tank> fluidInputs, List extras)
	{
		
		return NCSteamAdditionsRecipeHelper.matchIngredients(IngredientSorption.INPUT, itemIngredients, fluidIngredients, itemInputs, fluidInputs, isShapeless, extras);
	}

	@Override
	public NCSteamAdditionsRecipeMatchResult matchOutputs(List<ItemStack> itemOutputs, List<Tank> fluidOutputs)
	{
		return NCSteamAdditionsRecipeHelper.matchIngredients(IngredientSorption.OUTPUT, itemProducts, fluidProducts, itemOutputs, fluidOutputs, isShapeless, extras);
	}

	@Override
	public NCSteamAdditionsRecipeMatchResult matchIngredients(List<IItemIngredient> itemIngredients, List<IFluidIngredient> fluidIngredients)
	{
		return NCSteamAdditionsRecipeHelper.matchIngredients(IngredientSorption.INPUT, this.itemIngredients, this.fluidIngredients, itemIngredients, fluidIngredients, isShapeless, extras);
	}

	@Override
	public NCSteamAdditionsRecipeMatchResult matchProducts(List<IItemIngredient> itemProducts, List<IFluidIngredient> fluidProducts)
	{
		return NCSteamAdditionsRecipeHelper.matchIngredients(IngredientSorption.OUTPUT, this.itemProducts, this.fluidProducts, itemProducts, fluidProducts, isShapeless, extras);
	}

	/* ================================== Recipe Extras ===================================== */
	
	public long getHeatReleased()
	{
		return (long) extras.get(1);
	}
	
}
