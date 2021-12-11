package igentuman.ncsteamadditions.block;

import igentuman.ncsteamadditions.tab.NCSteamAdditionsTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockNCSteamAdditions extends Block
{

	public BlockNCSteamAdditions(Material material)
	{
		super(material);
		this.setCreativeTab(NCSteamAdditionsTabs.BLOCKS);
	}

}
