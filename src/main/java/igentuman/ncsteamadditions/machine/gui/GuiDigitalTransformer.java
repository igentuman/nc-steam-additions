package igentuman.ncsteamadditions.machine.gui;

import cofh.core.gui.GuiTextList;
import com.google.common.collect.Lists;
import igentuman.ncsteamadditions.machine.container.ContainerDigitalTransformer;
import igentuman.ncsteamadditions.processors.DigitalTransformer;
import igentuman.ncsteamadditions.tile.TDigitalTransformer;
import nc.container.ContainerTile;
import nc.gui.element.GuiItemRenderer;
import nc.init.NCItems;
import nc.tile.energy.ITileEnergy;
import nc.tile.processor.TileItemFluidProcessor;
import nc.util.NCMath;
import nc.util.Lang;
import nc.util.UnitHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GuiDigitalTransformer extends GuiItemFluidMachine
{
	public DigitalTransformer processor;
	protected GuiItemRenderer speedUpgradeRender = null, energyUpgradeRender = null;
	@Override
	public DigitalTransformer getProcessor()
	{
		return processor;
	}

	@Override
	public List<String> energyInfo(ITileEnergy tile)
	{
		String energy = UnitHelper.prefix(tile.getEnergyStorage().getEnergyStored(),
				tile.getEnergyStorage().getMaxEnergyStored(), 5, "RF");
		String power = UnitHelper.prefix(this.tile.getProcessPower(), 5, "RF/t");

		String speedMultiplier = "x" + NCMath.decimalPlaces(this.tile.getSpeedMultiplier(), 2);
		String powerMultiplier = "x" + NCMath.decimalPlaces(this.tile.getPowerMultiplier(), 2);

		return Lists.newArrayList(
				TextFormatting.LIGHT_PURPLE + Lang.localise("gui.nc.container.energy_stored") + TextFormatting.WHITE
						+ " " + energy,
				TextFormatting.LIGHT_PURPLE + Lang.localise("gui.nc.container.process_power") + TextFormatting.WHITE
						+ " " + power,
				TextFormatting.AQUA + Lang.localise("gui.nc.container.speed_multiplier") + TextFormatting.WHITE + " "
						+ speedMultiplier,
				TextFormatting.AQUA + Lang.localise("gui.nc.container.power_multiplier") + TextFormatting.WHITE + " "
						+ powerMultiplier);
	}

	@Override
	public void drawEnergyTooltip(ITileEnergy tile, int mouseX, int mouseY, int x, int y, int width, int height)
	{
		if (this.tile.defaultProcessPower != 0)
			super.drawEnergyTooltip(tile, mouseX, mouseY, x, y, width, height);
		else
			drawNoEnergyTooltip(mouseX, mouseY, x, y, width, height);
	}

	public GuiDigitalTransformer(EntityPlayer player, TileItemFluidProcessor tile, DigitalTransformer processor)
	{
		this(player, tile, new ContainerDigitalTransformer(player, tile), processor);
	}

	private GuiDigitalTransformer(EntityPlayer player, TileItemFluidProcessor tile, ContainerTile container, DigitalTransformer processor)
	{
		super(processor.getCode(), player, tile, container);
		xSize = 176;
		ySize = 166;
		this.processor = processor;
	}

	protected void drawUpgradeRenderers()
	{
		if (energyUpgradeRender == null)
		{
			energyUpgradeRender = new GuiItemRenderer(NCItems.upgrade, 1, guiLeft + 152, guiTop + ySize - 102, 0.5F);
		}
		energyUpgradeRender.draw();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1F, 1F, 1F, 1F);
		mc.getTextureManager().bindTexture(gui_textures);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		if (tile.defaultProcessPower != 0)
		{
			int e = (int) Math.round(74D * tile.getEnergyStorage().getEnergyStored() / tile.getEnergyStorage().getMaxEnergyStored());
			drawTexturedModalRect(guiLeft + 8, guiTop + 6 + 74 - e, 176, 90 + 74 - e, 15, e);
		}
		else
			drawGradientRect(guiLeft + 8, guiTop + 6, guiLeft + 8 + 16, guiTop + 6 + 74, 0xFFC6C6C6, 0xFF8B8B8B);

		drawTexturedModalRect(guiLeft + inputFluidsLeft+4, guiTop + 33, 0, 168, getCookProgressScaled(135), 12);
		//drawUpgradeRenderers();
		drawBackgroundExtras();
		//TDigitalTransformer t = (TDigitalTransformer) tile;
		//drawRect(guiLeft,guiTop-25,guiLeft + xSize,guiTop,0xfffffff);
		//drawString(Minecraft.getMinecraft().fontRenderer,"EF: "+String.format("%.2f", t.getRecipeEfficiency()),guiLeft + 2, guiTop  - 7, 16711680);
		//drawString(Minecraft.getMinecraft().fontRenderer,"CR: "+String.format("%.2f", t.getCurrentReactivity()),guiLeft + 35, guiTop - 7,1113879);
		//drawString(Minecraft.getMinecraft().fontRenderer,"TR: "+String.format("%.2f", t.getTargetReactivity()),guiLeft + 65, guiTop  - 7,6950317);
	}

	@Override
	public void renderTooltips(int mouseX, int mouseY)
	{
		drawEnergyTooltip(tile, mouseX, mouseY, 8, 6, 16, 74);
		renderButtonTooltips(mouseX, mouseY);
	}

}