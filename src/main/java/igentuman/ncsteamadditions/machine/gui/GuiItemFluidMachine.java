package igentuman.ncsteamadditions.machine.gui;

import com.google.common.collect.Lists;
import igentuman.ncsteamadditions.NCSteamAdditions;
import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import igentuman.ncsteamadditions.network.*;
import igentuman.ncsteamadditions.processors.AbstractProcessor;
import igentuman.ncsteamadditions.tile.TileNCSProcessor;
import nc.gui.element.*;
import nc.gui.processor.*;
import nc.init.NCItems;
import nc.network.gui.*;
import nc.tile.energy.ITileEnergy;
import nc.util.*;
import nclegacy.container.*;
import nclegacy.gui.*;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.List;

public class GuiItemFluidMachine extends NCGuiLegacy {
    protected final EntityPlayer player;
    protected final TileNCSProcessor tile;
    protected final ResourceLocation gui_textures;
    protected GuiItemRenderer speedUpgradeRender = null;
    public static int inputItemsTop = 42;
    public static int inputItemsLeft = 26;
    public static int inputFluidsTop = 16;
    public static int inputFluidsLeft = 26;
    public static int cellSpan = 19;
    public int sideConfigButton;
    public int redstoneButton;

    public AbstractProcessor processor;

    public AbstractProcessor getProcessor()
    {
        return processor;
    }

    public GuiItemFluidMachine(String name, EntityPlayer player, TileNCSProcessor tile, Container inventory)
    {
        super(inventory);
        this.player = player;
        this.tile = tile;
        gui_textures = new ResourceLocation(NCSteamAdditions.MOD_ID + ":textures/gui/" + name + ".png");
    }

    private GuiItemFluidMachine(EntityPlayer player, TileNCSProcessor tile, ContainerTileLegacy container, String name)
    {
        this(name, player, tile, container);
        xSize = 176;
        ySize = 166;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String s = tile.getDisplayName().getUnformattedText();
        fontRenderer.drawString(s, xSize / 2 - fontRenderer.getStringWidth(s) / 2, 6, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1F, 1F, 1F, 1F);
        mc.getTextureManager().bindTexture(gui_textures);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        drawEfficiencyBar();
        drawTexturedModalRect(guiLeft + inputFluidsLeft+4, guiTop + 33, 0, 168, getCookProgressScaled(120), 24);
        drawUpgradeRenderers();
        drawBackgroundExtras();
    }

    public void drawEfficiencyBar()
    {
        if(NCSteamAdditionsConfig.efficiencyCap > 0) {
            int e = (int) Math.round(74 * tile.getRecipeEfficiency() / NCSteamAdditionsConfig.efficiencyCap);
            drawTexturedModalRect(guiLeft + 10, guiTop + 6 + 74 - e, 176, 90 + 74 - e, 11, e);
        }
    }

    protected int getCookProgressScaled(double pixels)
    {
        double i = tile.time;
        double j = tile.baseProcessTime;
        return j != 0D ? (int) Math.round(i * pixels / j) : 0;
    }

    @Override
    public void drawEnergyTooltip(ITileEnergy tile, int mouseX, int mouseY, int x, int y, int width, int height)
    {
    }

    @Override
    public List<String> energyInfo(ITileEnergy tile)
    {
        return Lists.newArrayList("");
    }

    protected void drawUpgradeRenderers()
    {
        if (speedUpgradeRender == null)
        {
            speedUpgradeRender = new GuiItemRenderer(NCItems.upgrade, 0, guiLeft + 152, guiTop + ySize - 102, 0.5F);
        }
        speedUpgradeRender.draw();
    }



    @Override
    public void initGui()
    {
        super.initGui();
        initButtons();
    }

    @Override
    public void renderTooltips(int mouseX, int mouseY)
    {
        drawEfficiencyTooltip(tile, mouseX, mouseY, 8, 6, 16, 74);
        renderButtonTooltips(mouseX, mouseY);
    }

    public void drawEfficiencyTooltip(TileNCSProcessor tile, int mouseX, int mouseY, int x, int y, int width, int height)
    {
        if(NCSteamAdditionsConfig.efficiencyCap > 0) {
            super.drawTooltip(Lang.localize("gui.nc.container.efficiency")+" "+String.format("%.1f",tile.getRecipeEfficiency()), mouseX, mouseY, x, y, width, height);
        }
    }

    public void renderButtonTooltips(int mouseX, int mouseY)
    {
        int x = inputFluidsLeft;
        int idCounter = 0;

        if(getProcessor().getInputFluids() > 0) {
            for(int i = 0; i < getProcessor().getInputFluids(); i++) {
                drawFluidTooltip(tile.getTanks().get(idCounter++), mouseX, mouseY, x, inputFluidsTop, 16, 16);
                x += cellSpan;
            }
        }

        x = 152;
        if(getProcessor().getOutputFluids() > 0) {
            for (int i = 0; i < getProcessor().getOutputFluids(); i++) {
                drawFluidTooltip(tile.getTanks().get(idCounter++), mouseX, mouseY, x, inputFluidsTop, 16, 16);
                x += cellSpan;
            }
        }

        drawTooltip(Lang.localize("gui.nc.container.machine_side_config"), mouseX, mouseY, 27, 63, 18, 18);
        drawTooltip(Lang.localize("gui.nc.container.redstone_control"), mouseX, mouseY, 47, 63, 18, 18);
    }

