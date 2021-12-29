package igentuman.ncsteamadditions.processors;

import igentuman.ncsteamadditions.enums.ProcessorType;
import igentuman.ncsteamadditions.jei.JEIHandler;
import igentuman.ncsteamadditions.tab.NCSteamAdditionsTabs;
import mezz.jei.api.IGuiHelper;
import nc.integration.jei.JEIBasicCategory;
import net.minecraft.creativetab.CreativeTabs;
import igentuman.ncsteamadditions.machine.tile.TileNCSteamAdditionsProcessor;

public abstract class AbstractProcessor {
    public static String code;

    public static String PORCESSOR_ENUM;

    public static String particle1;

    public static String particle2;

    public static int GUID;

    public static int SIDEID = 1000+ GUID;

    public static int inputItems;

    public static int inputFluids;

    public static int outputFluids;

    public static int outputItems;

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

}
