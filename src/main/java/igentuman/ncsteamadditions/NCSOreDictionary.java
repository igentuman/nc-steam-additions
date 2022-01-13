package igentuman.ncsteamadditions;

import igentuman.ncsteamadditions.item.ItemCompressedCoal;
import igentuman.ncsteamadditions.item.ItemCopperSheet;
import igentuman.ncsteamadditions.item.ItemCopperWire;
import igentuman.ncsteamadditions.item.Items;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class NCSOreDictionary {
    public static void register()
    {
        OreDictionary.registerOre("compressedCoal",new ItemStack(Items.items[ItemCompressedCoal.regId]));
        OreDictionary.registerOre("copperSheet",new ItemStack(Items.items[ItemCopperSheet.regId]));
        OreDictionary.registerOre("wireCopper",new ItemStack(Items.items[ItemCopperWire.regId]));
        OreDictionary.registerOre("blockSnow",new ItemStack(Blocks.SNOW));
    }
}
