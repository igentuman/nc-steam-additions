package igentuman.ncsteamadditions.machine.gui;

import igentuman.ncsteamadditions.machine.container.ContainerSteamFluidTransformer;
import igentuman.ncsteamadditions.processors.SteamFluidTransformer;
import igentuman.ncsteamadditions.tile.TileNCSProcessor;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;

public class GuiSteamFluidTransformer extends GuiItemFluidMachine
{
	public SteamFluidTransformer processor;

	@Override
	public SteamFluidTransformer getProcessor()
	{
		return processor;
	}

	public GuiSteamFluidTransformer(EntityPlayer player, TileNCSProcessor tile, SteamFluidTransformer processor)
	{
		this(player, tile, new ContainerSteamFluidTransformer(player, tile), processor);
	}

	private GuiSteamFluidTransformer(EntityPlayer player, TileNCSProcessor tile, ContainerTile container, SteamFluidTransformer processor)
	{
		super(processor.getCode(), player, tile, container);
		xSize = 176;
		ySize = 166;
		this.processor = processor;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1F, 1F, 1F, 1F);
		mc.getTextureManager().bindTexture(gui_textures);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		drawTexturedModalRect(guiLeft + inputFluidsLeft+4, guiTop + 33, 0, 168, getCookProgressScaled(135), 12);
		drawUpgradeRenderers();
		drawBackgroundExtras();
		drawEfficiencyBar();
	}
}
