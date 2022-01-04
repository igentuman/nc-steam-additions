package igentuman.ncsteamadditions.machine.gui;

import igentuman.ncsteamadditions.machine.container.ContainerSteamWasher;
import igentuman.ncsteamadditions.processors.SteamWasher;
import nc.container.ContainerTile;
import nc.tile.processor.TileItemFluidProcessor;
import net.minecraft.entity.player.EntityPlayer;

public class GuiSteamWasher extends GuiItemFluidMachine
{
	public SteamWasher processor;

	@Override
	public SteamWasher getProcessor()
	{
		return processor;
	}

	public GuiSteamWasher(EntityPlayer player, TileItemFluidProcessor tile, SteamWasher processor)
	{
		this(player, tile, new ContainerSteamWasher(player, tile), processor);
	}

	private GuiSteamWasher(EntityPlayer player, TileItemFluidProcessor tile, ContainerTile container, SteamWasher processor)
	{
		super(processor.getCode(), player, tile, container);
		xSize = 176;
		ySize = 166;
		this.processor = processor;
	}

}