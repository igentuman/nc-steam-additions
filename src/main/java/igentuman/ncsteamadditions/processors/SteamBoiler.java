package igentuman.ncsteamadditions.processors;

import igentuman.ncsteamadditions.block.Blocks;
import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import igentuman.ncsteamadditions.jei.JEIHandler;
import igentuman.ncsteamadditions.jei.catergory.SteamBoilerCategory;
import igentuman.ncsteamadditions.machine.container.ContainerSteamBoiler;
import igentuman.ncsteamadditions.machine.gui.GuiSteamBoiler;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import igentuman.ncsteamadditions.tile.TileNCSProcessor;
import igentuman.ncsteamadditions.util.Util;
import mezz.jei.api.IGuiHelper;
import nc.container.processor.ContainerMachineConfig;
import nc.integration.jei.JEIBasicCategory;
import nc.util.FluidRegHelper;
import nc.util.RegistryHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

import static igentuman.ncsteamadditions.NCSteamAdditions.MOD_ID;
import static nc.block.property.BlockProperties.FACING_HORIZONTAL;

public class SteamBoiler extends AbstractProcessor {

   public SteamBoiler()
   {
       code = "steam_boiler";
       GUID = 0;
       SIDEID = 1000 + GUID;
       particle1 = "flame";
       particle2 = "smoke";
       inputItems = 1;
       inputFluids = 1;
       outputFluids = 1;
       outputItems = 0;
       craftingRecipe = new Object[] {
               "PBP", "CFC", "PBP",
               'P', "ingotZinc",
               'F', net.minecraft.init.Blocks.FURNACE,
               'C', RegistryHelper.itemStackFromRegistry(MOD_ID+":copper_sheet"),
               'B', RegistryHelper.itemStackFromRegistry("minecraft:cauldron")
       };
   }
    public void spawnParticles(BlockPos pos, Random rand, World world, IBlockState state)
    {
        Util.spawnParticleOnProcessor(state, world, pos, rand, state.getValue(FACING_HORIZONTAL),
                getType().getParticle1());
        Util.spawnParticleOnProcessor(state, world, pos.add(0,1.5D,0), rand, state.getValue(FACING_HORIZONTAL).getOpposite(),
                getType().getParticle2());
    }
    public Class getGuiClass()
    {
        return GuiSteamBoiler.class;
    }
    public String getSound()
    {
        return "boiler_on";
    }
    public boolean isFullCube() {return false;}
    public AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 2.0D, 1.0D);
    public boolean isCustomModel() {return true;}
    public String getBlockType()
    {
        return "nc_processor";
    }

    public Object getLocalGuiContainer(EntityPlayer player, TileEntity tile) {
        return new GuiSteamBoiler(player, (TileNCSProcessor) tile, this);
    }

    public Object getLocalGuiContainerConfig(EntityPlayer player, TileEntity tile) {
        return new GuiSteamBoiler.SideConfig(player, (TileNCSProcessor) tile, this);
    }

    public Object getGuiContainer(EntityPlayer player, TileEntity tile) {
        return new ContainerSteamBoiler(player, (TileNCSProcessor) tile);
    }

    public Object getGuiContainerConfig(EntityPlayer player, TileEntity tile) {
        return new ContainerMachineConfig(player, (TileNCSProcessor) tile);
    }

    public JEIBasicCategory getRecipeCategory(IGuiHelper guiHelper)
    {
        recipeHandler = new JEIHandler(this, NCSteamAdditionsRecipes.processorRecipeHandlers[GUID], Blocks.blocks[GUID], code, SteamBoilerCategory.SteamBoilerWrapper.class);
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

    public TileEntity getTile()
    {
        return new TileSteamBoiler();
    }

    public Class getTileClass()
    {
        return TileSteamBoiler.class;
    }

    public static class TileSteamBoiler extends TileNCSProcessor {
        public TileSteamBoiler() {
            super(
                    ProcessorsRegistry.get().STEAM_BOILER.code,
                    ProcessorsRegistry.get().STEAM_BOILER.inputItems,
                    ProcessorsRegistry.get().STEAM_BOILER.inputFluids,
                    ProcessorsRegistry.get().STEAM_BOILER.outputItems,
                    ProcessorsRegistry.get().STEAM_BOILER.outputFluids,
                    ProcessorsRegistry.get().STEAM_BOILER.GUID
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

        @Override
        public void addRecipes()
        {
            double x = NCSteamAdditionsConfig.boilerConversion;
            addBoilerRecipe("coal", "water","low_quality_steam", x, 1.0);
            addBoilerRecipe("coal", "low_quality_steam","low_pressure_steam", x-0.1, 1.5);
            addBoilerRecipe("coal","condensate_water","low_pressure_steam", x, 3.0);
            addBoilerRecipe("coal","ic2distilled_water","low_pressure_steam", x, 1.5);
            addBoilerRecipe("coal","preheated_water","low_pressure_steam", x, 1.0);
            addBoilerRecipe("coal","ic2hot_water","low_pressure_steam", x, 1.0);
            addBoilerRecipe("compressedCoal","low_pressure_steam","steam", x, 2.0);
        }

        public void addBoilerRecipe(String fuel, String input, String output, Double rate, Double time)
        {
            if(FluidRegHelper.fluidExists(input) && FluidRegHelper.fluidExists(output)) {
                addRecipe(new Object[]{
                        fuel,
                        fluidStack(input, 500),
                        fluidStack(output, (int) Math.round(500 * rate))
                ,time});
            }
        }
    }
}
