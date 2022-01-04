package igentuman.ncsteamadditions.machine.gui;

import igentuman.ncsteamadditions.machine.container.ContainerSteamCompactor;
import igentuman.ncsteamadditions.processors.SteamCompactor;
import nc.container.ContainerTile;
import nc.tile.processor.TileItemFluidProcessor;
import net.minecraft.entity.player.EntityPlayer;

public class GuiSteamCompactor extends GuiItemFluidMachine
{
	public SteamCompactor processor;

	@Override
	public SteamCompactor getProcessor()
	{
		return processor;
	}

	public GuiSteamCompactor(EntityPlayer player, TileItemFluidProcessor tile, SteamCompactor processor)
	{
		this(player, tile, new ContainerSteamCompactor(player, tile), processor);
	}

	private GuiSteamCompactor(EntityPlayer player, TileItemFluidProcessor tile, ContainerTile container, SteamCompactor processor)
	{
		super(processor.getCode(), player, tile, container);
		xSize = 176;
		ySize = 166;
		this.processor = processor;
	}

}