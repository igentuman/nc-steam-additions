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
	public static SteamCrusherRecipes steam_crusher;
	public static SteamBoilerRecipes steam_boiler;
	public static SteamFluidTransformerRecipes steam_fluid_transformer;

	@SubscribeEvent(priority = EventPriority.LOW)
	public void registerRecipes(RegistryEvent.Register<IRecipe> event)
	{
		if (initialized)
			return;
		steam_transformer 			= new SteamTransformerRecipes();
		steam_boiler 				= new SteamBoilerRecipes();
		steam_crusher 				= new SteamCrusherRecipes();
		steam_fluid_transformer 	= new SteamFluidTransformerRecipes();
		addRecipes();
		initialized = true;
	}

	public static List<List<String>> steam_valid_fluids;
	public static List<List<String>> steam_boiler_valid_fluids;
	public static List<List<String>> steam_fluid_transformer_valid_fluids;

	public static void init() 
	{
		steam_valid_fluids = RecipeHelper.validFluids(steam_transformer);
		steam_fluid_transformer_valid_fluids = RecipeHelper.validFluids(steam_fluid_transformer);
		steam_boiler_valid_fluids = RecipeHelper.validFluids(steam_boiler);
	}

	public static void refreshRecipeCaches() 
	{

		steam_crusher.refreshCache();
		steam_boiler.refreshCache();
		steam_transformer.refreshCache();
		steam_fluid_transformer.refreshCache();
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
