package igentuman.ncsteamadditions.machine.gui;

import java.io.IOException;
import igentuman.ncsteamadditions.machine.container.ContainerSteamTransformer;
import igentuman.ncsteamadditions.processors.SteamTransformer;
import nc.container.ContainerTile;
import nc.container.processor.ContainerMachineConfig;
import nc.gui.element.GuiFluidRenderer;
import nc.gui.element.NCButton;
import nc.gui.element.NCToggleButton;
import nc.gui.processor.GuiFluidSorptions;
import nc.gui.processor.GuiItemSorptions;
import nc.network.PacketHandler;
import nc.network.gui.EmptyTankPacket;
import nc.network.gui.ToggleRedstoneControlPacket;
import nc.tile.processor.TileItemFluidProcessor;
import nc.util.Lang;
import nc.util.NCUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class GuiSteamTransformer  extends GuiItemFluidMachine
{
	
	public GuiSteamTransformer(EntityPlayer player, TileItemFluidProcessor tile)
	{
		this(player, tile, new ContainerSteamTransformer(player, tile));
	}

	private GuiSteamTransformer(EntityPlayer player, TileItemFluidProcessor tile, ContainerTile container)
	{
		super(SteamTransformer.code, player, tile, container);
		xSize = 176;
		ySize = 166;
	}

	@Override
	public void renderTooltips(int mouseX, int mouseY)
	{
		renderButtonTooltips(mouseX, mouseY);
	}

	public void renderButtonTooltips(int mouseX, int mouseY)
	{
		int x = 16;
		int toLimit = 0;

		if(SteamTransformer.inputFluids > 0) {
			toLimit = SteamTransformer.inputFluids;
			for(int i = 0; i < toLimit; i++) {
				x += 20;
				drawFluidTooltip(tile.getTanks().get(i), mouseX, mouseY, x, 42, 16, 16);
			}
		}

		if(SteamTransformer.inputItems > 0) {
			toLimit += SteamTransformer.inputItems - 1;
			for (int i = toLimit - SteamTransformer.inputItems; i < toLimit; i++) {
				x += 20;
				//GuiFluidRenderer.renderGuiTank(tile.getTanks().get(i), guiLeft + x, guiTop + 42, zLevel, 16, 16);
			}
		}

		if(SteamTransformer.outputFluids > 0) {
			toLimit += SteamTransformer.outputFluids - 1;
			for (int i = toLimit - SteamTransformer.outputFluids; i < toLimit; i++) {
				x += 20;
				drawFluidTooltip(tile.getTanks().get(i), mouseX, mouseY, x, 42, 16, 16);
			}
		}

		if(SteamTransformer.outputItems > 0) {
			toLimit += SteamTransformer.outputItems - 1;
			for (int i = toLimit - SteamTransformer.outputItems; i < toLimit; i++) {
				x += 20;
				//GuiFluidRenderer.renderGuiTank(tile.getTanks().get(i), guiLeft + x, guiTop + 42, zLevel, 16, 16);
			}
		}
		x += 20;
		drawTooltip(Lang.localise("gui.nc.container.machine_side_config"), mouseX, mouseY, x, 63, 18, 18);
		drawTooltip(Lang.localise("gui.nc.container.redstone_control"), mouseX, mouseY, x+20, 63, 18, 18);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

		if (tile.defaultProcessPower != 0)
		{
			int e = (int) Math.round(74D * tile.getEnergyStorage().getEnergyStored() / tile.getEnergyStorage().getMaxEnergyStored());
			drawTexturedModalRect(guiLeft + 8, guiTop + 6 + 74 - e, 176, 90 + 74 - e, 16, e);
		}
		else
			drawGradientRect(guiLeft + 8, guiTop + 6, guiLeft + 8 + 16, guiTop + 6 + 74, 0xFFC6C6C6, 0xFF8B8B8B);

		drawTexturedModalRect(guiLeft + 40, guiTop + 29, 176, 0, getCookProgressScaled(48), 11);
		drawTexturedModalRect(guiLeft + 94, guiTop + 46, 176, 12, getCookProgressScaled(48)-32 < 0 ?0 : getCookProgressScaled(48)-32, 8);

		drawUpgradeRenderers();

		drawBackgroundExtras();
	}

	protected void drawBackgroundExtras()
	{
		int x = 16;
		int toLimit = 0;

		if(SteamTransformer.inputFluids > 0) {
			toLimit = SteamTransformer.inputFluids;
			for(int i = 0; i < toLimit; i++) {
				x += 20;
				GuiFluidRenderer.renderGuiTank(tile.getTanks().get(i), guiLeft + x, guiTop + 42, zLevel, 16, 16);
			}
		}

		if(SteamTransformer.inputItems > 0) {
			toLimit += SteamTransformer.inputItems - 1;
			for (int i = toLimit - SteamTransformer.inputItems; i < toLimit; i++) {
				x += 20;
				//GuiFluidRenderer.renderGuiTank(tile.getTanks().get(i), guiLeft + x, guiTop + 42, zLevel, 16, 16);
			}
		}

		if(SteamTransformer.outputFluids > 0) {
			toLimit += SteamTransformer.outputFluids - 1;
			for (int i = toLimit - SteamTransformer.outputFluids; i < toLimit; i++) {
				x += 20;
				GuiFluidRenderer.renderGuiTank(tile.getTanks().get(i), guiLeft + x, guiTop + 42, zLevel, 16, 16);
			}
		}

		if(SteamTransformer.outputItems > 0) {
			toLimit += SteamTransformer.outputItems - 1;
			for (int i = toLimit - SteamTransformer.outputItems; i < toLimit; i++) {
				x += 20;
				//GuiFluidRenderer.renderGuiTank(tile.getTanks().get(i), guiLeft + x, guiTop + 42, zLevel, 16, 16);
			}
		}
	}

	@Override
	public void initGui()
	{
		super.initGui();
		initButtons();
	}

	public void initButtons()
	{
		int x = 16;
		int toLimit = 0;

		if(SteamTransformer.inputFluids > 0) {
			toLimit = SteamTransformer.inputFluids;
			for(int i = 0; i < toLimit; i++) {
				x += 20;
				buttonList.add(new NCButton.EmptyTank(i, guiLeft + x, guiTop + 42, 16, 16));
			}
		}

		if(SteamTransformer.inputItems > 0) {
			toLimit += SteamTransformer.inputItems - 1;
			for (int i = toLimit - SteamTransformer.inputItems; i < toLimit; i++) {
				x += 20;
				//buttonList.add(new NCButton.EmptyTank(i, guiLeft + x, guiTop + 42, 16, 16));
			}
		}

		if(SteamTransformer.outputFluids > 0) {
			toLimit += SteamTransformer.outputFluids - 1;
			for (int i = toLimit - SteamTransformer.outputFluids; i < toLimit; i++) {
				x += 20;
				buttonList.add(new NCButton.SorptionConfig.FluidOutput(i, guiLeft + x, guiTop + 10));
			}
		}

		if(SteamTransformer.outputItems > 0) {
			toLimit += SteamTransformer.outputItems - 1;
			for (int i = toLimit - SteamTransformer.outputItems; i < toLimit; i++) {
				x += 20;
				//buttonList.add(new NCButton.SorptionConfig.ItemOutput(i, guiLeft + x, guiTop + 10));
			}
		}


		buttonList.add(new NCButton.MachineConfig(toLimit, guiLeft + 27, guiTop + 63));
		buttonList.add(new NCToggleButton.RedstoneControl(toLimit+1, guiLeft + 47, guiTop + 63, tile));
	}

	@Override
	protected void actionPerformed(GuiButton guiButton)
	{
		if (tile.getWorld().isRemote)
		{
			for (int i = 0; i < 3; i++)
				if (guiButton.id == i && NCUtil.isModifierKeyDown())
				{
					PacketHandler.instance.sendToServer(new EmptyTankPacket(tile, i));
					return;
				}

			else if (guiButton.id == 4)
			{
				tile.setRedstoneControl(!tile.getRedstoneControl());
				PacketHandler.instance.sendToServer(new ToggleRedstoneControlPacket(tile));
			}
		}
	}

	public static class SideConfig extends GuiSteamTransformer
	{

		public SideConfig(EntityPlayer player, TileItemFluidProcessor tile)
		{
			super(player, tile, new ContainerMachineConfig(player, tile));
		}


		@Override
		public void renderButtonTooltips(int mouseX, int mouseY)
		{

			int x = 16;
			int toLimit = 0;

			if(SteamTransformer.inputFluids > 0) {
				toLimit = SteamTransformer.inputFluids;
				for(int i = 0; i < toLimit; i++) {
					x += 20;
					drawTooltip(TextFormatting.DARK_AQUA + Lang.localise("gui.nc.container.input_tank_config"), mouseX, mouseY, x, 42, 18, 18);
				}
			}

			if(SteamTransformer.inputItems > 0) {
				toLimit += SteamTransformer.inputItems - 1;
				for (int i = toLimit - SteamTransformer.inputItems; i < toLimit; i++) {
					x += 20;
					drawTooltip(TextFormatting.BLUE + Lang.localise("gui.nc.container.input_item_config"), mouseX, mouseY, x, 11, 18, 18);

				}
			}

			if(SteamTransformer.outputFluids > 0) {
				toLimit += SteamTransformer.outputFluids - 1;
				for (int i = toLimit - SteamTransformer.outputFluids; i < toLimit; i++) {
					x += 20;
					drawTooltip(TextFormatting.DARK_AQUA + Lang.localise("gui.nc.container.output_tank_config"), mouseX, mouseY, x, 42, 18, 18);

				}
			}

			if(SteamTransformer.outputItems > 0) {
				toLimit += SteamTransformer.outputItems - 1;
				for (int i = toLimit - SteamTransformer.outputItems; i < toLimit; i++) {
					x += 20;
					drawTooltip(TextFormatting.GOLD + Lang.localise("gui.nc.container.output_item_config"), mouseX, mouseY, x, 42, 18, 18);
				}
			}

			drawTooltip(TextFormatting.DARK_BLUE + Lang.localise("gui.nc.container.upgrade_config"), mouseX, mouseY, 131, 63, 18, 18);
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
			int x = 15;
			int toLimit = 0;

			if(SteamTransformer.inputFluids > 0) {
				toLimit = SteamTransformer.inputFluids;
				for(int i = 0; i < toLimit; i++) {
					x += 20;
					buttonList.add(new NCButton.SorptionConfig.FluidInput(i, guiLeft + x, guiTop + 10));
				}
			}

			if(SteamTransformer.inputItems > 0) {
				toLimit += SteamTransformer.inputItems - 1;
				for (int i = toLimit - SteamTransformer.inputItems; i < toLimit; i++) {
					x += 20;
					buttonList.add(new NCButton.SorptionConfig.ItemInput(i, guiLeft + x, guiTop + 10));
				}
			}

			if(SteamTransformer.outputFluids > 0) {
				toLimit += SteamTransformer.outputFluids - 1;
				for (int i = toLimit - SteamTransformer.outputFluids; i < toLimit; i++) {
					x += 20;
					buttonList.add(new NCButton.SorptionConfig.FluidOutput(i, guiLeft + x, guiTop + 10));
				}
			}

			if(SteamTransformer.outputItems > 0) {
				toLimit += SteamTransformer.outputItems - 1;
				for (int i = toLimit - SteamTransformer.outputItems; i < toLimit; i++) {
					x += 20;
					buttonList.add(new NCButton.SorptionConfig.ItemOutput(i, guiLeft + x, guiTop + 10));
				}
			}

			buttonList.add(new NCButton.SorptionConfig.SpeedUpgrade(toLimit, guiLeft + 131, guiTop + 63));
		}

		@Override
		protected void actionPerformed(GuiButton guiButton)
		{
			if (tile.getWorld().isRemote)
			{
				//order Finput,Iinput,Foutput,Ioutput
				if(guiButton.id < SteamTransformer.inputFluids) {
					 FMLCommonHandler.instance().showGuiScreen(new GuiFluidSorptions.Input(this, tile, guiButton.id));
					 return;
				}

				if(guiButton.id < SteamTransformer.inputFluids + SteamTransformer.inputItems - 1) {
					FMLCommonHandler.instance().showGuiScreen(new GuiItemSorptions.Input(this, tile, guiButton.id));
					return;
				}

				if(guiButton.id < SteamTransformer.inputFluids + SteamTransformer.inputItems + SteamTransformer.outputFluids - 1) {
					FMLCommonHandler.instance().showGuiScreen(new GuiFluidSorptions.Output(this, tile, guiButton.id));
					return;
				}

				if(guiButton.id < SteamTransformer.inputFluids + SteamTransformer.inputItems + SteamTransformer.outputFluids + SteamTransformer.outputItems - 1) {
					FMLCommonHandler.instance().showGuiScreen(new GuiItemSorptions.Output(this, tile, guiButton.id));
					return;
				}

				FMLCommonHandler.instance().showGuiScreen(new GuiItemSorptions.SpeedUpgrade(this, tile, 4));
				return;
			}
		}
	}

}