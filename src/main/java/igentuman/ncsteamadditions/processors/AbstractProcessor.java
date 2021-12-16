package igentuman.ncsteamadditions.processors;

import igentuman.ncsteamadditions.enums.ProcessorType;
import igentuman.ncsteamadditions.tab.NCSteamAdditionsTabs;
import net.minecraft.creativetab.CreativeTabs;

public class AbstractProcessor {
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

    public static Object craftingRecipe;

    public static CreativeTabs getCreativeTab()
    {
        return NCSteamAdditionsTabs.ITEMS;
    }

    public static ProcessorType getType()
    {
        return new ProcessorType(code,GUID,particle1,particle2);
    }
}
