package igentuman.ncsteamadditions;

import igentuman.ncsteamadditions.block.MetaEnums;
import igentuman.ncsteamadditions.item.*;
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
        OreDictionary.registerOre("dustZinc",new ItemStack(Items.dust, 1, MetaEnums.DustType.ZINC.getID()));
        OreDictionary.registerOre("ingotZinc",new ItemStack(Items.ingot, 1, MetaEnums.IngotType.ZINC.getID()));
        OreDictionary.registerOre("oreZinc",new ItemStack(igentuman.ncsteamadditions.block.Blocks.ore, 1, MetaEnums.OreType.ZINC.getID()));
        OreDictionary.registerOre("blockZinc",new ItemStack(igentuman.ncsteamadditions.block.Blocks.ingot_block, 1, MetaEnums.IngotType.ZINC.getID()));
        OreDictionary.registerOre("dustUraniumOxide",new ItemStack(Items.dust, 1, MetaEnums.DustType.URANIUM_OXIDE.getID()));
        OreDictionary.registerOre("coreOfTransformation",new ItemStack(Items.items[3]));
    }
}
