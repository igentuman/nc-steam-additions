package igentuman.ncsteamadditions.processors;

import igentuman.ncsteamadditions.block.Blocks;
import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import igentuman.ncsteamadditions.item.ItemCopperSheet;
import igentuman.ncsteamadditions.item.ItemPipe;
import igentuman.ncsteamadditions.item.Items;
import igentuman.ncsteamadditions.jei.JEIHandler;
import igentuman.ncsteamadditions.jei.catergory.SteamTurbineCategory;
import igentuman.ncsteamadditions.machine.container.ContainerSteamTurbine;
import igentuman.ncsteamadditions.machine.gui.GuiSteamTurbine;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import igentuman.ncsteamadditions.tile.TileSteamTurbine;
import mezz.jei.api.IGuiHelper;
import nc.container.processor.ContainerMachineConfig;
import nc.init.NCBlocks;
import nc.init.NCItems;
import nc.integration.jei.JEIBasicCategory;
import nc.tile.processor.TileItemFluidProcessor;
import nc.util.FluidRegHelper;
import nc.util.FluidStackHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class SteamTurbine extends AbstractProcessor {

    public static String code = "steam_turbine";

    public static String particle1 = "fireworksSpark";

    public static String particle2 = "reddust";

    public final static int GUID = 6;

    public final static int SIDEID = 1000 + GUID;

    public static int inputItems = 0;

    public static int inputFluids = 1;

    public static int outputFluids = 1;

    public static int outputItems = 0;

    public static RecipeHandler recipes;

    public Object[] craftingRecipe = new Object[] {"PRP", "CFC", "PHP", 'P', Items.items[ItemCopperSheet.regId], 'F', "solenoidCopper", 'C', "blockIron", 'R', Blocks.otherBlocks[0], 'H', net.minecraft.init.Items.COMPARATOR};

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
        return "energy_processor";
    }

    public Object getLocalGuiContainer(EntityPlayer player, TileEntity tile) {
        return new GuiSteamTurbine(player,  (TileSteamTurbine)tile,this);
    }

    public Object getLocalGuiContainerConfig(EntityPlayer player, TileEntity tile) {
        return new GuiSteamTurbine.SideConfig(player,  (TileSteamTurbine)tile,this);
    }

    public Object getGuiContainer(EntityPlayer player, TileEntity tile) {
        return new ContainerSteamTurbine(player,  (TileSteamTurbine)tile);
    }

    public Object getGuiContainerConfig(EntityPlayer player, TileEntity tile) {
        return new ContainerMachineConfig(player,  (TileSteamTurbine)tile);
    }

    public JEIHandler getRecipeHandler()
    {
        return this.recipeHandler;
    }

    public JEIBasicCategory getRecipeCategory(IGuiHelper guiHelper)
    {
        recipeHandler = new JEIHandler(this, NCSteamAdditionsRecipes.processorRecipeHandlers[SteamTurbine.GUID], Blocks.blocks[SteamTurbine.GUID], SteamTurbine.code, SteamTurbineCategory.SteamTurbineWrapper.class);
        return new SteamTurbineCategory(guiHelper,recipeHandler, this);
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
        return TileSteamTurbine.class;
    }


    public SteamTurbine.RecipeHandler getRecipes()
    {
        return new SteamTurbine.RecipeHandler();
    }


    public class RecipeHandler extends AbstractProcessor.RecipeHandler {
        public RecipeHandler()
        {
            super(code, inputItems, inputFluids, outputItems, outputFluids);
        }

        @Override
        public void addRecipes()
        {
            double x = NCSteamAdditionsConfig.turbineConversion;
            addTurbineRecipe("low_quality_steam","condensate_water", x, 0.5);
            addTurbineRecipe("low_pressure_steam","preheated_water", x, 1.0);
            addTurbineRecipe("steam","low_quality_steam", x, 1.0);
            addTurbineRecipe("ic2steam","low_quality_steam", x, 1.0);
        }

        public void addTurbineRecipe(String input, String output, Double rate, Double time)
        {
            if(FluidRegHelper.fluidExists(input) && FluidRegHelper.fluidExists(output)) {
                addRecipe(new Object[]{
                        fluidStack(input, 500),
                        fluidStack(output, (int) Math.round(500 * rate))
                        ,time});
            }
        }
    }
}
