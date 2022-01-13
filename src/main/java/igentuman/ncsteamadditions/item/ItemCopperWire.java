package igentuman.ncsteamadditions.item;


import igentuman.ncsteamadditions.tab.NCSteamAdditionsTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemCopperWire extends Item {
    public ItemCopperWire() {
        super();
    }
    public static int regId = 2;
    public CreativeTabs getCreativeTab()
    {
        return NCSteamAdditionsTabs.ITEMS;
    }
}
