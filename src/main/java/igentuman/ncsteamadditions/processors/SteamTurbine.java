package igentuman.ncsteamadditions.processors;

import igentuman.ncsteamadditions.block.Blocks;
import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import igentuman.ncsteamadditions.item.ItemCopperSheet;
import igentuman.ncsteamadditions.item.Items;
import igentuman.ncsteamadditions.jei.JEIHandler;
import igentuman.ncsteamadditions.jei.catergory.SteamTurbineCategory;
import igentuman.ncsteamadditions.machine.container.ContainerSteamTurbine;
import igentuman.ncsteamadditions.machine.gui.GuiSteamTurbine;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import igentuman.ncsteamadditions.tile.TileDigitalTransformer;
import igentuman.ncsteamadditions.tile.TileSteamTurbine;
import mezz.jei.api.IGuiHelper;
import nc.container.processor.ContainerMachineConfig;
import nc.init.NCBlocks;
import nc.integration.jei.JEIBasicCategory;
import nc.util.FluidRegHelper;
import nc.util.RegistryHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import static igentuman.ncsteamadditions.NCSteamAdditions.MOD_ID;

public class SteamTurbine extends AbstractProcessor {

   public SteamTurbine()
   {
       code = "steam_turbine";
       particle1 = "fireworksSpark";
       particle2 = "reddust";
       GUID = 6;
       SIDEID = 1000 + GUID;
       inputItems = 0;
       inputFluids = 1;
       outputFluids = 1;
       outputItems = 0;
       craftingRecipe = new Object[] {
               "PRP", "CFC", "PHP",
               'P', RegistryHelper.itemStackFromRegistry(MOD_ID+":copper_sheet"),
               'F', "solenoidCopper",
               'C', "blockZinc",
               'R', Blocks.otherBlocks[0],
               'H', NCBlocks.voltaic_pile_basic
       };
   }

    public String getBlockType()
    {
        return "energy_processor";
    }

    public Object getLocalGuiContainer(EntityPlayer player, TileEntity tile) {
        return new GuiSteamTurbine(player, (TileSteamTurbine) tile, this);
    }

    public Object getLocalGuiContainerConfig(EntityPlayer player, TileEntity tile) {
        return new GuiSteamTurbine.SideConfig(player, (TileSteamTurbine) tile, this);
    }

    public Object getGuiContainer(EntityPlayer player, TileEntity tile) {
        return new ContainerSteamTurbine(player, (TileSteamTurbine) tile);
    }

    public Object getGuiContainerConfig(EntityPlayer player, TileEntity tile) {
        return new ContainerMachineConfig(player, (TileSteamTurbine) tile);
    }

    public JEIBasicCategory getRecipeCategory(IGuiHelper guiHelper)
    {
        recipeHandler = new JEIHandler(this, NCSteamAdditionsRecipes.processorRecipeHandlers[GUID], Blocks.blocks[GUID], code, SteamTurbineCategory.SteamTurbineWrapper.class);
        return new SteamTurbineCategory(guiHelper,recipeHandler, this);
    }

    public Class getTileClass() {
        return TileSteamTurbine.class;
    }

    public ProcessorType getType()
    {
        if(type == null) {
            type = new ProcessorType(code,GUID,particle1,particle2);
            type.setProcessor(this);
        }
        return type;
    }

    public TileEntity getTile()
    {
        return new TileSteamTurbine();
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
