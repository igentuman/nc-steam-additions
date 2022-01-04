package igentuman.ncsteamadditions.machine.gui;

import igentuman.ncsteamadditions.machine.container.ContainerSteamFluidTransformer;
import igentuman.ncsteamadditions.processors.SteamFluidTransformer;
import nc.container.ContainerTile;
import nc.tile.processor.TileItemFluidProcessor;
import net.minecraft.entity.player.EntityPlayer;

public class GuiSteamFluidTransformer extends GuiItemFluidMachine
{
	public SteamFluidTransformer processor;

	@Override
	public SteamFluidTransformer getProcessor()
	{
		return processor;
	}

	public GuiSteamFluidTransformer(EntityPlayer player, TileItemFluidProcessor tile, SteamFluidTransformer processor)
	{
		this(player, tile, new ContainerSteamFluidTransformer(player, tile), processor);
	}

	private GuiSteamFluidTransformer(EntityPlayer player, TileItemFluidProcessor tile, ContainerTile container, SteamFluidTransformer processor)
	{
		super(processor.getCode(), player, tile, container);
		xSize = 176;
		ySize = 166;
		this.processor = processor;
	}

}