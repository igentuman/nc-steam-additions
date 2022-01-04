package igentuman.ncsteamadditions.recipes;

import java.util.List;

import com.google.common.collect.Lists;

import igentuman.ncsteamadditions.processors.AbstractProcessor;
import igentuman.ncsteamadditions.processors.ProcessorsRegistry;
import igentuman.ncsteamadditions.processors.SteamTransformer;
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

	public static ProcessorRecipeHandler[] processorRecipeHandlers;
	public static List<List<String>>[] validFluids;

	@SubscribeEvent(priority = EventPriority.LOW)
	public void registerRecipes(RegistryEvent.Register<IRecipe> event)
	{
		if (initialized)
			return;
		processorRecipeHandlers = new ProcessorRecipeHandler[ProcessorsRegistry.get().processors().length];
		for(AbstractProcessor processor: ProcessorsRegistry.get().processors()) {
			processorRecipeHandlers[processor.getGuid()] = processor.getRecipes();
		}

		addRecipes();
		initialized = true;
	}


	public static void init() 
	{
		validFluids = new List[processorRecipeHandlers.length];
		for(AbstractProcessor proc: ProcessorsRegistry.get().processors()) {
			validFluids[proc.getGuid()] = RecipeHelper.validFluids(processorRecipeHandlers[proc.getGuid()]);
		}
	}

	public static void refreshRecipeCaches() 
	{
		for(ProcessorRecipeHandler recipeHandler: processorRecipeHandlers) {
			recipeHandler.refreshCache();
		}
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
