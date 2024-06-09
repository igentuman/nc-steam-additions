package igentuman.ncsteamadditions.recipe;

import crafttweaker.annotations.ZenRegister;
import nc.recipe.ingredient.*;
import nc.tile.internal.fluid.Tank;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Optional;
import stanhebben.zenscript.annotations.*;

import java.util.List;

@ZenClass("mods.ncsteamadditions.INCSteamAdditionsRecipe")
@ZenRegister
public interface INCSteamAdditionsRecipe {
	
	public List<IItemIngredient> getItemIngredients();
	
	public List<IFluidIngredient> getFluidIngredients();

	
	public List<IItemIngredient> getItemProducts();
	
	public List<IFluidIngredient> getFluidProducts();

	
	@ZenMethod("getItemIngredient")
	@Optional.Method(modid = "crafttweaker")
	public default crafttweaker.api.item.IIngredient ctItemIngredient(int index)
	{
		return getItemIngredients().get(index).ct();
	}
	
	@ZenMethod("getFluidIngredient")
	@Optional.Method(modid = "crafttweaker")
	public default crafttweaker.api.item.IIngredient ctFluidIngredient(int index)
	{
		return getFluidIngredients().get(index).ct();
	}
	
	@ZenMethod("getItemProduct")
	@Optional.Method(modid = "crafttweaker")
	public default crafttweaker.api.item.IIngredient ctItemProduct(int index)
	{
		return getItemProducts().get(index).ct();
	}
	
	@ZenMethod("getFluidProduct")
	@Optional.Method(modid = "crafttweaker")
	public default crafttweaker.api.item.IIngredient ctFluidProduct(int index)
	{
		return getFluidProducts().get(index).ct();
	}
	
	@ZenMethod
	public List getExtras();

	public NCSteamAdditionsRecipeMatchResult matchInputs(List<ItemStack> itemInputs, List<Tank> fluidInputs, List extras);
	public NCSteamAdditionsRecipeMatchResult matchOutputs(List<ItemStack> itemOutputs, List<Tank> fluidOutputs);

	public NCSteamAdditionsRecipeMatchResult matchIngredients(List<IItemIngredient> itemIngredients, List<IFluidIngredient> fluidIngredients);

	public NCSteamAdditionsRecipeMatchResult matchProducts(List<IItemIngredient> itemProducts, List<IFluidIngredient> fluidProducts);

	}
