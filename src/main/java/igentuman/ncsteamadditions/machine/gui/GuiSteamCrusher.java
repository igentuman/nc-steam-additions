package igentuman.ncsteamadditions.machine.gui;

import igentuman.ncsteamadditions.machine.container.ContainerSteamCrusher;
import igentuman.ncsteamadditions.processors.SteamCrusher;
import nc.container.ContainerTile;
import nc.tile.processor.TileItemFluidProcessor;
import net.minecraft.entity.player.EntityPlayer;

public class GuiSteamCrusher extends GuiItemFluidMachine
{
	public SteamCrusher processor;

	@Override
	public SteamCrusher getProcessor()
	{
		return processor;
	}

	public GuiSteamCrusher(EntityPlayer player, TileItemFluidProcessor tile, SteamCrusher processor)
	{
		this(player, tile, new ContainerSteamCrusher(player, tile), processor);
	}

	private GuiSteamCrusher(EntityPlayer player, TileItemFluidProcessor tile, ContainerTile container, SteamCrusher processor)
	{
		super(SteamCrusher.code, player, tile, container);
		xSize = 176;
		ySize = 166;
		this.processor = processor;
	}

}