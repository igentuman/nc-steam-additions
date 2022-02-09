package igentuman.ncsteamadditions.processors;

import igentuman.ncsteamadditions.block.Blocks;
import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import igentuman.ncsteamadditions.config.TransformerRecipesConfig;
import igentuman.ncsteamadditions.item.Items;
import igentuman.ncsteamadditions.jei.JEIHandler;
import igentuman.ncsteamadditions.jei.catergory.SteamFluidTransformerCategory;
import igentuman.ncsteamadditions.machine.container.ContainerSteamFluidTransformer;
import igentuman.ncsteamadditions.machine.gui.GuiSteamFluidTransformer;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import igentuman.ncsteamadditions.tile.TileNCSProcessor;
import mezz.jei.api.IGuiHelper;
import nc.container.processor.ContainerMachineConfig;
import nc.integration.jei.JEIBasicCategory;
import nc.tile.processor.TileItemFluidProcessor;
import nc.util.RegistryHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class SteamFluidTransformer extends AbstractProcessor {

    public static String code = "steam_fluid_transformer";

    public static String particle1 = "splash";

    public static String particle2 = "spell";

    public final static int GUID = 3;

    public final static int SIDEID = 1000 + GUID;

    public static int inputItems = 0;

    public static int inputFluids = 4;

    public static int outputFluids = 1;

    public static int outputItems = 0;

    public static RecipeHandler recipes;

    public Object[] craftingRecipe = new Object[] {"PRP", "CFC", "PHP", 'P', net.minecraft.init.Items.BUCKET, 'F', net.minecraft.init.Items.ENDER_EYE, 'C', Items.items[0], 'R', RegistryHelper.itemStackFromRegistry("minecraft:brewing_stand"), 'H', Items.items[1]};

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

    public String getBlockType()
    {
        return "nc_processor";
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
        return new GuiSteamFluidTransformer(player, (SteamFluidTransformer.TileSteamFluidTransformer) tile, this);
    }


    public Object getLocalGuiContainerConfig(EntityPlayer player, TileEntity tile) {
        return new GuiSteamFluidTransformer.SideConfig(player, (SteamFluidTransformer.TileSteamFluidTransformer) tile, this);
    }

    public Object getGuiContainer(EntityPlayer player, TileEntity tile) {
        return new ContainerSteamFluidTransformer(player, (SteamFluidTransformer.TileSteamFluidTransformer) tile);
    }


    public Object getGuiContainerConfig(EntityPlayer player, TileEntity tile) {
        return new ContainerMachineConfig(player, (SteamFluidTransformer.TileSteamFluidTransformer) tile);
    }

    public JEIHandler getRecipeHandler()
    {
        return this.recipeHandler;
    }

    public JEIBasicCategory getRecipeCategory(IGuiHelper guiHelper)
    {
        recipeHandler = new JEIHandler(this,
                NCSteamAdditionsRecipes.processorRecipeHandlers[GUID],
                Blocks.blocks[SteamFluidTransformer.GUID],
                SteamFluidTransformer.code,
                SteamFluidTransformerCategory.SteamFluidTransformerWrapper.class);
        return new SteamFluidTransformerCategory(guiHelper,recipeHandler, this);
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
        return TileSteamFluidTransformer.class;
    }

    public static class TileSteamFluidTransformer extends TileNCSProcessor
    {
        public TileSteamFluidTransformer()
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

    public SteamFluidTransformer.RecipeHandler getRecipes()
    {
        return new SteamFluidTransformer.RecipeHandler();
    }


    public class RecipeHandler extends AbstractProcessor.RecipeHandler {
        public RecipeHandler()
        {
            super(code, inputItems, inputFluids, outputItems, outputFluids);
        }

        @Override
        public void addRecipes()
        {
            for(int i = 0; i < TransformerRecipesConfig.fluidTransformerRecipes.length; i++) {
                Object[] recipe = TransformerRecipesConfig.parseFluidTransformerRecipe(TransformerRecipesConfig.fluidTransformerRecipes[i]);
                if(recipe != null) {
                    addRecipe(recipe);
                }
            }
        }
    }
}
