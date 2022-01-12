package igentuman.ncsteamadditions.processors;

import com.google.common.collect.Lists;
import igentuman.ncsteamadditions.block.Blocks;
import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import igentuman.ncsteamadditions.item.Items;
import igentuman.ncsteamadditions.jei.JEIHandler;
import igentuman.ncsteamadditions.jei.catergory.SteamBoilerCategory;
import igentuman.ncsteamadditions.machine.container.ContainerSteamBoiler;
import igentuman.ncsteamadditions.machine.gui.GuiSteamBoiler;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import mezz.jei.api.IGuiHelper;
import nc.container.processor.ContainerMachineConfig;
import nc.integration.jei.JEIBasicCategory;
import nc.tile.processor.TileItemFluidProcessor;
import nc.util.FluidRegHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;

public class SteamBoiler extends AbstractProcessor {

    public static String code = "steam_boiler";

    public static String particle1 = "splash";

    public static String particle2 = "reddust";

    public final static int GUID = 2;

    public final static int SIDEID = 1000 + GUID;

    public static int inputItems = 1;

    public static int inputFluids = 1;

    public static int outputFluids = 1;

    public static int outputItems = 0;

    public static RecipeHandler recipes;

    public Object[] craftingRecipe = new Object[] {"PBP", "CFC", "PBP", 'P', "ingotLead", 'F', net.minecraft.init.Blocks.FURNACE, 'C', Items.items[0], 'B', net.minecraft.init.Blocks.CAULDRON};

    public int getInputItems() {
        return inputItems;
    }

    public int getInputFluids() {
        return inputFluids;
    }

    public int getOutputFluids() {
        return outputFluids;
    }

    public int getOutputItems() {
        return outputItems;
    }

    public Object[] getCraftingRecipe()
    {
       return this.craftingRecipe;
    }

    public JEIHandler recipeHandler;

    public int getGuid()
    {
        return GUID;
    }

    public int getSideid()
    {
        return SIDEID;
    }

    public String getCode()
    {
        return code;
    }

    public String getBlockType()
    {
        return "nc_processor";
    }

    public Object getLocalGuiContainer(EntityPlayer player, TileEntity tile) {
        return new GuiSteamBoiler(player,  (SteamBoiler.TileSteamBoiler)tile,this);
    }

    public Object getLocalGuiContainerConfig(EntityPlayer player, TileEntity tile) {
        return new GuiSteamBoiler.SideConfig(player,  (SteamBoiler.TileSteamBoiler)tile,this);
    }

    public Object getGuiContainer(EntityPlayer player, TileEntity tile) {
        return new ContainerSteamBoiler(player,  (SteamBoiler.TileSteamBoiler)tile);
    }

    public Object getGuiContainerConfig(EntityPlayer player, TileEntity tile) {
        return new ContainerMachineConfig(player,  (SteamBoiler.TileSteamBoiler)tile);
    }

    public JEIHandler getRecipeHandler()
    {
        return this.recipeHandler;
    }

    public JEIBasicCategory getRecipeCategory(IGuiHelper guiHelper)
    {
        recipeHandler = new JEIHandler(this, NCSteamAdditionsRecipes.processorRecipeHandlers[GUID], Blocks.blocks[SteamBoiler.GUID], SteamBoiler.code, SteamBoilerCategory.SteamBoilerWrapper.class);
        return new SteamBoilerCategory(guiHelper,recipeHandler, this);
    }

    public ProcessorType getType()
    {
        if(type == null) {
            type = new ProcessorType(code,GUID,particle1,particle2);
            type.setProcessor(this);
        }
        return type;
    }

    public Class getTileClass()
    {
        return TileSteamBoiler.class;
    }

    public static class TileSteamBoiler extends TileItemFluidProcessor
    {
        public TileSteamBoiler()
        {
            super(
                    code,
                    inputItems,
                    inputFluids,
                    outputItems,
                    outputFluids,
                    defaultItemSorptions(inputItems, outputItems, true),
                    defaultTankCapacities(20000, inputFluids, outputFluids),
                    defaultTankSorptions(inputFluids, outputFluids),
                    NCSteamAdditionsRecipes.validFluids[GUID],
                    NCSteamAdditionsConfig.processor_time[GUID],
                    0, true,
                    NCSteamAdditionsRecipes.processorRecipeHandlers[GUID],
                    GUID+1, 0
            );
        }
    }

    public SteamBoiler.RecipeHandler getRecipes()
    {
        return new SteamBoiler.RecipeHandler();
    }


    public class RecipeHandler extends AbstractProcessor.RecipeHandler {
        public RecipeHandler()
        {
            super(code, inputItems, inputFluids, outputItems, outputFluids);
        }

        ArrayList fuels = Lists.newArrayList(new String[]{"coal"});

        @Override
        public void addRecipes()
        {
            addBoilerRecipe("coal", "water","low_quality_steam", NCSteamAdditionsConfig.boilerConversion, 1.0);
            addBoilerRecipe("coal", "low_quality_steam","low_pressure_steam", NCSteamAdditionsConfig.boilerConversion-0.1, 1.5);
            addBoilerRecipe("coal","condensate_water","low_pressure_steam", NCSteamAdditionsConfig.boilerConversion, 3.0);
            addBoilerRecipe("coal","ic2distilled_water","low_pressure_steam", NCSteamAdditionsConfig.boilerConversion, 1.5);
            addBoilerRecipe("coal","preheated_water","low_pressure_steam", NCSteamAdditionsConfig.boilerConversion, 0.5);
            addBoilerRecipe("coal","ic2hot_water","low_pressure_steam", NCSteamAdditionsConfig.boilerConversion, 0.5);
            addBoilerRecipe("compressedCoal","low_pressure_steam","steam", NCSteamAdditionsConfig.boilerConversion, 3.0);
        }

        public void addBoilerRecipe(String fuel, String input, String output, Double rate, Double time)
        {
            if(FluidRegHelper.fluidExists(input) && FluidRegHelper.fluidExists(output)) {
                addRecipe(new Object[]{
                        fuel,
                        fluidStack(input, 100),
                        fluidStack(output, (int) Math.round(100 * rate))
                ,time});
            }
        }
    }
}
