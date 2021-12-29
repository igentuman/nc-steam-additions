package igentuman.ncsteamadditions.gui;

import igentuman.ncsteamadditions.machine.container.ContainerSteamTransformer;
import igentuman.ncsteamadditions.machine.gui.GuiSteamTransformer;
import igentuman.ncsteamadditions.machine.tile.TileNCSteamAdditionsProcessor.TileSteamTransformer;
import igentuman.ncsteamadditions.processors.*;
import nc.container.processor.ContainerMachineConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		ID--;
		if (tile != null)
		{
			switch (ID)
			{
				case SteamTransformer.GUID:
					if (tile instanceof TileSteamTransformer)
						return new ContainerSteamTransformer(player,  (TileSteamTransformer)tile);
				case SteamTransformer.SIDEID:
					if (tile instanceof TileSteamTransformer)
						return new ContainerMachineConfig(player,  (TileSteamTransformer)tile);
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		ID--;
		if (tile != null)
		{
			switch (ID)
			{
				case SteamTransformer.GUID:
					if (tile instanceof TileSteamTransformer)
						return new GuiSteamTransformer(player,(TileSteamTransformer) tile);
				case SteamTransformer.SIDEID:
					if (tile instanceof TileSteamTransformer)
						return new GuiSteamTransformer.SideConfig(player,(TileSteamTransformer) tile);
			}
		}
		return null;
	}
}
