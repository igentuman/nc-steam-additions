package igentuman.ncsteamadditions.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.google.common.collect.Lists;
import igentuman.ncsteamadditions.NCSteamAdditions;
import igentuman.ncsteamadditions.block.Blocks;
import igentuman.ncsteamadditions.item.Items;
import igentuman.ncsteamadditions.processors.*;
import nc.config.NCConfig;
import nc.init.NCBlocks;
import nc.init.NCItems;
import nc.radiation.RadSources;
import nc.recipe.NCRecipes;
import nc.recipe.ingredient.EmptyFluidIngredient;
import nc.recipe.ingredient.FluidIngredient;
import nc.recipe.ingredient.OreIngredient;
import nc.recipe.vanilla.recipe.ShapedEnergyRecipe;
import nc.recipe.vanilla.recipe.ShapedFluidRecipe;
import nc.recipe.vanilla.recipe.ShapelessArmorRadShieldingRecipe;
import nc.recipe.vanilla.recipe.ShapelessFluidRecipe;
import nc.util.FluidRegHelper;
import nc.util.NCUtil;
import nc.util.OreDictHelper;
import nc.util.StackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.registries.ForgeRegistry;

public class NCSteamAdditionsCraftingRecipeHandler
{

	public static void registerCraftingRecipes() 
	{
		//crafting
		for (AbstractProcessor processor: ProcessorsRegistry.get().processors()) {
			addShapedOreRecipe(Blocks.blocks[processor.getGuid()], processor.getCraftingRecipe());
		}
		addShapedOreRecipe(new ItemStack(Blocks.otherBlocks[0],4),new Object[]{"SSS", "S S", "SSS", 'S', "copperSheet"});
		addShapedOreRecipe(new ItemStack(Items.items[0],2),new Object[]{"   ", " SS", " SS", 'S', "ingotCopper"});
		removeRecipeFor(new ItemStack(NCItems.part, 1, 4).getItem(),4);
		removeRecipeFor(new ItemStack(NCBlocks.water_source, 1).getItem(),0);
		addShapedOreRecipe(new ItemStack(NCItems.part, 2, 4), "CC", "II", "CC", 'C', "wireCopper", 'I', "ingotIron");
		NCRecipes.infuser.addOxidizingRecipe("dustUranium",1000);
		NCRecipes.chemical_reactor.addRecipe(new Object[] {fluidStack("uranium_oxide",1000),fluidStack("hydrofluoric_acid",500),fluidStack("uranium_hexafluoride",1000), new EmptyFluidIngredient()});
		NCRecipes.centrifuge.addRecipe(new Object[]{fluidStack("uranium_hexafluoride",1000),fluidStack("uranium_235",144),fluidStack("uranium_238",144*4),fluidStack("uranium_233",72),new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient()});
		NCRecipes.assembler.addRecipe(new Object[]{NCBlocks.assembler, Blocks.blocks[SteamTransformer.GUID], Blocks.blocks[SteamFluidTransformer.GUID], "coreOfTransformation", Blocks.blocks[DigitalTransformer.GUID], 2.0D, 2.0D});
		NCRecipes.fission_irradiator.addRecipe(new Object[]{"gemEnderEye","coreOfTransformation",2720000, NCConfig.fission_irradiator_heat_per_flux[1], NCConfig.fission_irradiator_efficiency[1], RadSources.TBP});
	}

	public static OreIngredient oreStack(String oreType, int stackSize) {
		return !OreDictHelper.oreExists(oreType) ? null : new OreIngredient(oreType, stackSize);
	}

	public static FluidIngredient fluidStack(String fluidName, int stackSize) {
		return !FluidRegHelper.fluidExists(fluidName) ? null : new FluidIngredient(fluidName, stackSize);
	}

	public static void removeRecipeFor(Item item, int meta)
	{
		ForgeRegistry<IRecipe> recipeRegistry = (ForgeRegistry<IRecipe>)ForgeRegistries.RECIPES;
		ArrayList<IRecipe> recipes = Lists.newArrayList(recipeRegistry.getValues());

		for (IRecipe r : recipes)
		{
			ItemStack output = r.getRecipeOutput();
			if (output.getItem() == item && output.getItem().getMetadata(output) == meta)
			{
				recipeRegistry.remove(r.getRegistryName());
			}
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