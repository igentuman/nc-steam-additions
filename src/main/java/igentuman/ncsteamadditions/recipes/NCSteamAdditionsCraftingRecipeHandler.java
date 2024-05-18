package igentuman.ncsteamadditions.recipes;

import com.google.common.collect.Lists;
import igentuman.ncsteamadditions.NCSteamAdditions;
import igentuman.ncsteamadditions.block.Blocks;
import igentuman.ncsteamadditions.block.MetaEnums.IngotType;
import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import igentuman.ncsteamadditions.item.Items;
import igentuman.ncsteamadditions.processors.*;
import nc.ModCheck;
import nc.config.NCConfig;
import nc.init.*;
import nc.radiation.RadSources;
import nc.recipe.NCRecipes;
import nc.recipe.ingredient.OreIngredient;
import nc.recipe.ingredient.*;
import nc.recipe.vanilla.recipe.*;
import nc.util.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.*;
import net.minecraftforge.oredict.*;
import net.minecraftforge.registries.ForgeRegistry;
import vazkii.patchouli.common.item.ItemModBook;

import java.util.*;

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

		addShapedOreRecipe(new ItemStack(Blocks.ingot_block,1, IngotType.ZINC.getID()),new Object[]{"SSS", "SSS", "SSS", 'S', "ingotZinc"});
		addShapelessOreRecipe(new ItemStack(Items.ingot,9, IngotType.ZINC.getID()),new Object[]{"S", 'S', "blockZinc"});

		for (int i = 0; i < IngotType.values().length; i++)
		{
			String type = StringHelper.capitalize( IngotType.values()[i].getName());
            for (ItemStack dust : OreDictionary.getOres("dust" + type)) {
				GameRegistry.addSmelting(dust, OreDictHelper.getPrioritisedCraftingStack(new ItemStack(Items.ingot, 1, i), "ingot" + type), 0F);
			}
			for (ItemStack ore : OreDictionary.getOres("ore" + type)) {
				GameRegistry.addSmelting(ore, OreDictHelper.getPrioritisedCraftingStack(new ItemStack(Items.ingot, 1, i), "ingot" + type), 0F);
			}
		}

		if (ModCheck.patchouliLoaded())
		{
			addShapelessOreRecipe(ItemModBook.forBook("ncsteamaddtions:guide"), new Object[] {net.minecraft.init.Items.BOOK, "coal"});
			addShapelessOreRecipe(net.minecraft.init.Items.BOOK, new Object[] {ItemModBook.forBook("ncsteamaddtions:guide")});
		}

		if(NCSteamAdditionsConfig.overrideNcRecipes) {
			removeRecipeFor(new ItemStack(NCItems.part, 1, 4).getItem(), 4);
			removeRecipeFor(new ItemStack(NCBlocks.water_source, 1).getItem(), 0);
			removeRecipeFor(new ItemStack(NCItems.part, 2).getItem(), 0);
			removeRecipeFor(new ItemStack(NCBlocks.voltaic_pile_basic, 1).getItem(), 0);
			removeRecipeFor(new ItemStack(NCBlocks.turbine_controller, 1).getItem(), 0);
			addShapedOreRecipe(new ItemStack(NCItems.part, 2, 0), " L ", "GZG", " L ", 'L', "ingotLead", 'G', "dustGraphite",'Z',"ingotZinc");
			addShapedOreRecipe(new ItemStack(NCItems.part, 2, 4), "CC", "II", "CC", 'C', "wireCopper", 'I', "ingotIron");
			addShapedOreRecipe(new ItemStack(NCBlocks.turbine_controller, 1, 0), "THT", "CSC", "THT", 'C', net.minecraft.init.Items.COMPARATOR, 'H', "ingotHSLASteel", 'T', "ingotTough", 'S', Blocks.blocks[ProcessorsRegistry.get().STEAM_TURBINE.GUID]);
		}

		NCRecipes.infuser.addOxidizingRecipe("dustUranium",1000);
		NCRecipes.chemical_reactor.addRecipe(new Object[] {fluidStack("uranium_oxide",1000),fluidStack("hydrofluoric_acid",500),fluidStack("uranium_hexafluoride",1000), new EmptyFluidIngredient()});
		NCRecipes.centrifuge.addRecipe(new Object[]{fluidStack("uranium_hexafluoride",1000),fluidStack("uranium_235",144),fluidStack("uranium_238",144*4),fluidStack("uranium_233",72),new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient()});
		NCRecipes.assembler.addRecipe(new Object[]{NCBlocks.assembler, Blocks.blocks[ProcessorsRegistry.get().STEAM_TRANSFORMER.GUID], Blocks.blocks[ProcessorsRegistry.get().STEAM_FLUID_TRANSFORMER.GUID], "coreOfTransformation", Blocks.blocks[ProcessorsRegistry.get().DIGITAL_TRANSFORMER.GUID], 2.0D, 2.0D});
		NCRecipes.fission_irradiator.addRecipe(new Object[]{net.minecraft.init.Items.ENDER_EYE, Items.items[3],2720000, NCConfig.fission_irradiator_heat_per_flux[1], NCConfig.fission_irradiator_efficiency[1], RadSources.TBP});
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
				IRecipe recipe = ReflectionHelper.newInstance(clazz, location, outStack, inputs);
				recipe.setRegistryName(location);
				ForgeRegistries.RECIPES.register(recipe);
			}
			catch (Exception e)
			{

			}
		}
	}
}
