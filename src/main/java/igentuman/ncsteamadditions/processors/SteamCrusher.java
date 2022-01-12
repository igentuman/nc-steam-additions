package igentuman.ncsteamadditions.processors;

import igentuman.ncsteamadditions.block.Blocks;
import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import igentuman.ncsteamadditions.item.Items;
import igentuman.ncsteamadditions.jei.JEIHandler;
import igentuman.ncsteamadditions.jei.catergory.SteamCrusherCategory;
import igentuman.ncsteamadditions.machine.container.ContainerSteamCrusher;
import igentuman.ncsteamadditions.machine.gui.GuiSteamCrusher;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import mezz.jei.api.IGuiHelper;
import nc.container.processor.ContainerMachineConfig;
import nc.init.NCBlocks;
import nc.integration.jei.JEIBasicCategory;
import nc.tile.processor.TileItemFluidProcessor;
import nc.util.FluidStackHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class SteamCrusher extends AbstractProcessor {

    public static String code = "steam_crusher";

    public static String particle1 = "splash";

    public static String particle2 = "reddust";

    public final static int GUID = 1;

    public final static int SIDEID = 1000 + GUID;

    public static int inputItems = 1;

    public static int inputFluids = 1;

    public static int outputFluids = 0;

    public static int outputItems = 1;

    public static RecipeHandler recipes;

    public Object[] craftingRecipe = new Object[] {"BRB", "CFC", "PRP", 'B', net.minecraft.init.Items.BUCKET, 'F', "chest", 'C', Items.items[0], 'R', net.minecraft.init.Blocks.PISTON, 'P', net.minecraft.init.Items.DIAMOND_PICKAXE};

    public int getInputItems() {
        return inputItems;
    }

    public int getInputFluids() {
        return inputFluids;
    }

    public int getOutputFluids() {
        return outputFluids;
    }

    public String getBlockType()
    {
        return "nc_processor";
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
        return new GuiSteamCrusher(player,  (SteamCrusher.TileSteamCrusher)tile,this);
    }

    public Object getLocalGuiContainerConfig(EntityPlayer player, TileEntity tile) {
        return new GuiSteamCrusher.SideConfig(player,  (SteamCrusher.TileSteamCrusher)tile,this);
    }

    public Object getGuiContainer(EntityPlayer player, TileEntity tile) {
        return new ContainerSteamCrusher(player,  (SteamCrusher.TileSteamCrusher)tile);
    }

    public Object getGuiContainerConfig(EntityPlayer player, TileEntity tile) {
        return new ContainerMachineConfig(player,  (SteamCrusher.TileSteamCrusher)tile);
    }

    public JEIHandler getRecipeHandler()
    {
        return this.recipeHandler;
    }

    public JEIBasicCategory getRecipeCategory(IGuiHelper guiHelper)
    {
        recipeHandler = new JEIHandler(this, NCSteamAdditionsRecipes.processorRecipeHandlers[GUID], Blocks.blocks[SteamCrusher.GUID], SteamCrusher.code, SteamCrusherCategory.SteamCrusherWrapper.class);
        return new SteamCrusherCategory(guiHelper,recipeHandler, this);
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
        return TileSteamCrusher.class;
    }

    public static class TileSteamCrusher extends TileItemFluidProcessor
    {
        public TileSteamCrusher()
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
                    GUID+1, 0
            );
        }
    }

    public SteamCrusher.RecipeHandler getRecipes()
    {
        return new SteamCrusher.RecipeHandler();
    }


    public class RecipeHandler extends AbstractProcessor.RecipeHandler {
        public RecipeHandler()
        {
            super(code, inputItems, inputFluids, outputItems, outputFluids);
        }

        @Override
        public void addRecipes()
        {
            addRecipe("oreIron",
                    fluidStack("steam", FluidStackHelper.INGOT_VOLUME),
                    oreStack("dustIron", 1)
            );
        }
    }
}
