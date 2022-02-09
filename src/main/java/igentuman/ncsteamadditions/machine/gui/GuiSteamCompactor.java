package igentuman.ncsteamadditions.machine.gui;

import igentuman.ncsteamadditions.machine.container.ContainerSteamCompactor;
import igentuman.ncsteamadditions.processors.SteamCompactor;
import igentuman.ncsteamadditions.tile.TileNCSProcessor;
import nc.container.ContainerTile;
import net.minecraft.entity.player.EntityPlayer;

public class GuiSteamCompactor extends GuiItemFluidMachine
{
	public SteamCompactor processor;

	@Override
	public SteamCompactor getProcessor()
	{
		return processor;
	}

	public GuiSteamCompactor(EntityPlayer player, TileNCSProcessor tile, SteamCompactor processor)
	{
		this(player, tile, new ContainerSteamCompactor(player, tile), processor);
	}

	private GuiSteamCompactor(EntityPlayer player, TileNCSProcessor tile, ContainerTile container, SteamCompactor processor)
	{
		super(processor.getCode(), player, tile, container);
		xSize = 176;
		ySize = 166;
		this.processor = processor;
	}

}