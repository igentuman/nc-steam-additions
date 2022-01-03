package igentuman.ncsteamadditions.item;


import igentuman.ncsteamadditions.block.BlockPipe;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class Items {

    public static ItemBlock getItemBlock(Block block)
    {
        if(BlockPipe.class == block.getClass()) {
            BlockPipe.PIPE_ITEM = new ItemPipe(block);
            return (ItemBlock) BlockPipe.PIPE_ITEM;
        }
        return null;
    }
}
