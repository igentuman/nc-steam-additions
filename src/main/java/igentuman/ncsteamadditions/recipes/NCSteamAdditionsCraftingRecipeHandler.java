package igentuman.ncsteamadditions.recipes;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import com.google.common.collect.Lists;
import igentuman.ncsteamadditions.NCSteamAdditions;
import igentuman.ncsteamadditions.block.Blocks;
import igentuman.ncsteamadditions.machine.block.BlockNCSteamAdditionsProcessor;
import igentuman.ncsteamadditions.processors.AbstractProcessor;
import igentuman.ncsteamadditions.processors.ProcessorsList;
import nc.init.NCBlocks;
import nc.recipe.vanilla.recipe.ShapedEnergyRecipe;
import nc.recipe.vanilla.recipe.ShapedFluidRecipe;
import nc.recipe.vanilla.recipe.ShapelessArmorRadShieldingRecipe;
import nc.recipe.vanilla.recipe.ShapelessFluidRecipe;
import nc.util.NCUtil;
import nc.util.StackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class NCSteamAdditionsCraftingRecipeHandler
{
	public static AbstractProcessor getProcessorObject(String name)
	{
		Class cTile = null;
		try {
			cTile = Class.forName(name);
		} catch (ClassNotFoundException e) {
			return null;
		}
		try {
			return (AbstractProcessor) cTile.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			return null;
		}
	}

	public static void registerCraftingRecipes() 
	{
		int i = 1;
		//crafting
		for (String processorName: ProcessorsList.processors) {
			AbstractProcessor processor = getProcessorObject(processorName);
			addShapedOreRecipe(Blocks.blocks[processor.GUID], processor);
		}

	}


	private static final Map<String, Integer> RECIPE_COUNT_MAP = new HashMap<String, Integer>();

	public static void addShapedOreRecipe(Object out, Object... inputs)
	{
		registerRecipe(ShapedOreRecipe.class, out, inputs);
	}

	public static void addShapedEnergyRecipe(Object out, Object... inputs)
	{
		registerRecipe(ShapedEnergyRecipe.class, out, inputs);
	}

	public static void addShapedFluidRecipe(Object out, Object... inputs)
	{
		registerRecipe(ShapedFluidRecipe.class, out, inputs);
	}

	public static void addShapelessOreRecipe(Object out, Object... inputs)
	{
		registerRecipe(ShapelessOreRecipe.class, out, inputs);
	}

	public static void addShapelessFluidRecipe(Object out, Object... inputs)
	{
		registerRecipe(ShapelessFluidRecipe.class, out, inputs);
	}

	public static void addShapelessArmorUpgradeRecipe(Object out, Object... inputs)
	{
		registerRecipe(ShapelessArmorRadShieldingRecipe.class, out, inputs);
	}

	public static void registerRecipe(Class<? extends IRecipe> clazz, Object out, Object... inputs)
	{
		if (out == null || Lists.newArrayList(inputs).contains(null))
			return;
		ItemStack outStack = StackHelper.fixItemStack(out);
		if (!outStack.isEmpty() && inputs != null)
		{
			String outName = outStack.getTranslationKey();
			if (RECIPE_COUNT_MAP.containsKey(outName))
			{
				int count = RECIPE_COUNT_MAP.get(outName);
				RECIPE_COUNT_MAP.put(outName, count + 1);
				outName = outName + "_" + count;
			}
			else
				RECIPE_COUNT_MAP.put(outName, 1);
			ResourceLocation location = new ResourceLocation(NCSteamAdditions.MOD_ID, outName);
			try
			{
				IRecipe recipe = NCUtil.newInstance(clazz, location, outStack, inputs);
				recipe.setRegistryName(location);
				ForgeRegistries.RECIPES.register(recipe);
			}
			catch (Exception e)
			{

			}
		}
	}
}