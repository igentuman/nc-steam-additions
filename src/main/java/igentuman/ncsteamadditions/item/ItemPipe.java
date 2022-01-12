package igentuman.ncsteamadditions.item;


import igentuman.ncsteamadditions.tab.NCSteamAdditionsTabs;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemPipe extends ItemBlock {
    public ItemPipe(Block block) {
        super(block);
        initTag(block);
    }

    public CreativeTabs getCreativeTab()
    {
        return NCSteamAdditionsTabs.ITEMS;
    }

    public ItemStack initTag(Block block) {
        ItemStack item = new ItemStack(this);

        if (!item.hasTagCompound()) {
            item.setTagCompound(new NBTTagCompound());
        }

        NBTTagCompound baseBlockNbt = new NBTTagCompound();
        item.writeToNBT(baseBlockNbt);
        item.getTagCompound().setTag("BlockPipe", baseBlockNbt);
        return item;
    }

    public ItemStack getBlockPipe(ItemStack item) {
        if (item.hasTagCompound()) {
            NBTTagCompound baseBlockNbt = item.getTagCompound().getCompoundTag("BlockPipe");
            ItemStack baseBlock = new ItemStack(baseBlockNbt);

            if (!baseBlock.isEmpty() && Block.getBlockFromItem(baseBlock.getItem()) != Blocks.AIR) {
                return baseBlock;
            }
        }

        return new ItemStack(Blocks.PLANKS);
    }

}
