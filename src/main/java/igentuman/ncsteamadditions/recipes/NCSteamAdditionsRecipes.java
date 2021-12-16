package igentuman.ncsteamadditions.recipes;

import java.util.List;

import com.google.common.collect.Lists;

import nc.recipe.RecipeHelper;
import nc.recipe.ingredient.FluidIngredient;
import nc.util.FluidRegHelper;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NCSteamAdditionsRecipes
{
	private static boolean initialized = false;

	public static SteamTransformerRecipes steam_transformer;

	@SubscribeEvent(priority = EventPriority.LOW)
	public void registerRecipes(RegistryEvent.Register<IRecipe> event)
	{
		if (initialized)
			return;
		steam_transformer = new SteamTransformerRecipes();
		addRecipes();
		initialized = true;
	}

	public static List<List<String>> steam_transformer_valid_fluids;

	public static void init() 
	{
		steam_transformer_valid_fluids = RecipeHelper.validFluids(steam_transformer);
	}

	public static void refreshRecipeCaches() 
	{
		steam_transformer.refreshCache();
	}
	
	public static void addRecipes()
	{
		// Crafting
		NCSteamAdditionsCraftingRecipeHandler.registerCraftingRecipes();
	}

	public static FluidIngredient fluidStack(String fluidName, int stackSize)
	{
		if (!FluidRegHelper.fluidExists(fluidName))
			return null;
		return new FluidIngredient(fluidName, stackSize);
	}

}
