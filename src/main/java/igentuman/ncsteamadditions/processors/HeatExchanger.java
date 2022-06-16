package igentuman.ncsteamadditions.processors;

import igentuman.ncsteamadditions.block.Blocks;
import igentuman.ncsteamadditions.item.Items;
import igentuman.ncsteamadditions.jei.JEIHandler;
import igentuman.ncsteamadditions.jei.catergory.HeatExchangerCategory;
import igentuman.ncsteamadditions.machine.container.ContainerHeatExchanger;
import igentuman.ncsteamadditions.machine.gui.GuiHeatExchanger;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import igentuman.ncsteamadditions.tile.TileNCSProcessor;
import mezz.jei.api.IGuiHelper;
import nc.container.processor.ContainerMachineConfig;
import nc.init.NCCoolantFluids;
import nc.integration.jei.JEIBasicCategory;
import nc.recipe.ingredient.FluidIngredient;
import nc.util.RegistryHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import static igentuman.ncsteamadditions.NCSteamAdditions.MOD_ID;

public class HeatExchanger extends AbstractProcessor {

    public HeatExchanger()
    {
        code = "heat_exchanger";
        particle1 = "splash";
        particle2 = "spell";
        GUID = 9;
        SIDEID = 1000 + GUID;
        inputItems = 0;
        inputFluids = 2;
        outputFluids = 2;
        outputItems = 0;
        craftingRecipe = new Object[] {
                "PRP", "CFC", "PHP",
                'P', net.minecraft.init.Blocks.IRON_BARS,
                'F', RegistryHelper.itemStackFromRegistry("minecraft:cauldron"),
                'C', RegistryHelper.itemStackFromRegistry(MOD_ID+":copper_sheet"),
                'R', RegistryHelper.itemStackFromRegistry("minecraft:brewing_stand"),
                'H', Blocks.otherBlocks[0]};

    }
    public AxisAlignedBB getAABB()
    {
        return AABB;
    }
    public String getBlockType()
    {
        return "nc_processor";
    }

    public Object getLocalGuiContainer(EntityPlayer player, TileEntity tile) {
        return new GuiHeatExchanger(player, (TileNCSProcessor) tile, this);
    }


    public Object getLocalGuiContainerConfig(EntityPlayer player, TileEntity tile) {
        return new GuiHeatExchanger.SideConfig(player, (TileNCSProcessor) tile, this);
    }

    public Object getGuiContainer(EntityPlayer player, TileEntity tile) {
        return new ContainerHeatExchanger(player, (TileNCSProcessor) tile);
    }


    public Object getGuiContainerConfig(EntityPlayer player, TileEntity tile) {
        return new ContainerMachineConfig(player, (TileNCSProcessor) tile);
    }

    public JEIBasicCategory getRecipeCategory(IGuiHelper guiHelper)
    {
        recipeHandler = new JEIHandler(this,
                NCSteamAdditionsRecipes.processorRecipeHandlers[GUID],
                Blocks.blocks[GUID],
                code,
                HeatExchangerCategory.HeatExchangerWrapper.class);
        return new HeatExchangerCategory(guiHelper,recipeHandler, this);
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
        return new TileFluidTransformer();
    }

    public Class getTileClass()
    {
        return TileFluidTransformer.class;
    }

    public static class TileFluidTransformer extends TileNCSProcessor {
        public TileFluidTransformer() {
            super(
                    ProcessorsRegistry.get().HEAT_EXCHANGER.code,
                    ProcessorsRegistry.get().HEAT_EXCHANGER.inputItems,
                    ProcessorsRegistry.get().HEAT_EXCHANGER.inputFluids,
                    ProcessorsRegistry.get().HEAT_EXCHANGER.outputItems,
                    ProcessorsRegistry.get().HEAT_EXCHANGER.outputFluids,
                    ProcessorsRegistry.get().HEAT_EXCHANGER.GUID
            );
        }

        public void update()
        {
            super.update();
            FluidStack coolant = getTanks().get(0).getFluid();
            FluidStack heater = getTanks().get(1).getFluid();
            if (heater != null && !heater.getUnlocalizedName().matches("(.*)hot(.*)")) {
                getTanks().get(0).setFluid(heater);
                getTanks().get(1).setFluid(coolant);
            }
        }
    }


    public HeatExchanger.RecipeHandler getRecipes()
    {
        return new HeatExchanger.RecipeHandler();
    }
    public boolean isFullCube() {return false;}
    public AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.5D, 1.0D);
    public boolean isCustomModel() {return true;}
    public String getSound()
    {
        return "heat_exchanger_on";
    }
    public class RecipeHandler extends AbstractProcessor.RecipeHandler {
        public RecipeHandler()
        {
            super(code, inputItems, inputFluids, outputItems, outputFluids);
        }

        @Override
        public void addRecipes()
        {
            FluidIngredient hot = fluidStack("nak_hot", 10);
            FluidIngredient notHot = fluidStack("nak", 10);
            this.addRecipe(fluidStack("exhaust_steam", 100),hot, fluidStack("low_pressure_steam", 100),notHot);

            for(int i = 1; i < NCCoolantFluids.COOLANTS.size(); ++i) {
                hot = fluidStack((String)NCCoolantFluids.COOLANTS.get(i) + "_nak_hot",10);
                notHot = fluidStack((String)NCCoolantFluids.COOLANTS.get(i) + "_nak",10);
                this.addRecipe(fluidStack("exhaust_steam", 100),hot, fluidStack("low_pressure_steam", 100),notHot);
            }
        }
    }
}
