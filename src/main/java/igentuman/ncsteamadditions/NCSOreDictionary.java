package igentuman.ncsteamadditions;

import igentuman.ncsteamadditions.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class NCSOreDictionary {
    public static void register()
    {
        OreDictionary.registerOre("compressedCoal",new ItemStack(Items.items[1]));
    }
}
