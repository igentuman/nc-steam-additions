package igentuman.ncsteamadditions.item;

import igentuman.ncsteamadditions.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;

public class ItemDummy extends ItemBlock {

    public ItemDummy(Block block) {
        super(block);
        initTag(block);
    }

    public CreativeTabs getCreativeTab()
    {
        return null;
    }

    public ItemStack initTag(Block block) {
        ItemStack item = new ItemStack(this);

        if (!item.hasTagCompound()) {
            item.setTagCompound(new NBTTagCompound());
        }

        NBTTagCompound baseBlockNbt = new NBTTagCompound();
        item.writeToNBT(baseBlockNbt);
        item.getTagCompound().setTag("BlockDummy", baseBlockNbt);
        return item;
    }

    public ItemStack getBlockDummy(ItemStack item) {
        if (item.hasTagCompound()) {
            NBTTagCompound baseBlockNbt = item.getTagCompound().getCompoundTag("BlockDummy");
            ItemStack baseBlock = new ItemStack(baseBlockNbt);

            if (!baseBlock.isEmpty()) {
                return baseBlock;
            }
        }

        return new ItemStack(Blocks.otherBlocks[1]);
    }

}
