package igentuman.ncsteamadditions.recipe;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import crafttweaker.annotations.ZenRegister;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import igentuman.ncsteamadditions.NCSteamAdditions;
import igentuman.ncsteamadditions.util.Util;
import nc.config.NCConfig;
import nc.recipe.ingredient.IFluidIngredient;
import nc.recipe.ingredient.IItemIngredient;
import nc.util.NCMath;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.ncsteamadditions.NCSteamAdditionsRecipeHandler")
@ZenRegister
public abstract class NCSteamAdditionsRecipeHandler extends AbstractNCSteamAdditionsRecipeHandler<NCSteamAdditionsRecipe>
{

	private final String recipeName;
	public final int itemInputSize, fluidInputSize, itemOutputSize, fluidOutputSize;
	public final boolean isShapeless;

	public NCSteamAdditionsRecipeHandler(@Nonnull String recipeName, int itemInputSize, int fluidInputSize,
										 int itemOutputSize, int fluidOutputSize)
	{
		this(recipeName, itemInputSize, fluidInputSize, itemOutputSize, fluidOutputSize, true);
	}

	public NCSteamAdditionsRecipeHandler(@Nonnull String recipeName, int itemInputSize, int fluidInputSize,
										 int itemOutputSize, int fluidOutputSize, boolean isShapeless)
	{
		this.itemInputSize = itemInputSize;
		this.fluidInputSize = fluidInputSize;
		this.itemOutputSize = itemOutputSize;
		this.fluidOutputSize = fluidOutputSize;
		this.isShapeless = isShapeless;
		this.recipeName = recipeName;

		addRecipes();
	}

	public void addRecipe(Object... objects)
	{
		List itemInputs = new ArrayList(), fluidInputs = new ArrayList(), particleInputs = new ArrayList(),
				itemOutputs = new ArrayList(), fluidOutputs = new ArrayList(), particleOutputs = new ArrayList(),
				extras = new ArrayList();
		for (int i = 0; i < objects.length; i++)
		{
			Object object = objects[i];
			if (i < itemInputSize)
			{
				itemInputs.add(object);
			}
			else if (i < itemInputSize + fluidInputSize)
			{
				fluidInputs.add(object);
			}
			else
			{
				extras.add(object);
			}
		}
		NCSteamAdditionsRecipe recipe = buildRecipe(itemInputs, fluidInputs, itemOutputs, fluidOutputs, fixExtras(extras), isShapeless);
		
		addRecipe(recipe);
	}

	public abstract List fixExtras(List extras);

	
	@Nullable
	public NCSteamAdditionsRecipe buildRecipe(List itemInputs, List fluidInputs, List itemOutputs,
											  List fluidOutputs, List extras, boolean shapeless)
	{
		List<IItemIngredient> itemIngredients = new ArrayList<IItemIngredient>(),
				itemProducts = new ArrayList<IItemIngredient>();
		List<IFluidIngredient> fluidIngredients = new ArrayList<IFluidIngredient>(),
				fluidProducts = new ArrayList<IFluidIngredient>();


		for (Object obj : itemInputs)
		{
			if (obj != null && isValidItemInputType(obj))
			{
				IItemIngredient input = NCSteamAdditionsRecipeHelper.buildItemIngredient(obj);
				if (input == null)
					return null;
				itemIngredients.add(input);
			}
			else
				return null;
		}
		for (Object obj : fluidInputs)
		{
			if (obj != null && isValidFluidInputType(obj))
			{
				IFluidIngredient input = NCSteamAdditionsRecipeHelper.buildFluidIngredient(obj);
				if (input == null)
					return null;
				fluidIngredients.add(input);
			}
			else
				return null;
		}

		for (Object obj : itemOutputs)
		{
			if (obj != null && isValidItemOutputType(obj))
			{
				IItemIngredient output = NCSteamAdditionsRecipeHelper.buildItemIngredient(obj);
				if (output == null)
					return null;
				itemProducts.add(output);
			}
			else
				return null;
		}
		for (Object obj : fluidOutputs)
		{
			if (obj != null && isValidFluidOutputType(obj))
			{
				IFluidIngredient output = NCSteamAdditionsRecipeHelper.buildFluidIngredient(obj);
				if (output == null)
					return null;
				fluidProducts.add(output);
			}
			else
				return null;
		}

		if (!isValidRecipe(itemIngredients, fluidIngredients, itemProducts, fluidProducts))
		{
			Util.getLogger().info(getRecipeName() + " - a recipe was removed: " + NCSteamAdditionsRecipeHelper.getRecipeString(itemIngredients, fluidIngredients, itemProducts, fluidProducts));
		}
		return new NCSteamAdditionsRecipe(itemIngredients, fluidIngredients, itemProducts, fluidProducts, extras, shapeless);
	}

	public boolean isValidRecipe(List<IItemIngredient> itemIngredients, List<IFluidIngredient> fluidIngredients, List<IItemIngredient> itemProducts,
			List<IFluidIngredient> fluidProducts)
	{
		return itemIngredients.size() == itemInputSize && fluidIngredients.size() == fluidInputSize
				 && itemProducts.size() == itemOutputSize
				&& fluidProducts.size() == fluidOutputSize;
	}

	@Override
	public String getRecipeName()
	{
		return NCSteamAdditions.MOD_ID + "_" + recipeName;
	}
	
	@Override
	@ZenMethod
	public List<NCSteamAdditionsRecipe> getRecipeList()
	{
		return recipeList;
	}

	@ZenMethod
	public int getItemInputSize()
	{
		return itemInputSize;
	}

	@ZenMethod
	public int getFluidInputSize()
	{
		return fluidInputSize;
	}


	@ZenMethod
	public int getItemOutputSize()
	{
		return itemOutputSize;
	}

	@ZenMethod
	public int getFluidOutputSize()
	{
		return fluidOutputSize;
	}

	@ZenMethod
	public boolean isShapeless()
	{
		return isShapeless;
	}
}
