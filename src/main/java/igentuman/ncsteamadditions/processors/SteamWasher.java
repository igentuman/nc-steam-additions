package igentuman.ncsteamadditions.processors;

import igentuman.ncsteamadditions.block.Blocks;
import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import igentuman.ncsteamadditions.item.Items;
import igentuman.ncsteamadditions.jei.JEIHandler;
import igentuman.ncsteamadditions.jei.catergory.SteamWasherCategory;
import igentuman.ncsteamadditions.machine.container.ContainerSteamWasher;
import igentuman.ncsteamadditions.machine.gui.GuiItemFluidMachine;
import igentuman.ncsteamadditions.machine.gui.GuiSteamWasher;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import igentuman.ncsteamadditions.tile.TileNCSProcessor;
import mezz.jei.api.IGuiHelper;
import nc.container.processor.ContainerMachineConfig;
import nc.integration.jei.JEIBasicCategory;
import nc.recipe.ingredient.FluidIngredient;
import nc.tile.ITileGui;
import nc.tile.processor.TileItemFluidProcessor;
import nc.util.OreDictHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.oredict.OreDictionary;

public class SteamWasher extends AbstractProcessor {

    public static String code = "steam_washer";

    public static String particle1 = "splash";

    public static String particle2 = "reddust";

    public final static int GUID = 5;

    public final static int SIDEID = 1000 + GUID;

    public static int inputItems = 1;

    public static int inputFluids = 1;

    public static int outputFluids = 0;

    public static int outputItems = 1;

    public static RecipeHandler recipes;

    public Object[] craftingRecipe = new Object[] {"PRP", "CFC", "PHP", 'P', "chest", 'F', net.minecraft.init.Items.WATER_BUCKET, 'C', Items.items[0], 'R', net.minecraft.init.Items.ENDER_PEARL, 'H', Items.items[1]};

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
    public JEIHandler getRecipeHandler()
    {
        return this.recipeHandler;
    }

    public Object getLocalGuiContainer(EntityPlayer player, TileEntity tile) {
        return new GuiSteamWasher(player, (SteamWasher.TileSteamWasher) tile, this);
    }


    public Object getLocalGuiContainerConfig(EntityPlayer player, TileEntity tile) {
        return new GuiSteamWasher.SideConfig(player, (SteamWasher.TileSteamWasher) tile, this);
    }

    public Object getGuiContainer(EntityPlayer player, TileEntity tile) {
        return new ContainerSteamWasher(player, (SteamWasher.TileSteamWasher) tile);
    }


    public Object getGuiContainerConfig(EntityPlayer player, TileEntity tile) {
        return new ContainerMachineConfig(player, (SteamWasher.TileSteamWasher) tile);
    }

    public JEIBasicCategory getRecipeCategory(IGuiHelper guiHelper)
    {
        recipeHandler = new JEIHandler(this, NCSteamAdditionsRecipes.processorRecipeHandlers[GUID], Blocks.blocks[SteamWasher.GUID], SteamWasher.code, SteamWasherCategory.SteamWasherWrapper.class);
        return new SteamWasherCategory(guiHelper,recipeHandler, this);
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
        return TileSteamWasher.class;
    }

    public static class TileSteamWasher extends TileNCSProcessor
    {
        public TileSteamWasher()
        {
            super(
                    code,
                    inputItems,
                    inputFluids,
                    outputItems,
                    outputFluids,
                    defaultItemSorptions(inputItems, outputItems, true),
                    defaultTankCapacities(5000, inputFluids, outputFluids),
                    defaultTankSorptions(inputFluids, outputFluids),
                    NCSteamAdditionsRecipes.validFluids[GUID],
                    NCSteamAdditionsConfig.processor_time[GUID],
                    0, true,
                    NCSteamAdditionsRecipes.processorRecipeHandlers[GUID],
                    GUID+1, 0,0,10
            );
        }
    }

    public SteamWasher.RecipeHandler getRecipes()
    {
        return new SteamWasher.RecipeHandler();
    }


    public class RecipeHandler extends AbstractProcessor.RecipeHandler {
        public RecipeHandler()
        {
            super(code, inputItems, inputFluids, outputItems, outputFluids);
        }

        @Override
        public void addRecipes()
        {
            addRecipe(oreStack("dustUranium", 3),
                    fluidStack("steam", 250),
                    oreStack("ingotUranium235", 2)
            );
            addIsotopeRecipes();
        }

        public void addIsotopeRecipes() {
            String[] var1 = OreDictionary.getOreNames();
            int var2 = var1.length;
            FluidIngredient steam = fluidStack("low_pressure_steam", 125);
            for(int var3 = 0; var3 < var2; ++var3) {
                String oreEntry = var1[var3];
                String isotope;
                if (oreEntry.startsWith("ingot")) {
                    isotope = oreEntry.substring(5);

                    String oxide = "ingot" + isotope + "Oxide";
                    String nitride = "ingot" + isotope + "Nitride";
                    String carbide = "ingot" + isotope + "Carbide";
                    String za = "ingot" + isotope + "ZA";
                    if (OreDictHelper.oreExists(oxide)) {
                        this.addRecipe(new Object[]{oxide, steam, oreEntry, 1.0D});
                    } else if(OreDictHelper.oreExists(nitride)){
                        this.addRecipe(new Object[]{nitride, steam, oreEntry, 1.0D});
                    } else if(OreDictHelper.oreExists(carbide)){
                        this.addRecipe(new Object[]{carbide, steam, oreEntry, 1.0D});
                    } else if(OreDictHelper.oreExists(za)){
                        this.addRecipe(new Object[]{za, steam, oreEntry, 1.0D});
                    }
                }
            }

        }
    }
}
