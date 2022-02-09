package igentuman.ncsteamadditions.machine.gui;

import igentuman.ncsteamadditions.machine.container.ContainerSteamTurbine;
import igentuman.ncsteamadditions.processors.SteamTurbine;
import igentuman.ncsteamadditions.tile.TileNCSProcessor;
import nc.container.ContainerTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;

public class GuiSteamTurbine extends GuiItemFluidMachine
{
	public SteamTurbine processor;

	@Override
	public SteamTurbine getProcessor()
	{
		return processor;
	}

	public GuiSteamTurbine(EntityPlayer player, TileNCSProcessor tile, SteamTurbine processor)
	{
		this(player, tile, new ContainerSteamTurbine(player, tile), processor);
	}

	private GuiSteamTurbine(EntityPlayer player, TileNCSProcessor tile, ContainerTile container, SteamTurbine processor)
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
		drawBackgroundExtras();
		drawString(Minecraft.getMinecraft().fontRenderer,"Efficiency: "+String.format("%.2f", tile.getRecipeEfficiency()),guiLeft + 2, guiTop  - 7, 16711680);

	}

	public void initButtons()
	{
		super.initButtons();
	}


}