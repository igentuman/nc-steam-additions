package igentuman.ncsteamadditions.processors;

import igentuman.ncsteamadditions.gui.GUIHandler;
import igentuman.ncsteamadditions.jei.JEIHandler;
import igentuman.ncsteamadditions.tab.NCSteamAdditionsTabs;
import mezz.jei.api.IGuiHelper;
import nc.container.processor.ContainerItemFluidProcessor;
import nc.container.processor.ContainerMachineConfig;
import nc.integration.jei.JEIBasicCategory;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public abstract class AbstractProcessor {
    public static String code;

    public static String particle1;

    public static String particle2;

    public static int GUID;

    public static int SIDEID = 1000+ GUID;

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

}
