package igentuman.ncsteamadditions.enums;

import igentuman.ncsteamadditions.block.NCSteamAdditionsBlocks;
import igentuman.ncsteamadditions.gui.GUI_ID;
import igentuman.ncsteamadditions.machine.tile.TileNCSteamAdditionsProcessor.TileSteamTransformer;
import igentuman.ncsteamadditions.tab.NCSteamAdditionsTabs;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IStringSerializable;

public class BlockTypes
{

	public enum ProcessorType implements IStringSerializable
	{
		STEAM_TRANSFORMER("steam_transformer", GUI_ID.STEAM_TRANSFORMER, "splash", "reddust");
		private String name;
		private int id;

		private ProcessorType(String name, int id, String particle1, String particle2)
		{
			this.name = name;
			this.id = id;
		}

		@Override
		public String getName()
		{
			return name;
		}

		public int getID()
		{
			return id;
		}

		public TileEntity getTile()
		{
			switch (this)
			{
			case STEAM_TRANSFORMER:
				return new TileSteamTransformer();

			default:
				return null;
			}
		}

		public Block getBlock()
		{
			switch (this)
			{
			case STEAM_TRANSFORMER:
				return NCSteamAdditionsBlocks.steamTransformer;
			default:
				return NCSteamAdditionsBlocks.steamTransformer;
			}
		}

		public CreativeTabs getCreativeTab()
		{
			switch (this)
			{
			default:
				return NCSteamAdditionsTabs.BLOCKS;
			}
		}
	}

}


