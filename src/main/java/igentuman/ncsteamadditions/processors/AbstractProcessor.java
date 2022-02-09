package igentuman.ncsteamadditions.processors;

import com.google.common.collect.Lists;
import igentuman.ncsteamadditions.jei.JEIHandler;
import igentuman.ncsteamadditions.recipes.ProcessorRecipeHandler;
import igentuman.ncsteamadditions.tab.NCSteamAdditionsTabs;
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
    public static String code;

    public static String particle1;

    public static String particle2;

    public static int GUID;

    public static int SIDEID = 1000 + GUID;

    public static int inputItems;

    public static int inputFluids;

    public static int outputFluids;

    public static int outputItems;

    public abstract int getInputItems();

    public abstract int getInputFluids();

    public abstract int getOutputFluids();

    public abstract int getOutputItems();

    public static Object[] craftingRecipe;

    public JEIHandler recipeHandler;

    public abstract Object[] getCraftingRecipe();

    public abstract JEIHandler getRecipeHandler();

    public abstract JEIBasicCategory getRecipeCategory(IGuiHelper guiHelper);

    public CreativeTabs getCreativeTab()
    {
        return NCSteamAdditionsTabs.ITEMS;
    }

    protected ProcessorType type;

    public abstract ProcessorType getType();

    public abstract Class getTileClass();

    public abstract String getCode();

    public abstract int getGuid();

    public abstract int getSideid();

    public abstract Object getGuiContainer(EntityPlayer player, TileEntity tile);

    public abstract Object getGuiContainerConfig(EntityPlayer player, TileEntity tile);

    public abstract Object getLocalGuiContainerConfig(EntityPlayer player, TileEntity tile);

    public abstract Object getLocalGuiContainer(EntityPlayer player, TileEntity tile);

    public abstract RecipeHandler getRecipes();
    public abstract String getBlockType();
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
