package igentuman.ncsteamadditions.machine.gui;

import igentuman.ncsteamadditions.machine.container.ContainerSteamWasher;
import igentuman.ncsteamadditions.processors.SteamWasher;
import igentuman.ncsteamadditions.tile.TileNCSProcessor;
import net.minecraft.entity.player.EntityPlayer;

public class GuiSteamWasher extends GuiItemFluidMachine
{
	public SteamWasher processor;

	@Override
	public SteamWasher getProcessor()
	{
		return processor;
	}

	public GuiSteamWasher(EntityPlayer player, TileNCSProcessor tile, SteamWasher processor)
	{
		this(player, tile, new ContainerSteamWasher(player, tile), processor);
	}

	private GuiSteamWasher(EntityPlayer player, TileNCSProcessor tile, ContainerTile container, SteamWasher processor)
	{
		super(processor.getCode(), player, tile, container);
		xSize = 176;
		ySize = 166;
		this.processor = processor;
	}
}
