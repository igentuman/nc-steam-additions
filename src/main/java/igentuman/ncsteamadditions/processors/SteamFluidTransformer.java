package igentuman.ncsteamadditions.processors;

import igentuman.ncsteamadditions.block.Blocks;
import igentuman.ncsteamadditions.config.TransformerRecipesConfig;
import igentuman.ncsteamadditions.item.Items;
import igentuman.ncsteamadditions.jei.JEIHandler;
import igentuman.ncsteamadditions.jei.catergory.SteamFluidTransformerCategory;
import igentuman.ncsteamadditions.machine.container.ContainerSteamFluidTransformer;
import igentuman.ncsteamadditions.machine.gui.GuiDigitalTransformer;
import igentuman.ncsteamadditions.machine.gui.GuiSteamFluidTransformer;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import igentuman.ncsteamadditions.tile.TileNCSProcessor;
import mezz.jei.api.IGuiHelper;
import nc.container.processor.ContainerMachineConfig;
import nc.integration.jei.JEIBasicCategory;
import nc.util.RegistryHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import static igentuman.ncsteamadditions.NCSteamAdditions.MOD_ID;

public class SteamFluidTransformer extends AbstractProcessor {

    public SteamFluidTransformer()
    {
        code = "steam_fluid_transformer";
        particle1 = "splash";
        particle2 = "spell";
        GUID = 3;
        SIDEID = 1000 + GUID;
        inputItems = 0;
        inputFluids = 4;
        outputFluids = 1;
        outputItems = 0;
        craftingRecipe = new Object[] {
                "PRP", "CFC", "PHP",
                'P', net.minecraft.init.Items.BUCKET,
                'F', net.minecraft.init.Items.ENDER_EYE,
                'C', Items.items[0],
                'R', RegistryHelper.itemStackFromRegistry("minecraft:brewing_stand"),
                'H', Items.items[1]};

    }

    public Class getGuiClass()
    {
        return GuiSteamFluidTransformer.class;
    }
    public String getBlockType()
    {
        return "nc_processor";
    }

    public Object getLocalGuiContainer(EntityPlayer player, TileEntity tile) {
        return new GuiSteamFluidTransformer(player, (TileNCSProcessor) tile, this);
    }


    public Object getLocalGuiContainerConfig(EntityPlayer player, TileEntity tile) {
        return new GuiSteamFluidTransformer.SideConfig(player, (TileNCSProcessor) tile, this);
    }

    public Object getGuiContainer(EntityPlayer player, TileEntity tile) {
        return new ContainerSteamFluidTransformer(player, (TileNCSProcessor) tile);
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
                SteamFluidTransformerCategory.SteamFluidTransformerWrapper.class);
        return new SteamFluidTransformerCategory(guiHelper,recipeHandler, this);
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
                    ProcessorsRegistry.get().STEAM_FLUID_TRANSFORMER.code,
                    ProcessorsRegistry.get().STEAM_FLUID_TRANSFORMER.inputItems,
                    ProcessorsRegistry.get().STEAM_FLUID_TRANSFORMER.inputFluids,
                    ProcessorsRegistry.get().STEAM_FLUID_TRANSFORMER.outputItems,
                    ProcessorsRegistry.get().STEAM_FLUID_TRANSFORMER.outputFluids,
                    ProcessorsRegistry.get().STEAM_FLUID_TRANSFORMER.GUID
            );
        }
    }

    public SteamFluidTransformer.RecipeHandler getRecipes()
    {
        return new SteamFluidTransformer.RecipeHandler();
    }


    public class RecipeHandler extends AbstractProcessor.RecipeHandler {
        public RecipeHandler()
        {
            super(code, inputItems, inputFluids, outputItems, outputFluids);
        }

        @Override
        public void addRecipes()
        {
            for(int i = 0; i < TransformerRecipesConfig.fluidTransformerRecipes.length; i++) {
                Object[] recipe = TransformerRecipesConfig.parseFluidTransformerRecipe(TransformerRecipesConfig.fluidTransformerRecipes[i]);
                if(recipe != null) {
                    addRecipe(recipe);
                }
            }
        }
    }
}
