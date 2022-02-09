package igentuman.ncsteamadditions.processors;

import com.google.common.collect.Sets;
import igentuman.ncsteamadditions.block.Blocks;
import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import igentuman.ncsteamadditions.item.Items;
import igentuman.ncsteamadditions.jei.JEIHandler;
import igentuman.ncsteamadditions.jei.catergory.SteamCompactorCategory;
import igentuman.ncsteamadditions.machine.container.ContainerSteamCompactor;
import igentuman.ncsteamadditions.machine.gui.GuiSteamCompactor;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import igentuman.ncsteamadditions.tile.TileNCSProcessor;
import mezz.jei.api.IGuiHelper;
import nc.container.processor.ContainerMachineConfig;
import nc.integration.jei.JEIBasicCategory;
import nc.recipe.ingredient.FluidIngredient;
import nc.tile.processor.TileItemFluidProcessor;
import nc.util.OreDictHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Set;

public class SteamCompactor extends AbstractProcessor {

    public static String code = "steam_compactor";

    public static String particle1 = "splash";

    public static String particle2 = "reddust";

    public final static int GUID = 4;

    public final static int SIDEID = 1000 + GUID;

    public static int inputItems = 1;

    public static int inputFluids = 1;

    public static int outputFluids = 0;

    public static int outputItems = 1;

    public static RecipeHandler recipes;

    public Object[] craftingRecipe = new Object[] {"BRB", "RFR", "PRP", 'B', net.minecraft.init.Items.BUCKET, 'F', "chest", 'R', net.minecraft.init.Blocks.PISTON, 'P', Items.items[0]};

    public int getInputItems() {
        return inputItems;
    }

    public String getBlockType()
    {
        return "nc_processor";
    }

    public int getInputFluids() {
        return inputFluids;
    }

    public int getOutputFluids() {
        return outputFluids;
    }

    public int getOutputItems() {
        return outputItems;
    }

    public Object[] getCraftingRecipe()
    {
       return this.craftingRecipe;
    }

    public JEIHandler recipeHandler;

    public int getGuid()
    {
        return GUID;
    }

    public int getSideid()
    {
        return SIDEID;
    }

    public String getCode()
    {
        return code;
    }

    public Object getLocalGuiContainer(EntityPlayer player, TileEntity tile) {
        return new GuiSteamCompactor(player, (SteamCompactor.TileSteamCompactor) tile, this);
    }


    public Object getLocalGuiContainerConfig(EntityPlayer player, TileEntity tile) {
       return new GuiSteamCompactor.SideConfig(player, (SteamCompactor.TileSteamCompactor) tile, this);
    }

    public Object getGuiContainer(EntityPlayer player, TileEntity tile) {
        return new ContainerSteamCompactor(player, (SteamCompactor.TileSteamCompactor) tile);
    }


    public Object getGuiContainerConfig(EntityPlayer player, TileEntity tile) {
        return new ContainerMachineConfig(player, (SteamCompactor.TileSteamCompactor) tile);
    }

    public JEIHandler getRecipeHandler()
    {
        return this.recipeHandler;
    }

    public JEIBasicCategory getRecipeCategory(IGuiHelper guiHelper)
    {
        recipeHandler = new JEIHandler(this, NCSteamAdditionsRecipes.processorRecipeHandlers[GUID], Blocks.blocks[SteamCompactor.GUID], SteamCompactor.code, SteamCompactorCategory.SteamCompactorWrapper.class);
        return new SteamCompactorCategory(guiHelper,recipeHandler, this);
    }

    public ProcessorType getType()
    {
        if(type == null) {
            type = new ProcessorType(code,GUID,particle1,particle2);
            type.setProcessor(this);
        }
        return type;
    }

    public Class getTileClass()
    {
        return TileSteamCompactor.class;
    }

    public static class TileSteamCompactor extends TileNCSProcessor
    {
        public TileSteamCompactor()
        {
            super(
                    code,
                    inputItems,
                    inputFluids,
                    outputItems,
                    outputFluids,
                    defaultItemSorptions(inputItems, outputItems, true),
                    defaultTankCapacities(5000, inputFluids, outputFluids),
                    defaultTankSorptions(inputFluids, outputFluids),
                    NCSteamAdditionsRecipes.validFluids[GUID],
                    NCSteamAdditionsConfig.processor_time[GUID],
                    0, true,
                    NCSteamAdditionsRecipes.processorRecipeHandlers[GUID],
                    GUID+1, 0,0,10
            );
        }
    }

    public SteamCompactor.RecipeHandler getRecipes()
    {
        return new SteamCompactor.RecipeHandler();
    }


    public class RecipeHandler extends AbstractProcessor.RecipeHandler {
        public RecipeHandler()
        {
            super(code, inputItems, inputFluids, outputItems, outputFluids);
        }
        @Override
        public void addRecipes()
        {
            addRecipe(new Object[]{
                    oreStack("coal", 4),
                    fluidStack("low_pressure_steam", 250),
                    oreStack("compressedCoal", 1)}
            );
            addRecipe(new Object[]{
                    oreStack("copperSheet", 1),
                    fluidStack("low_pressure_steam", 250),
                    oreStack("wireCopper", 1)}
            );
            addPlatePressingRecipes();
        }

        public void addPlatePressingRecipes() {
            String[] var1 = OreDictionary.getOreNames();
            int var2 = var1.length;
            FluidIngredient steam = fluidStack("low_pressure_steam", 250);
            Set<String> PLATE_BLACKLIST = Sets.newHashSet(new String[]{"Graphite"});
            for(int var3 = 0; var3 < var2; ++var3) {
                String ore = var1[var3];
                String plate;
                if (ore.startsWith("plate")) {
                    plate = ore.substring(5);
                    if (PLATE_BLACKLIST.contains(plate)) {
                        continue;
                    }

                    String ingot = "ingot" + plate;
                    String gem = "gem" + plate;
                    if (OreDictHelper.oreExists(ingot)) {
                        this.addRecipe(new Object[]{ingot, steam, ore, 1.0D, 1.0D});
                    } else if (OreDictHelper.oreExists(gem)) {
                        this.addRecipe(new Object[]{gem,steam, ore, 1.0D, 1.0D});
                    }
                }

                if (ore.startsWith("plateDense")) {
                    plate = "plate" + ore.substring(10);
                    if (OreDictHelper.oreExists(plate)) {
                        this.addRecipe(new Object[]{oreStack(plate, 9), steam, ore, 2.0D, 2.0D});
                    }
                }
            }

        }
    }
}
