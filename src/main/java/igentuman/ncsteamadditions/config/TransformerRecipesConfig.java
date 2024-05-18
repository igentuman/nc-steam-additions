package igentuman.ncsteamadditions.config;

import igentuman.ncsteamadditions.NCSteamAdditions;
import igentuman.ncsteamadditions.util.Util;
import nc.recipe.ingredient.*;
import nc.util.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.*;

import java.io.File;

public class TransformerRecipesConfig {

	private static Configuration config = null;
	public static final String RECIPES = "recipes";
	public static String[] transformerRecipes;
	public static String[] fluidTransformerRecipes;
	public static String[] digitalTransformerRecipes;

	public static void preInit()
	{
		config = new Configuration(new File(Loader.instance().getConfigDir(), "NCS_transformer_recipes.cfg"));
		syncConfig(true, true);
	}

	public static OreIngredient oreStack(String oreType, int stackSize) {
		return !OreDictHelper.oreExists(oreType) ? null : new OreIngredient(oreType, stackSize);
	}

	public static FluidIngredient fluidStack(String fluidName, int stackSize) {
		return !FluidRegHelper.fluidExists(fluidName) ? null : new FluidIngredient(fluidName, stackSize);
	}

	public static void clientPreInit()
	{
		MinecraftForge.EVENT_BUS.register(new ClientConfigEventHandler());
	}


	private static void syncConfig(boolean loadFromFile, boolean setFromConfig)
	{
		if (loadFromFile) config.load();

		String[] defaultTransformerRecipes = new String[] {
				"minecraft:water_bucket;blockPrismarine;plateBasic*4;dustTin;low_quality_steam*250=nuclearcraft:water_source",
				"blockMagnesium;solenoidCopper*4;plateBasic*4;null;steam*250=nuclearcraft:voltaic_pile_basic",
				"ingotSteel;itemSilicon;stone;null;low_pressure_steam*250=oreBoron",
				"ingotLead;gemEmerald;stone;null;low_pressure_steam*250=oreUranium",
				"ingotClay;dyeGreen;null;null;high_pressure_steam*1500=ingotUranium",
				"ingotClay;dyeYellow;null;null;high_pressure_steam*1500=ingotGold",
				"ingotClay;dyeGray;null;null;high_pressure_steam*1500=ingotBoron",
				"ingotClay;dyeWhite;null;null;high_pressure_steam*1500=ingotTin",
				"ingotIron;ingotBoron;ingotLithium;coal;high_pressure_steam*500=ingotTough*2",
				"ingotClay;dyeLightBlue;null;null;high_pressure_steam*2500=ingotPlatinum",
				"compressedCoal;null;null;null;high_pressure_steam*2500=gemDiamond"
		};

		String[] defaultDigitalTransformerRecipes = new String[] {
				"blockGlowstone;null;null;null;null;null;null;high_pressure_steam*250=advanced_solar_panels:crafting:0;steam*25",
				"minecraft:wool:11;null;null;null;null;null;null;high_pressure_steam*250=minecraft:lapis_block;steam*25",
				"minecraft:wool:14;null;null;null;null;null;null;high_pressure_steam*250=minecraft:redstone_block;steam*25",
				"minecraft:dye:4;null;null;null;null;null;null;high_pressure_steam*250=gemSapphire;steam*25",
				"dustTitanium;null;null;null;null;null;null;high_pressure_steam*250=dustChrome;steam*25",
				"ingotTitanium;null;null;null;null;null;null;high_pressure_steam*250=ingotChrome;steam*25",
				"gemNetherQuartz;null;null;null;null;null;null;high_pressure_steam*250=gemCertusQuartz;steam*25",
				"ingotCopper;null;null;null;null;null;null;high_pressure_steam*250=ingotNickel;steam*25",
				"ingotTin;null;null;null;null;null;null;high_pressure_steam*250=ingotSilver;steam*25",
				"ingotGold;null;null;null;null;null;null;high_pressure_steam*250=ingotPlatinum;steam*25",
				"ingotSteel;null;null;null;null;null;null;high_pressure_steam*250=galacticraftcore:meteoric_iron_raw;steam*25",
				"ingotThorium;null;null;null;null;null;null;high_pressure_steam*125=ingotTBU;steam*25",
				"minecraft:skull:1;null;null;null;null;null;null;high_pressure_steam*250=netherStar;steam*25"
		};

		String[] defaultFluidTransformerRecipes = new String[] {
				"steam;ethene;lava;glowstone=pyrotheum*2000",
				"steam;ethanol;fluorite_water;lapis=cryotheum*2000",
				"steam;low_quality_steam;null;null=low_pressure_steam*2000",
				"steam;lead;glowstone*144;null=gold*288",
				"steam;iron*144;coal*432;null=steel*144",
				"steam*125;water;lava;null=obsidian",
				"steam*125;water;null;null=preheated_water",
				"high_pressure_steam*125;uranium_oxide;hydrofluoric_acid*500;null=uranium_hexafluoride*1500"
		};

		transformerRecipes = config.get(RECIPES, "transformer", defaultTransformerRecipes).getStringList();
		fluidTransformerRecipes = config.get(RECIPES, "fluid_transformer", defaultFluidTransformerRecipes).getStringList();
		digitalTransformerRecipes = config.get(RECIPES, "digital_transformer", defaultDigitalTransformerRecipes).getStringList();

		if (config.hasChanged()) config.save();
	}

