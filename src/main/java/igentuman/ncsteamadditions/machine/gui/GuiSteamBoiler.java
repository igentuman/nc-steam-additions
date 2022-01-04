package igentuman.ncsteamadditions.machine.gui;

import igentuman.ncsteamadditions.machine.container.ContainerSteamBoiler;
import igentuman.ncsteamadditions.processors.SteamBoiler;
import nc.container.ContainerTile;
import nc.tile.processor.TileItemFluidProcessor;
import net.minecraft.entity.player.EntityPlayer;

public class GuiSteamBoiler extends GuiItemFluidMachine
{
	public SteamBoiler processor;

	@Override
	public SteamBoiler getProcessor()
	{
		return processor;
	}

	public GuiSteamBoiler(EntityPlayer player, TileItemFluidProcessor tile, SteamBoiler processor)
	{
		this(player, tile, new ContainerSteamBoiler(player, tile), processor);
	}

	private GuiSteamBoiler(EntityPlayer player, TileItemFluidProcessor tile, ContainerTile container, SteamBoiler processor)
	{
		super(SteamBoiler.code, player, tile, container);
		xSize = 176;
		ySize = 166;
		this.processor = processor;
	}

}