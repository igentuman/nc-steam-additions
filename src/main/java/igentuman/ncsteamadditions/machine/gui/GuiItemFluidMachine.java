package igentuman.ncsteamadditions.machine.gui;

import java.util.List;
import com.google.common.collect.Lists;
import igentuman.ncsteamadditions.NCSteamAdditions;
import igentuman.ncsteamadditions.network.NCSAPacketHandler;
import igentuman.ncsteamadditions.network.OpenSideGuiPacket;
import igentuman.ncsteamadditions.processors.AbstractProcessor;
import igentuman.ncsteamadditions.tile.TileNCSProcessor;
import nc.container.ContainerTile;
import nc.container.processor.ContainerMachineConfig;
import nc.gui.NCGui;
import nc.gui.element.GuiFluidRenderer;
import nc.gui.element.GuiItemRenderer;
import nc.gui.element.NCButton;
import nc.gui.element.NCToggleButton;
import nc.gui.processor.GuiFluidSorptions;
import nc.gui.processor.GuiItemSorptions;
import nc.init.NCItems;
import nc.network.PacketHandler;
import nc.network.gui.EmptyTankPacket;
import nc.network.gui.ToggleRedstoneControlPacket;
import nc.tile.energy.ITileEnergy;
import nc.util.Lang;
import nc.util.NCUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class GuiItemFluidMachine extends NCGui {
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

    private GuiItemFluidMachine(EntityPlayer player, TileNCSProcessor tile, ContainerTile container,String name)
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

        drawTexturedModalRect(guiLeft + inputFluidsLeft+4, guiTop + 33, 0, 168, getCookProgressScaled(120), 24);

        drawUpgradeRenderers();

        drawBackgroundExtras();
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
        renderButtonTooltips(mouseX, mouseY);
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

        x = inputItemsLeft;
        if(getProcessor().getInputItems() > 0) {
            for (int i = 0; i < getProcessor().getInputItems(); i++) {
                //x += cellSpan;
                //GuiFluidRenderer.renderGuiTank(tile.getTanks().get(idCounter++), guiLeft + x, guiTop + getInputItems()Top, zLevel, 16, 16);
            }
        }

        x = 152;
        if(getProcessor().getOutputFluids() > 0) {
            for (int i = 0; i < getProcessor().getOutputFluids(); i++) {
                drawFluidTooltip(tile.getTanks().get(idCounter++), mouseX, mouseY, x, inputFluidsTop, 16, 16);
                x += cellSpan;
            }
        }

        x = 152;
        if(getProcessor().getOutputItems() > 0) {
            for (int i = 0; i < getProcessor().getOutputItems(); i++) {
                //GuiFluidRenderer.renderGuiTank(tile.getTanks().get(idCounter++), guiLeft + x, guiTop + getInputItems()Top, zLevel, 16, 16);
                //x += cellSpan;
            }
        }
        drawTooltip(Lang.localise("gui.nc.container.machine_side_config"), mouseX, mouseY, 27, 63, 18, 18);
        drawTooltip(Lang.localise("gui.nc.container.redstone_control"), mouseX, mouseY, 47, 63, 18, 18);
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

        x = inputItemsLeft;
        if(getProcessor().getInputItems() > 0) {
            for (int i = 0; i < getProcessor().getInputItems(); i++) {
                //GuiFluidRenderer.renderGuiTank(tile.getTanks().get(idCounter++), guiLeft + x, guiTop + getInputItems()Top, zLevel, 16, 16);
                //x += cellSpan;
            }
        }

        x = 152;
        if(getProcessor().getOutputFluids() > 0) {
            for (int i = 0; i < getProcessor().getOutputFluids(); i++) {
                GuiFluidRenderer.renderGuiTank(tile.getTanks().get(idCounter++), guiLeft + x, guiTop + inputFluidsTop, zLevel, 16, 16);
                x += cellSpan;
            }
        }

        x = 152;
        if(getProcessor().getOutputItems() > 0) {
            for (int i = 0; i < getProcessor().getOutputItems(); i++) {
                //GuiFluidRenderer.renderGuiTank(tile.getTanks().get(idCounter++), guiLeft + x, guiTop + getInputItems()Top, zLevel, 16, 16);
                //x += cellSpan;
            }
        }
    }

    public void initButtons()
    {
        int x = inputFluidsLeft;

        int idCounter = 1;
        if(getProcessor().getInputFluids() > 0) {
            for(int i = 0; i < getProcessor().getInputFluids(); i++) {
                buttonList.add(new NCButton.EmptyTank(idCounter++, guiLeft + x, guiTop + inputFluidsTop, 16, 16));
                x += cellSpan;
            }
        }

        x = inputItemsLeft;
        if(getProcessor().getInputItems() > 0) {
            for (int i = 0; i < getProcessor().getInputItems(); i++) {
                //buttonList.add(new NCButton.EmptyTank(idCounter++, guiLeft + x, guiTop + getInputItems()Top, 16, 16));
                //x += cellSpan;
            }
        }

        x = 152;
        if(getProcessor().getOutputFluids() > 0) {
            for (int i = 0; i < getProcessor().getOutputFluids(); i++) {
                buttonList.add(new NCButton.EmptyTank(idCounter++, guiLeft + x, guiTop + inputFluidsTop, 16, 16));
                x += cellSpan;
            }
        }

        x = 152;
        if(getProcessor().getOutputItems() > 0) {
            for (int i = 0; i < getProcessor().getOutputItems(); i++) {
                //buttonList.add(new NCButton.SorptionConfig.ItemOutput(idCounter++, guiLeft + x, guiTop + getInputItems()Top));
                //x += cellSpan;
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
                PacketHandler.instance.sendToServer(new ToggleRedstoneControlPacket(tile));
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
                    PacketHandler.instance.sendToServer(new EmptyTankPacket(tile, i-1));
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
            super(player, tile, new ContainerMachineConfig(player, tile), proc.getCode());
            processor = proc;
        }

        @Override
        public void renderButtonTooltips(int mouseX, int mouseY)
        {

            int x = inputFluidsLeft;

            if(getProcessor().getInputFluids() > 0) {
                for(int i = 0; i < getProcessor().getInputFluids(); i++) {
                    drawTooltip(TextFormatting.DARK_AQUA + Lang.localise("gui.nc.container.input_tank_config"), mouseX, mouseY, x, inputFluidsTop, 18, 18);
                    x += cellSpan;
                }
            }

            x = inputItemsLeft;
            if(getProcessor().getInputItems() > 0) {
                for (int i = 0; i < getProcessor().getInputItems(); i++) {
                    drawTooltip(TextFormatting.BLUE + Lang.localise("gui.nc.container.input_item_config"), mouseX, mouseY, x, inputItemsTop, 18, 18);
                    x += cellSpan;
                }
            }

            x = 152;
            if(getProcessor().getOutputFluids() > 0) {
                for (int i = 0; i < getProcessor().getOutputFluids(); i++) {
                    drawTooltip(TextFormatting.DARK_PURPLE + Lang.localise("gui.nc.container.output_tank_config"), mouseX, mouseY, x, inputFluidsTop, 18, 18);
                    x += cellSpan;
                }
            }

            x = 152;
            if(getProcessor().getOutputItems() > 0) {
                for (int i = 0; i < getProcessor().getOutputItems(); i++) {
                    drawTooltip(TextFormatting.GOLD + Lang.localise("gui.nc.container.output_item_config"), mouseX, mouseY, x, inputItemsTop, 18, 18);
                    x += cellSpan;
                }
            }

            //drawTooltip(TextFormatting.DARK_BLUE + Lang.localise("gui.nc.container.upgrade_config"), mouseX, mouseY, 152, 63, 18, 18);
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
                    buttonList.add(new NCButton.SorptionConfig.FluidInput(idCounter++, guiLeft + x, guiTop + inputFluidsTop-1));
                    x += cellSpan;
                }
            }

            x = inputItemsLeft-1;
            if(getProcessor().getInputItems() > 0) {
                for (int i = 0; i < getProcessor().getInputItems(); i++) {
                    buttonList.add(new NCButton.SorptionConfig.ItemInput(idCounter++, guiLeft + x, guiTop + inputItemsTop-1));
                    x += cellSpan;
                }
            }

            x = guiLeft + 151;
            if(getProcessor().getOutputFluids() > 0) {
                for (int i = 0; i < getProcessor().getOutputFluids(); i++) {
                    buttonList.add(new NCButton.SorptionConfig.FluidOutputSmall(idCounter++,  x, guiTop + inputFluidsTop-1));
                    x += cellSpan;
                }
            }

            x = guiLeft + 151;
            if(getProcessor().getOutputItems() > 0) {
                for (int i = 0; i < getProcessor().getOutputItems(); i++) {
                    buttonList.add(new NCButton.SorptionConfig.ItemOutputSmall(idCounter++, x, guiTop + inputItemsTop-1));
                    x += cellSpan;
                }
            }

            //buttonList.add(new NCButton.SorptionConfig.SpeedUpgrade(idCounter, guiLeft + 152, guiTop + 63));
        }

        @Override
        protected void actionPerformed(GuiButton guiButton)
        {
            if (tile.getWorld().isRemote)
            {
                //order Finput,Iinput,Foutput,Ioutput
                int idCounter = getProcessor().getInputFluids();
                if(guiButton.id < idCounter) {
                    FMLCommonHandler.instance().showGuiScreen(new GuiFluidSorptions.Input(this, tile, guiButton.id));
                    return;
                }
                idCounter += getProcessor().getInputItems();
                if(guiButton.id < idCounter) {
                    FMLCommonHandler.instance().showGuiScreen(new GuiItemSorptions.Input(this, tile, guiButton.id));
                    return;
                }
                idCounter += getProcessor().getOutputFluids();
                if(guiButton.id < idCounter) {
                    FMLCommonHandler.instance().showGuiScreen(new GuiFluidSorptions.Output(this, tile, guiButton.id));
                    return;
                }
                idCounter += getProcessor().getOutputItems();
                if(guiButton.id < idCounter) {
                    FMLCommonHandler.instance().showGuiScreen(new GuiItemSorptions.Output(this, tile, guiButton.id));
                }
            }
        }
    }
}
