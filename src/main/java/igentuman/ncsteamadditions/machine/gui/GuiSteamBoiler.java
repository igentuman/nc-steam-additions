package igentuman.ncsteamadditions.machine.gui;

import igentuman.ncsteamadditions.machine.container.ContainerSteamBoiler;
import igentuman.ncsteamadditions.processors.SteamBoiler;
import igentuman.ncsteamadditions.tile.TileNCSProcessor;
import nc.container.ContainerTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;

public class GuiSteamBoiler extends GuiItemFluidMachine
{
	public SteamBoiler processor;

	@Override
	public SteamBoiler getProcessor()
	{
		return processor;
	}

	public GuiSteamBoiler(EntityPlayer player, TileNCSProcessor tile, SteamBoiler processor)
	{
		this(player, tile, new ContainerSteamBoiler(player, tile), processor);
	}

	private GuiSteamBoiler(EntityPlayer player, TileNCSProcessor tile, ContainerTile container, SteamBoiler processor)
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
		drawEfficiencyBar();
		drawTexturedModalRect(guiLeft + inputFluidsLeft+4, guiTop + 33, 0, 168, getCookProgressScaled(135), 12);
		drawUpgradeRenderers();
		drawBackgroundExtras();
	}

}