    protected void drawBackgroundExtras()
    {
        int x = inputFluidsLeft+guiLeft;
        int idCounter = 0;

        if(getProcessor().getInputFluids() > 0) {
            for(int i = 0; i < getProcessor().getInputFluids(); i++) {
                GuiFluidRenderer.renderGuiTank(tile.getTanks().get(idCounter++), x, guiTop + inputFluidsTop, zLevel, 16, 16);
                x += cellSpan;
            }
        }

        x = 152;
        if(getProcessor().getOutputFluids() > 0) {
            for (int i = 0; i < getProcessor().getOutputFluids(); i++) {
                GuiFluidRenderer.renderGuiTank(tile.getTanks().get(idCounter++), guiLeft + x, guiTop + inputFluidsTop, zLevel, 16, 16);
                x += cellSpan;
            }
        }
    }

    public void initButtons()
    {
        int x = inputFluidsLeft;

        int idCounter = 1;
        if(getProcessor().getInputFluids() > 0) {
            for(int i = 0; i < getProcessor().getInputFluids(); i++) {
                buttonList.add(new NCButton.ClearTank(idCounter++, guiLeft + x, guiTop + inputFluidsTop, 16, 16));
                x += cellSpan;
            }
        }

        x = 152;
        if(getProcessor().getOutputFluids() > 0) {
            for (int i = 0; i < getProcessor().getOutputFluids(); i++) {
                buttonList.add(new NCButton.ClearTank(idCounter++, guiLeft + x, guiTop + inputFluidsTop, 16, 16));
                x += cellSpan;
            }
        }

        sideConfigButton = idCounter;
        redstoneButton = idCounter+1;
        buttonList.add(new NCButton.MachineConfig(sideConfigButton, guiLeft + 27, guiTop + 63));
        buttonList.add(new NCToggleButton.RedstoneControl(redstoneButton, guiLeft + 47, guiTop + 63, tile));
    }


    @Override
    protected void actionPerformed(GuiButton guiButton)
    {
        if (tile.getWorld().isRemote)
        {
            if (guiButton.id == redstoneButton)
            {
                tile.setRedstoneControl(!tile.getRedstoneControl());
                new ToggleRedstoneControlPacket(tile).sendToServer();
                return;
            }

            if (guiButton.id == sideConfigButton)
            {
                NCSAPacketHandler.instance.sendToServer(new OpenSideGuiPacket(tile));
                return;
            }

            for (int i = 1; i <= getProcessor().getInputFluids() + getProcessor().getOutputFluids(); i++)
                if (guiButton.id == i && NCUtil.isModifierKeyDown())
                {
                    new ClearTankPacket(tile, i-1).sendToServer();
                    return;
                }
        }
    }


    public static class SideConfig extends GuiItemFluidMachine
    {
        AbstractProcessor processor;

        public AbstractProcessor getProcessor()
        {
            return processor;
        }

        public SideConfig(EntityPlayer player, TileNCSProcessor tile, AbstractProcessor proc)
        {
            super(player, tile, new ContainerMachineConfigLegacy(player, tile), proc.getCode());
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

            x = inputItemsLeft;
            if(getProcessor().getInputItems() > 0) {
                for (int i = 0; i < getProcessor().getInputItems(); i++) {
                    drawTooltip(TextFormatting.BLUE + Lang.localize("gui.nc.container.input_item_config"), mouseX, mouseY, x, inputItemsTop, 18, 18);
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

            x = 152;
            if(getProcessor().getOutputItems() > 0) {
                for (int i = 0; i < getProcessor().getOutputItems(); i++) {
                    drawTooltip(TextFormatting.GOLD + Lang.localize("gui.nc.container.output_item_config"), mouseX, mouseY, x, inputItemsTop, 18, 18);
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

            x = inputItemsLeft-1;
            if(getProcessor().getInputItems() > 0) {
                for (int i = 0; i < getProcessor().getInputItems(); i++) {
                    buttonList.add(new NCButton.SorptionConfig.ItemInput(idCounter++, guiLeft + x, guiTop + inputItemsTop-1, 18, 18));
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

            x = guiLeft + 151;
            if(getProcessor().getOutputItems() > 0) {
                for (int i = 0; i < getProcessor().getOutputItems(); i++) {
                    buttonList.add(new NCButton.SorptionConfig.ItemOutput(idCounter++, x, guiTop + inputItemsTop-1, 18, 18));
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
                idCounter += getProcessor().getInputItems();
                if(guiButton.id < idCounter) {
                    FMLCommonHandler.instance().showGuiScreen(new GuiFluidSorptionsLegacy.Input(this, tile, guiButton.id));
                    return;
                }
                idCounter += getProcessor().getOutputFluids();
                if(guiButton.id < idCounter) {
                    FMLCommonHandler.instance().showGuiScreen(new GuiFluidSorptionsLegacy.Output(this, tile, guiButton.id-1));
                    return;
                }
                idCounter += getProcessor().getOutputItems();
                if(guiButton.id < idCounter) {
                    FMLCommonHandler.instance().showGuiScreen(new GuiFluidSorptionsLegacy.Output(this, tile, guiButton.id-1));
                }
            }
        }
    }
}
