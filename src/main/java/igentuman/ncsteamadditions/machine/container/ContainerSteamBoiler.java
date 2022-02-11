package igentuman.ncsteamadditions.machine.container;

import igentuman.ncsteamadditions.processors.ProcessorsRegistry;
import igentuman.ncsteamadditions.tile.TileNCSProcessor;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerSteamBoiler extends ProcessorContainer
{
	public ContainerSteamBoiler(EntityPlayer player, TileNCSProcessor tileEntity)
	{
		super(player, tileEntity, ProcessorsRegistry.get().STEAM_BOILER);
	}
}