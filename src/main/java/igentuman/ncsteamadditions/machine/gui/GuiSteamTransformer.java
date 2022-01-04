package igentuman.ncsteamadditions.machine.gui;

import igentuman.ncsteamadditions.machine.container.ContainerSteamTransformer;
import igentuman.ncsteamadditions.processors.SteamTransformer;
import nc.container.ContainerTile;
import nc.tile.processor.TileItemFluidProcessor;
import net.minecraft.entity.player.EntityPlayer;

public class GuiSteamTransformer  extends GuiItemFluidMachine
{
	public SteamTransformer processor;

	@Override
	public SteamTransformer getProcessor()
	{
		return processor;
	}

	public GuiSteamTransformer(EntityPlayer player, TileItemFluidProcessor tile, SteamTransformer processor)
	{
		this(player, tile, new ContainerSteamTransformer(player, tile), processor);
	}

	private GuiSteamTransformer(EntityPlayer player, TileItemFluidProcessor tile, ContainerTile container, SteamTransformer processor)
	{
		super(processor.getCode(), player, tile, container);
		xSize = 176;
		ySize = 166;
		this.processor = processor;
	}

}