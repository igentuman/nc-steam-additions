package igentuman.ncsteamadditions.processors;

import igentuman.ncsteamadditions.block.Blocks;
import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import igentuman.ncsteamadditions.item.Items;
import igentuman.ncsteamadditions.jei.JEIHandler;
import igentuman.ncsteamadditions.jei.catergory.SteamBlenderCategory;
import igentuman.ncsteamadditions.machine.container.ContainerSteamBlender;
import igentuman.ncsteamadditions.machine.gui.GuiSteamBlender;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import mezz.jei.api.IGuiHelper;
import nc.container.processor.ContainerMachineConfig;
import nc.integration.jei.JEIBasicCategory;
import nc.recipe.ingredient.EmptyItemIngredient;
import nc.tile.processor.TileItemFluidProcessor;
import nc.util.FluidRegHelper;
import nc.util.FluidStackHelper;
import nc.util.RegistryHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class SteamBlender extends AbstractProcessor {

    public static String code = "steam_blender";

    public static String particle1 = "splash";

    public static String particle2 = "endRod";

    public final static int GUID = 7;

    public final static int SIDEID = 1000 + GUID;

    public static int inputItems = 3;

    public static int inputFluids = 1;

    public static int outputFluids = 1;

    public static int outputItems = 0;

    public static RecipeHandler recipes;

    public Object[] craftingRecipe = new Object[] {"PPP", "CFC", "RHR", 'P', "chest", 'F', net.minecraft.init.Blocks.FURNACE, 'C', Items.items[0], 'R', net.minecraft.init.Items.ENDER_EYE, 'H',  RegistryHelper.itemStackFromRegistry("minecraft:cauldron")};

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

    public String getBlockType()
    {
        return "nc_processor";
    }

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

    public Object getLocalGuiContainer(EntityPlayer player, TileEntity tile) {
        return new GuiSteamBlender(player,  (SteamBlender.TileSteamBlender)tile,this);
    }

    public Object getLocalGuiContainerConfig(EntityPlayer player, TileEntity tile) {
        return new GuiSteamBlender.SideConfig(player,  (SteamBlender.TileSteamBlender)tile,this);
    }

    public Object getGuiContainer(EntityPlayer player, TileEntity tile) {
        return new ContainerSteamBlender(player,  (SteamBlender.TileSteamBlender)tile);
    }

    public Object getGuiContainerConfig(EntityPlayer player, TileEntity tile) {
        return new ContainerMachineConfig(player,  (SteamBlender.TileSteamBlender)tile);
    }

    public JEIHandler getRecipeHandler()
    {
        return this.recipeHandler;
    }

    public JEIBasicCategory getRecipeCategory(IGuiHelper guiHelper)
    {
        recipeHandler = new JEIHandler(this, NCSteamAdditionsRecipes.processorRecipeHandlers[getGuid()], Blocks.blocks[getGuid()], SteamBlender.code, SteamBlenderCategory.SteamBlenderWrapper.class);
        return new SteamBlenderCategory(guiHelper,recipeHandler, this);
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
        return TileSteamBlender.class;
    }

    public static class TileSteamBlender extends TileItemFluidProcessor
    {
        public TileSteamBlender()
        {
            super(
                    code,
                    inputItems,
                    inputFluids,
                    outputItems,
                    outputFluids,
                    defaultItemSorptions(inputItems, outputItems, true),
                    defaultTankCapacities(10000, inputFluids, outputFluids),
                    defaultTankSorptions(inputFluids, outputFluids),
                    NCSteamAdditionsRecipes.validFluids[GUID],
                    NCSteamAdditionsConfig.processor_time[GUID],
                    0, true,
                    NCSteamAdditionsRecipes.processorRecipeHandlers[GUID],
                    GUID+1, 0
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
            addRecipe("dustIron",oreStack("dustCoal",3),new EmptyItemIngredient(),
                    fluidStack("steam", 250),
                    fluidStack("steel", FluidStackHelper.INGOT_VOLUME)
            );
            addRecipe(oreStack("dustCopper",3),"dustLead",new EmptyItemIngredient(),
                    fluidStack("steam", 250),
                    fluidStack("bronze", FluidStackHelper.INGOT_VOLUME)
            );
            if(FluidRegHelper.fluidExists("ic2distilled_water")) {
                addRecipe("blockSnow", new EmptyItemIngredient(), new EmptyItemIngredient(),
                        fluidStack("low_pressure_steam", 250),
                        fluidStack("ic2distilled_water", FluidStackHelper.BUCKET_VOLUME)
                );
            }
        }
    }
}
