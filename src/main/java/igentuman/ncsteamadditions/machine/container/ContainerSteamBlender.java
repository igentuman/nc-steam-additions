package igentuman.ncsteamadditions.machine.container;

import igentuman.ncsteamadditions.processors.ProcessorsRegistry;
import igentuman.ncsteamadditions.tile.TileNCSProcessor;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerSteamBlender extends ProcessorContainer
{
	public ContainerSteamBlender(EntityPlayer player, TileNCSProcessor tileEntity)
	{
		super(player, tileEntity, ProcessorsRegistry.get().STEAM_BLENDER);
	}
}
