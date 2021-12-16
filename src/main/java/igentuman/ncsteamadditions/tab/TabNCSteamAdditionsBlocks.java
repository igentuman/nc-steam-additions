package igentuman.ncsteamadditions.tab;

import igentuman.ncsteamadditions.block.Blocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabNCSteamAdditionsBlocks extends CreativeTabs
{

	public TabNCSteamAdditionsBlocks()
	{
		super("ncsteamadditions.blocks");
	}

	@Override
	public ItemStack createIcon()
	{
		return new ItemStack(Blocks.blocks[1]);
	}
}
