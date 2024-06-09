package igentuman.ncsteamadditions.machine.gui;

import igentuman.ncsteamadditions.machine.container.ContainerHeatExchanger;
import igentuman.ncsteamadditions.processors.HeatExchanger;
import igentuman.ncsteamadditions.tile.TileNCSProcessor;
import nc.gui.element.*;
import nc.gui.processor.GuiFluidSorptions;
import nc.util.Lang;
import nclegacy.container.*;
import nclegacy.gui.GuiFluidSorptionsLegacy;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class GuiHeatExchanger extends GuiItemFluidMachine
{
	public HeatExchanger processor;

	@Override
	public HeatExchanger getProcessor()
	{
		return processor;
	}

	public GuiHeatExchanger(EntityPlayer player, TileNCSProcessor tile, HeatExchanger processor)
	{
		this(player, tile, new ContainerHeatExchanger(player, tile), processor);
	}

	private GuiHeatExchanger(EntityPlayer player, TileNCSProcessor tile, ContainerTileLegacy container, HeatExchanger processor)
	{
		super(processor.getCode(), player, tile, container);
		xSize = 176;
		ySize = 166;
		this.processor = processor;
	}

	protected void drawBackgroundExtras()
	{
		int x = inputFluidsLeft+guiLeft;
		int y = guiTop + inputFluidsTop;

		int idCounter = 0;

		if(getProcessor().getInputFluids() > 0) {
			for(int i = 0; i < getProcessor().getInputFluids(); i++) {
				GuiFluidRenderer.renderGuiTank(tile.getTanks().get(idCounter++), x, y, zLevel, 16, 16);
				y += 27;
			}
		}
		y = guiTop + inputFluidsTop;
		x = 152;
		if(getProcessor().getOutputFluids() > 0) {
			for (int i = 0; i < getProcessor().getOutputFluids(); i++) {
				GuiFluidRenderer.renderGuiTank(tile.getTanks().get(idCounter++), guiLeft + x, y, zLevel, 16, 16);
				y += 27;
			}
		}
	}

	public void renderButtonTooltips(int mouseX, int mouseY)
	{
		int x = inputFluidsLeft;
		int idCounter = 0;
		int y = inputFluidsTop;
		if(getProcessor().getInputFluids() > 0) {
			for(int i = 0; i < getProcessor().getInputFluids(); i++) {
				drawFluidTooltip(tile.getTanks().get(idCounter++), mouseX, mouseY, x, y, 16, 16);
				y += 27;
			}
		}
		y = inputFluidsTop;
		x = 152;
		if(getProcessor().getOutputFluids() > 0) {
			for (int i = 0; i < getProcessor().getOutputFluids(); i++) {
				drawFluidTooltip(tile.getTanks().get(idCounter++), mouseX, mouseY, x, y, 16, 16);
				y += 27;
			}
		}

		drawTooltip(Lang.localize("gui.nc.container.machine_side_config"), mouseX, mouseY, 27, 63, 18, 18);
		drawTooltip(Lang.localize("gui.nc.container.redstone_control"), mouseX, mouseY, 47, 63, 18, 18);
	}

	public void initButtons()
	{
		int x = inputFluidsLeft;
		int y = guiTop + inputFluidsTop;
		int idCounter = 1;
		if(getProcessor().getInputFluids() > 0) {
			for(int i = 0; i < getProcessor().getInputFluids(); i++) {
				buttonList.add(new NCButton.ClearTank(idCounter++, guiLeft + x, y, 16, 16));
				y += 27;
			}
		}
		y = guiTop + inputFluidsTop;
		x = 152;
		if(getProcessor().getOutputFluids() > 0) {
			for (int i = 0; i < getProcessor().getOutputFluids(); i++) {
				buttonList.add(new NCButton.ClearTank(idCounter++, guiLeft + x, guiTop + inputFluidsTop, 16, 16));
				y += 27;
			}
		}

		sideConfigButton = idCounter;
		redstoneButton = idCounter+1;
		buttonList.add(new NCButton.MachineConfig(sideConfigButton, guiLeft + 27, guiTop + 63));
		buttonList.add(new NCToggleButton.RedstoneControl(redstoneButton, guiLeft + 47, guiTop + 63, tile));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1F, 1F, 1F, 1F);
		mc.getTextureManager().bindTexture(gui_textures);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		drawEfficiencyBar();
		drawTexturedModalRect(guiLeft + inputFluidsLeft+24, guiTop + 17, 0, 167, getCookProgressScaled(95), 41);
		drawBackgroundExtras();

	}

	public static class SideConfig extends GuiHeatExchanger
	{
		HeatExchanger processor;

		public HeatExchanger getProcessor()
		{
			return processor;
		}

		public SideConfig(EntityPlayer player, TileNCSProcessor tile, HeatExchanger proc)
		{
			super(player, tile, new ContainerMachineConfigLegacy(player, tile), proc);
			processor = proc;
		}

		@Override
		public void renderButtonTooltips(int mouseX, int mouseY)
		{

			int x = inputFluidsLeft;

			if(getProcessor().getInputFluids() > 0) {
				for(int i = 0; i < getProcessor().getInputFluids(); i++) {
					drawTooltip(TextFormatting.DARK_AQUA + Lang.localize("gui.nc.container.input_tank_config"), mouseX, mouseY, x, inputFluidsTop, 18, 18);
					x += cellSpan;
				}
			}

			x = 152;
			if(getProcessor().getOutputFluids() > 0) {
				for (int i = 0; i < getProcessor().getOutputFluids(); i++) {
					drawTooltip(TextFormatting.DARK_PURPLE + Lang.localize("gui.nc.container.output_tank_config"), mouseX, mouseY, x, inputFluidsTop, 18, 18);
					x += cellSpan;
				}
			}

		}

		@Override
		protected void drawUpgradeRenderers()
		{
		}

		@Override
		protected void drawBackgroundExtras()
		{
		};



		@Override
		public void initButtons()
		{
			int x = inputFluidsLeft-1;
			int idCounter = 0;

			if(getProcessor().getInputFluids() > 0) {
				for(int i = 0; i < getProcessor().getInputFluids(); i++) {
					buttonList.add(new NCButton.SorptionConfig.FluidInput(idCounter++, guiLeft + x, guiTop + inputFluidsTop-1, 18, 18));
					x += cellSpan;
				}
			}


			x = guiLeft + 151;
			if(getProcessor().getOutputFluids() > 0) {
				for (int i = 0; i < getProcessor().getOutputFluids(); i++) {
					buttonList.add(new NCButton.SorptionConfig.FluidOutput(idCounter++,  x, guiTop + inputFluidsTop-1, 18, 18));
					x += cellSpan;
				}
			}

		}

		@Override
		protected void actionPerformed(GuiButton guiButton)
		{
			if (tile.getWorld().isRemote)
			{
				//order Finput,Iinput,Foutput,Ioutput
				int idCounter = getProcessor().getInputFluids();
				if(guiButton.id < idCounter) {
					FMLCommonHandler.instance().showGuiScreen(new GuiFluidSorptionsLegacy.Input(this, tile, guiButton.id));
					return;
				}
				idCounter += getProcessor().getOutputFluids();
				if(guiButton.id < idCounter) {
					FMLCommonHandler.instance().showGuiScreen(new GuiFluidSorptionsLegacy.Output(this, tile, guiButton.id-1));
					return;
				}
			}
		}
	}
}
