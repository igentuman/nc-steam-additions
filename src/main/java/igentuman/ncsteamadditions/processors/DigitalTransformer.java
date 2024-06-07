package igentuman.ncsteamadditions.processors;

import igentuman.ncsteamadditions.block.Blocks;
import igentuman.ncsteamadditions.config.*;
import igentuman.ncsteamadditions.jei.JEIHandler;
import igentuman.ncsteamadditions.jei.category.DigitalTransformerCategory;
import igentuman.ncsteamadditions.machine.container.ContainerDigitalTransformer;
import igentuman.ncsteamadditions.machine.gui.GuiDigitalTransformer;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import igentuman.ncsteamadditions.tile.TileDigitalTransformer;
import mezz.jei.api.IGuiHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.*;

public class DigitalTransformer extends AbstractProcessor {

    public DigitalTransformer()
    {
        code = "digital_transformer";
        particle1 = "endRod";
        particle2 = "reddust";
        GUID = 8;
        SIDEID = 1000 + GUID;
        inputItems = 4;
        inputFluids = 4;
        outputFluids = 1;
        outputItems = 1;
        craftingRecipe = new Object[] {};
    }

    public int getProcessPower() {
        return NCSteamAdditionsConfig.digitalTransformerRF;
    }

    public String getBlockType()
    {
        return "nc_processor";
    }
    @SideOnly(Side.CLIENT)
    public Object getLocalGuiContainer(EntityPlayer player, TileEntity tile) {
        return new GuiDigitalTransformer(player, (TileDigitalTransformer) tile, this);
    }
    public boolean isFullCube() {return false;}
    public Object getGuiContainer(EntityPlayer player, TileEntity tile) {
        return new ContainerDigitalTransformer(player, (TileDigitalTransformer) tile);
    }

    public Object getGuiContainerConfig(EntityPlayer player, TileEntity tile) {
       return new ContainerMachineConfig(player, (TileDigitalTransformer) tile);
    }
    @SideOnly(Side.CLIENT)
    public Object getLocalGuiContainerConfig(EntityPlayer player, TileEntity tile) {
        return new GuiDigitalTransformer.SideConfig(player, (TileDigitalTransformer) tile, this);
    }

    public Class getGuiClass()
    {
        return GuiDigitalTransformer.class;
    }

    public JEIHandler getRecipeHandler()
    {
        return this.recipeHandler;
    }

    public JEIBasicCategory getRecipeCategory(IGuiHelper guiHelper)
    {
        recipeHandler = new JEIHandler(this, NCSteamAdditionsRecipes.processorRecipeHandlers[getGuid()], Blocks.blocks[getGuid()], code, DigitalTransformerCategory.DigitalTransformerWrapper.class);
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

    public TileEntity getTile()
    {
        return new TileDigitalTransformer();
    }

    public Class getTileClass() {
        return TileDigitalTransformer.class;
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
