package igentuman.ncsteamadditions.item;

import igentuman.ncsteamadditions.tab.NCSteamAdditionsTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemCompressedCoal extends Item {
    public ItemCompressedCoal() {
        super();
    }
    public static int regId = 1;
    public CreativeTabs getCreativeTab()
    {
        return NCSteamAdditionsTabs.ITEMS;
    }
}
