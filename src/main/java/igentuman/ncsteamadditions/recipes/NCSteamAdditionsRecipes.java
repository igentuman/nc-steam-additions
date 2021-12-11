package igentuman.ncsteamadditions.recipes;

import static nc.config.NCConfig.ore_dict_raw_material_recipes;
import static nc.config.NCConfig.processor_time;
import static nc.config.NCConfig.turbine_expansion_level;
import static nc.config.NCConfig.turbine_power_per_mb;
import static nc.config.NCConfig.turbine_spin_up_multiplier;
import static nc.util.FluidStackHelper.BUCKET_VOLUME;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import igentuman.ncsteamadditions.block.NCSteamAdditionsBlocks;
import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import igentuman.ncsteamadditions.recipe.NCSteamAdditionsRecipeHelper;
import nc.config.NCConfig;
import nc.enumm.MetaEnums;
import nc.init.NCItems;
import nc.radiation.RadSources;
import nc.recipe.AbstractRecipeHandler;
import nc.recipe.NCRecipes;
import nc.recipe.RecipeHelper;
import nc.recipe.ingredient.EmptyFluidIngredient;
import nc.recipe.ingredient.EmptyItemIngredient;
import nc.recipe.ingredient.FluidIngredient;
import nc.recipe.ingredient.IFluidIngredient;
import nc.recipe.ingredient.IItemIngredient;
import nc.recipe.ingredient.ItemIngredient;
import nc.util.FluidRegHelper;
import nc.util.FluidStackHelper;
import nc.util.NCMath;
import nc.util.OreDictHelper;
import nc.util.StringHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

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
	
	
	public static final List<String> PLASTIC_TYPES = Lists.newArrayList("bioplastic", "sheetPlastic");
	
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
