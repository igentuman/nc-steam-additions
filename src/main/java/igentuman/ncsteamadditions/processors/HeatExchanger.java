package igentuman.ncsteamadditions.processors;

import cofh.core.util.helpers.FluidHelper;
import igentuman.ncsteamadditions.block.BlockPipe;
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
import nc.util.FluidRegHelper;
import nc.util.RegistryHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

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
                'C', Items.items[0],
                'R', RegistryHelper.itemStackFromRegistry("minecraft:brewing_stand"),
                'H', Blocks.otherBlocks[0]};

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
    }

    public HeatExchanger.RecipeHandler getRecipes()
    {
        return new HeatExchanger.RecipeHandler();
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
