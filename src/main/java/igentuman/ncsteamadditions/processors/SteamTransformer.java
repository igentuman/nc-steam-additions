package igentuman.ncsteamadditions.processors;

import igentuman.ncsteamadditions.block.Blocks;
import igentuman.ncsteamadditions.config.TransformerRecipesConfig;
import igentuman.ncsteamadditions.item.Items;
import igentuman.ncsteamadditions.jei.JEIHandler;
import igentuman.ncsteamadditions.jei.category.SteamTransformerCategory;
import igentuman.ncsteamadditions.machine.container.ContainerSteamTransformer;
import igentuman.ncsteamadditions.machine.gui.GuiSteamTransformer;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import igentuman.ncsteamadditions.tile.TileNCSProcessor;
import mezz.jei.api.IGuiHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.*;

public class SteamTransformer extends AbstractProcessor {

    public SteamTransformer()
    {
        code = "steam_transformer";
        particle1 = "splash";
        particle2 = "reddust";
        GUID = 2;
        SIDEID = 1000 + GUID;
        inputItems = 4;
        inputFluids = 1;
        outputFluids = 0;
        outputItems = 1;
        craftingRecipe = new Object[] {
                "PRP", "CFC", "PHP",
                'P', "chest",
                'F', net.minecraft.init.Items.BUCKET,
                'C', Items.items[0],
                'R', net.minecraft.init.Items.ENDER_EYE,
                'H', Blocks.otherBlocks[0]};
    }
    public Class getGuiClass()
    {
        return GuiSteamTransformer.class;
    }
    public String getBlockType()
    {
        return "nc_processor";
    }
    @SideOnly(Side.CLIENT)
    public Object getLocalGuiContainer(EntityPlayer player, TileEntity tile) {
        return new GuiSteamTransformer(player, (TileNCSProcessor) tile, this);
    }

    @SideOnly(Side.CLIENT)
    public Object getLocalGuiContainerConfig(EntityPlayer player, TileEntity tile) {
        return new GuiSteamTransformer.SideConfig(player, (TileNCSProcessor) tile, this);
    }

    public Object getGuiContainer(EntityPlayer player, TileEntity tile) {
        return new ContainerSteamTransformer(player, (TileNCSProcessor) tile);
    }


    public Object getGuiContainerConfig(EntityPlayer player, TileEntity tile) {
        return new ContainerMachineConfig(player, (TileNCSProcessor) tile);
    }

    public JEIBasicCategory getRecipeCategory(IGuiHelper guiHelper)
    {
        recipeHandler = new JEIHandler(this, NCSteamAdditionsRecipes.processorRecipeHandlers[getGuid()], Blocks.blocks[getGuid()], code, SteamTransformerCategory.SteamTransformerWrapper.class);
        return new SteamTransformerCategory(guiHelper,recipeHandler, this);
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
        return new TileSteamTransformer();
    }

    public Class getTileClass()
    {
        return TileSteamTransformer.class;
    }

    public static class TileSteamTransformer extends TileNCSProcessor {
        public TileSteamTransformer() {
            super(
                    ProcessorsRegistry.get().STEAM_TRANSFORMER.code,
                    ProcessorsRegistry.get().STEAM_TRANSFORMER.inputItems,
                    ProcessorsRegistry.get().STEAM_TRANSFORMER.inputFluids,
                    ProcessorsRegistry.get().STEAM_TRANSFORMER.outputItems,
                    ProcessorsRegistry.get().STEAM_TRANSFORMER.outputFluids,
                    ProcessorsRegistry.get().STEAM_TRANSFORMER.GUID
            );
        }
    }

    public RecipeHandler getRecipes()
    {
        return new RecipeHandler();
    }


    public class RecipeHandler extends AbstractProcessor.RecipeHandler {
        public RecipeHandler()
        {
            super(code, inputItems, inputFluids, outputItems, outputFluids);
        }

        @Override
        public void addRecipes()
        {
            for(int i = 0; i < TransformerRecipesConfig.transformerRecipes.length; i++) {
                Object[] recipe = TransformerRecipesConfig.parseTransformerRecipe(TransformerRecipesConfig.transformerRecipes[i]);
                if(recipe != null) {
                    addRecipe(recipe);
                }
            }
        }
    }
}