	public static Object[] parseDigitalTransformerRecipe(String recipe)
	{

		//4 item inputs
		//4 fluid inputs
		//1 item input
		//1 fluid input


		String[] parts = recipe.split("=");
		if (parts.length != 2) return null;
		String[] input = parts[0].split(";");
		Object[] recipeObj = new Object[input.length + 2];
		int qty = 1000;
		try {
			//items
			for (int i = 0; i < 4; i++) {
				String[] ingredient = input[i].split("\\*");
				if (input[i].equals("null")) {
					recipeObj[i] = new EmptyItemIngredient();
					continue;
				}
				qty = 1;
				if (ingredient.length > 1) {
					qty = Integer.parseInt(ingredient[1]);
				}
				if(OreDictHelper.oreExists(ingredient[0])) {
					recipeObj[i] = oreStack(ingredient[0], qty);
				} else {
					ItemStack stack = RegistryHelper.itemStackFromRegistry(ingredient[0],qty);
					if(stack == null) {
						stack = RegistryHelper.blockStackFromRegistry(ingredient[0], qty);
					}
					if((stack == null || stack.isEmpty()) && !ingredient[0].equals("null")) {
						return null;
					}
					recipeObj[i] = stack;
				}
			}

			//fluids
			for (int i = 4; i < 8; i++) {
				String[] ingredient = input[i].split("\\*");
				if (input[i].equals("null")) {
					recipeObj[i] = new EmptyFluidIngredient();
					continue;
				}
				qty = 1000;
				if (ingredient.length > 1) {
					qty = Integer.parseInt(ingredient[1]);
				}
				if (!FluidRegHelper.fluidExists(ingredient[0])) {
					return null;
				}
				recipeObj[i] = fluidStack(ingredient[0], qty);
			}
			String[] output = parts[1].split(";");
			String[] outputItem = output[0].split("\\*");
			String[] outputFluid = output[1].split("\\*");

			//item
			qty = 1;
			if(outputItem.length > 1) {
				qty = Integer.parseInt(outputItem[1]);
			}
			if(OreDictHelper.oreExists(outputItem[0])) {
				recipeObj[recipeObj.length - 2] = oreStack(outputItem[0], qty);
			} else {
				ItemStack stack = RegistryHelper.itemStackFromRegistry(outputItem[0],qty);
				if(stack == null) {
					stack = RegistryHelper.blockStackFromRegistry(outputItem[0], qty);
				}
				if((stack == null || stack.isEmpty()) && !outputItem[0].equals("null")) {
					return null;
				}
				recipeObj[recipeObj.length - 2] = stack;
			}


			//fluid
			qty = 1000;
			if (outputFluid.length > 1) {
				qty = Integer.parseInt(outputFluid[1]);
			}
			if (!FluidRegHelper.fluidExists(outputFluid[0])) {
				return null;
			}
			recipeObj[recipeObj.length - 1] = fluidStack(outputFluid[0], qty);
		} catch (Exception e) {
			Util.getLogger().warn("Steam Fluid Transformer recipe exception: " + recipe);
			return null;
		}
		return recipeObj;
	}

