package igentuman.ncsteamadditions.processors;

import igentuman.ncsteamadditions.block.Blocks;
import igentuman.ncsteamadditions.config.TransformerRecipesConfig;
import igentuman.ncsteamadditions.jei.JEIHandler;
import igentuman.ncsteamadditions.jei.catergory.DigitalTransformerCategory;
import igentuman.ncsteamadditions.machine.container.ContainerDigitalTransformer;
import igentuman.ncsteamadditions.machine.gui.GuiDigitalTransformer;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import igentuman.ncsteamadditions.tile.TDigitalTransformer;
import mezz.jei.api.IGuiHelper;
import nc.container.processor.ContainerMachineConfig;
import nc.integration.jei.JEIBasicCategory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class DigitalTransformer extends AbstractProcessor {

    public static String code = "digital_transformer";

    public static String particle1 = "endRod";

    public static String particle2 = "reddust";

    public final static int GUID = 8;

    public final static int SIDEID = 1000 + GUID;

    public static int inputItems = 4;

    public static int inputFluids = 4;

    public static int outputFluids = 1;

    public static int outputItems = 1;

    public static RecipeHandler recipes;

    public Object[] craftingRecipe = new Object[] {};

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

    public Object getLocalGuiContainer(EntityPlayer player, TileEntity tile) {
        return new GuiDigitalTransformer(player, (DigitalTransformer.TileDigitalTransformer) tile, this);
    }

    public Object getGuiContainer(EntityPlayer player, TileEntity tile) {
        return new ContainerDigitalTransformer(player, (DigitalTransformer.TileDigitalTransformer) tile);
    }


    public Object getGuiContainerConfig(EntityPlayer player, TileEntity tile) {
       return new ContainerMachineConfig(player, (DigitalTransformer.TileDigitalTransformer) tile);
    }

    public Object getLocalGuiContainerConfig(EntityPlayer player, TileEntity tile) {
        return new GuiDigitalTransformer.SideConfig(player, (DigitalTransformer.TileDigitalTransformer) tile, this);
    }

    public JEIHandler getRecipeHandler()
    {
        return this.recipeHandler;
    }

    public JEIBasicCategory getRecipeCategory(IGuiHelper guiHelper)
    {
        recipeHandler = new JEIHandler(this, NCSteamAdditionsRecipes.processorRecipeHandlers[getGuid()], Blocks.blocks[getGuid()], DigitalTransformer.code, DigitalTransformerCategory.DigitalTransformerWrapper.class);
        return new DigitalTransformerCategory(guiHelper,recipeHandler, this);
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
        return TileDigitalTransformer.class;
    }

    public static class TileDigitalTransformer extends TDigitalTransformer
    {

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
            for(int i = 0; i < TransformerRecipesConfig.digitalTransformerRecipes.length; i++) {
                Object[] recipe = TransformerRecipesConfig.parseDigitalTransformerRecipe(TransformerRecipesConfig.digitalTransformerRecipes[i]);
                if(recipe != null) {
                    addRecipe(recipe);
                }
            }
        }
    }
}
