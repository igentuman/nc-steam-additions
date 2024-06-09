package igentuman.ncsteamadditions.processors;

import com.google.common.collect.Lists;
import igentuman.ncsteamadditions.jei.JEIHandler;
import igentuman.ncsteamadditions.recipes.ProcessorRecipeHandler;
import igentuman.ncsteamadditions.tab.NCSteamAdditionsTabs;
import igentuman.ncsteamadditions.tile.TileNCSProcessor;
import igentuman.ncsteamadditions.util.Util;
import mezz.jei.api.IGuiHelper;
import nc.recipe.ingredient.FluidIngredient;
import nc.util.*;
import nclegacy.jei.JEIBasicCategoryLegacy;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.util.*;

import static nc.block.property.BlockProperties.FACING_HORIZONTAL;

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

    public Class getGuiClass()
    {
        return null;
    }
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

    public abstract JEIBasicCategoryLegacy getRecipeCategory(IGuiHelper guiHelper);

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

    public void spawnParticles(BlockPos pos, Random rand, World world, IBlockState state)
    {
        Util.spawnParticleOnProcessor(state, world, pos, rand, state.getValue(FACING_HORIZONTAL),
                getType().getParticle1());
        Util.spawnParticleOnProcessor(state, world, pos, rand, state.getValue(FACING_HORIZONTAL),
                getType().getParticle2());
    }

    public String getSound()
    {
        return "";
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

    public boolean isFullCube() {return true;}
    public AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    public boolean isCustomModel() {return false;}
    public AxisAlignedBB getAABB()
    {
        return AABB;
    }

    public int getProcessPower() {
        return 0;
    }
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
