package igentuman.ncsteamadditions.processors;

import igentuman.ncsteamadditions.block.Blocks;
import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import igentuman.ncsteamadditions.config.TransformerRecipesConfig;
import igentuman.ncsteamadditions.item.Items;
import igentuman.ncsteamadditions.jei.JEIHandler;
import igentuman.ncsteamadditions.jei.catergory.SteamTransformerCategory;
import igentuman.ncsteamadditions.machine.container.ContainerSteamTransformer;
import igentuman.ncsteamadditions.machine.gui.GuiSteamTransformer;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import igentuman.ncsteamadditions.tile.TileNCSProcessor;
import mezz.jei.api.IGuiHelper;
import nc.container.processor.ContainerMachineConfig;
import nc.integration.jei.JEIBasicCategory;
import nc.tile.processor.TileItemFluidProcessor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class SteamTransformer extends AbstractProcessor {

    public static String code = "steam_transformer";

    public static String particle1 = "splash";

    public static String particle2 = "reddust";

    public final static int GUID = 0;

    public final static int SIDEID = 1000 + GUID;

    public static int inputItems = 4;

    public static int inputFluids = 1;

    public static int outputFluids = 0;

    public static int outputItems = 1;

    public static RecipeHandler recipes;

    public Object[] craftingRecipe = new Object[] {"PRP", "CFC", "PHP", 'P', "chest", 'F', net.minecraft.init.Items.BUCKET, 'C', Items.items[0], 'R', net.minecraft.init.Items.ENDER_EYE, 'H', Items.items[1]};

    public int getInputItems() {
        return inputItems;
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

    public String getBlockType()
    {
        return "nc_processor";
    }

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

    GuiSteamTransformer guiSteamTransformer;
    GuiSteamTransformer.SideConfig sideConfig;
    Object containerMachineConfig;

    public Object getLocalGuiContainer(EntityPlayer player, TileEntity tile) {
        if(guiSteamTransformer == null) {
            guiSteamTransformer = new GuiSteamTransformer(player, (SteamTransformer.TileSteamTransformer) tile, this);
        }
        return guiSteamTransformer;
    }


    public Object getLocalGuiContainerConfig(EntityPlayer player, TileEntity tile) {
        if(sideConfig == null) {
            sideConfig = new GuiSteamTransformer.SideConfig(player, (SteamTransformer.TileSteamTransformer) tile, this);
        }
        return sideConfig;
    }

    public Object getGuiContainer(EntityPlayer player, TileEntity tile) {
        return new ContainerSteamTransformer(player, (SteamTransformer.TileSteamTransformer) tile);
    }


    public Object getGuiContainerConfig(EntityPlayer player, TileEntity tile) {
        if(containerMachineConfig == null) {
            containerMachineConfig = new ContainerMachineConfig(player, (SteamTransformer.TileSteamTransformer) tile);
        }
        return containerMachineConfig;
    }

    public JEIHandler getRecipeHandler()
    {
        return this.recipeHandler;
    }

    public JEIBasicCategory getRecipeCategory(IGuiHelper guiHelper)
    {
        recipeHandler = new JEIHandler(this, NCSteamAdditionsRecipes.processorRecipeHandlers[getGuid()], Blocks.blocks[getGuid()], SteamTransformer.code, SteamTransformerCategory.SteamTransformerWrapper.class);
        return new SteamTransformerCategory(guiHelper,recipeHandler, this);
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
        return TileSteamTransformer.class;
    }

    public static class TileSteamTransformer extends TileNCSProcessor
    {
        public TileSteamTransformer()
        {
            super(
                    code,
                    inputItems,
                    inputFluids,
                    outputItems,
                    outputFluids,
                    defaultItemSorptions(inputItems, outputItems, true),
                    defaultTankCapacities(10000, inputFluids, outputFluids),
                    defaultTankSorptions(inputFluids, outputFluids),
                    NCSteamAdditionsRecipes.validFluids[GUID],
                    NCSteamAdditionsConfig.processor_time[GUID],
                    0, true,
                    NCSteamAdditionsRecipes.processorRecipeHandlers[GUID],
                    GUID+1, 0,0,10
            );
        }
    }

    public RecipeHandler getRecipes()
    {
        return new RecipeHandler();
    }


    public class RecipeHandler extends AbstractProcessor.RecipeHandler {
        public RecipeHandler()
        {
            super(code, inputItems, inputFluids, outputItems, outputFluids);
        }

        @Override
        public void addRecipes()
        {
            for(int i = 0; i < TransformerRecipesConfig.transformerRecipes.length; i++) {
                Object[] recipe = TransformerRecipesConfig.parseTransformerRecipe(TransformerRecipesConfig.transformerRecipes[i]);
                if(recipe != null) {
                    addRecipe(recipe);
                }
            }
        }
    }
}