	public static Object[] parseTransformerRecipe(String recipe)
	{
		String[] parts = recipe.split("=");
		if(parts.length != 2) return null;
		String[] input = parts[0].split(";");
		Object[] recipeObj = new Object[input.length+1];
		int qty = 1;
		try {
			for(int i = 0; i < input.length; i++) {
				String[] ingredient = input[i].split("\\*");
				if(input[i].equals("null")) {
					recipeObj[i] = new EmptyItemIngredient();
					continue;
				}
				if(i == 4) {
					qty = 1000;
					recipeObj[i] = fluidStack(ingredient[0], qty);
				} else {
					qty = 1;
					if(ingredient.length > 1) {
						qty = Integer.parseInt(ingredient[1]);
					}
					if(OreDictHelper.oreExists(ingredient[0])) {
						recipeObj[i] = oreStack(ingredient[0], qty);
					} else {
						ItemStack stack = RegistryHelper.itemStackFromRegistry(ingredient[0],qty);
						if(stack == null) {
							stack = RegistryHelper.blockStackFromRegistry(ingredient[0], qty);
						}
						if((stack == null || stack.isEmpty()) && !ingredient[0].equals("null")) {
							return null;
						}
						recipeObj[i] = stack;
					}
				}
			}
			String[] output = parts[1].split("\\*");
			qty = 1;
			if(output.length > 1) {
				qty = Integer.parseInt(output[1]);
			}
			if(OreDictHelper.oreExists(output[0])) {
				recipeObj[recipeObj.length-1] = oreStack(output[0], qty);
			} else {
				ItemStack stack = RegistryHelper.itemStackFromRegistry(output[0],qty);
				if(stack == null) {
					stack = RegistryHelper.blockStackFromRegistry(output[0], qty);
				}
				if((stack == null || stack.isEmpty()) && !output[0].equals("null")) {
					return null;
				}
				recipeObj[recipeObj.length-1] = stack;
			}
		} catch (Exception e) {
			Util.getLogger().warn("Steam Transformer recipe exception: " + recipe);
			return null;
		}
		return recipeObj;
	}

	public static Object[] parseFluidTransformerRecipe(String recipe)
	{
		String[] parts = recipe.split("=");
		if(parts.length != 2) return null;
		String[] input = parts[0].split(";");
		Object[] recipeObj = new Object[input.length+1];
		int qty = 1000;
		try {
			for (int i = 0; i < 4; i++) {
				String[] ingredient = input[i].split("\\*");
				if (input[i].equals("null")) {
					recipeObj[i] = new EmptyFluidIngredient();
					continue;
				}
				qty = 1000;
				if (ingredient.length > 1) {
					qty = Integer.parseInt(ingredient[1]);
				}
				if (!FluidRegHelper.fluidExists(ingredient[0])) {
					return null;
				}
				recipeObj[i] = fluidStack(ingredient[0], qty);
			}

			String[] output = parts[1].split("\\*");
			qty = 1000;
			if (output.length > 1) {
				qty = Integer.parseInt(output[1]);
			}
			if (!FluidRegHelper.fluidExists(output[0])) {
				return null;
			}
			recipeObj[recipeObj.length - 1] = fluidStack(output[0], qty);
		} catch (Exception e) {
			Util.getLogger().warn("Steam Transformer recipe exception: " + recipe);
			return null;
		}
		return recipeObj;
	}

	private static class ClientConfigEventHandler
	{

		@SubscribeEvent(priority = EventPriority.LOWEST)
		public void onEvent(OnConfigChangedEvent event)
		{
			if (event.getModID().equals(NCSteamAdditions.MOD_ID))
			{
				syncConfig(false, true);
			}
		}
	}
	

}
