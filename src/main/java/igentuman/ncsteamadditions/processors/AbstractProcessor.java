package igentuman.ncsteamadditions.processors;

import com.google.common.collect.Lists;
import igentuman.ncsteamadditions.jei.JEIHandler;
import igentuman.ncsteamadditions.recipes.ProcessorRecipeHandler;
import igentuman.ncsteamadditions.tab.NCSteamAdditionsTabs;
import igentuman.ncsteamadditions.tile.NCSteamAdditionsTiles;
import igentuman.ncsteamadditions.tile.TileNCSProcessor;
import mezz.jei.api.IGuiHelper;
import nc.integration.jei.JEIBasicCategory;
import nc.recipe.ingredient.FluidIngredient;
import nc.util.FluidRegHelper;
import nc.util.FluidStackHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;

public abstract class AbstractProcessor {

    public String code;

    public String particle1;

    public String particle2;

    public int GUID;

    public int SIDEID = 1000 + GUID;

    public int inputItems;

    public int inputFluids;

    public int outputFluids;

    public int outputItems;

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

    public Object[] craftingRecipe;

    public JEIHandler recipeHandler;

    public Object[] getCraftingRecipe()
    {
        return this.craftingRecipe;
    }

    public JEIHandler getRecipeHandler()
    {
        return this.recipeHandler;
    }

    public abstract JEIBasicCategory getRecipeCategory(IGuiHelper guiHelper);

    public CreativeTabs getCreativeTab()
    {
        return NCSteamAdditionsTabs.ITEMS;
    }

    protected ProcessorType type;

    public abstract ProcessorType getType();

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

    public Class getTileClass() {
        return TileNCSProcessor.class;
    }

    public abstract Object getGuiContainer(EntityPlayer player, TileEntity tile);

    public abstract Object getGuiContainerConfig(EntityPlayer player, TileEntity tile);

    public abstract Object getLocalGuiContainerConfig(EntityPlayer player, TileEntity tile);

    public abstract Object getLocalGuiContainer(EntityPlayer player, TileEntity tile);

    public abstract RecipeHandler getRecipes();
    public abstract String getBlockType();

    public abstract TileEntity getTile();

    public abstract class RecipeHandler extends ProcessorRecipeHandler {
        public int bucket() {
            return FluidStackHelper.BUCKET_VOLUME;
        }
        public int ingot()
        {
            return FluidStackHelper.INGOT_VOLUME;
        }
        public ArrayList steam;

        public ArrayList getSteamIngredient()
        {
            if(steam == null) {
                steam = Lists.newArrayList(
                        new FluidIngredient[]{fluidStack("steam", bucket())}
                );
                if(FluidRegHelper.fluidExists("ic2steam")) {
                    steam.add(fluidStack("ic2steam", bucket()));
                }
            }
            return steam;
        }

        public RecipeHandler() {
            super(code, inputItems, inputFluids, outputItems, outputFluids);
        }

        public RecipeHandler(String code, int inputItems, int inputFluids, int outputItems, int outputFluids) {
            super(code, inputItems, inputFluids, outputItems, outputFluids);
        }

        public abstract void addRecipes();
    };

}
