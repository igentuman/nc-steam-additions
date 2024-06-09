package igentuman.ncsteamadditions.processors;

import igentuman.ncsteamadditions.block.Blocks;
import igentuman.ncsteamadditions.item.Items;
import igentuman.ncsteamadditions.jei.JEIHandler;
import igentuman.ncsteamadditions.jei.category.SteamWasherCategory;
import igentuman.ncsteamadditions.machine.container.ContainerSteamWasher;
import igentuman.ncsteamadditions.machine.gui.GuiSteamWasher;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import igentuman.ncsteamadditions.tile.TileNCSProcessor;
import mezz.jei.api.IGuiHelper;
import nc.recipe.ingredient.FluidIngredient;
import nc.util.OreDictHelper;
import nclegacy.container.ContainerMachineConfigLegacy;
import nclegacy.jei.JEIBasicCategoryLegacy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.*;
import net.minecraftforge.oredict.OreDictionary;

public class SteamWasher extends AbstractProcessor {

    public SteamWasher()
    {
        code = "steam_washer";
        particle1 = "splash";
        particle2 = "reddust";
        GUID = 5;
        SIDEID = 1000 + GUID;
        inputItems = 1;
        inputFluids = 1;
        outputFluids = 0;
        outputItems = 1;
        craftingRecipe = new Object[] {
                "PRP", "CFC", "PHP",
                'P', "chest",
                'F', net.minecraft.init.Items.WATER_BUCKET,
                'C', Items.items[0],
                'R', net.minecraft.init.Items.ENDER_PEARL,
                'H', Blocks.otherBlocks[0]};
    }
    public Class getGuiClass()
    {
        return GuiSteamWasher.class;
    }
    public String getBlockType()
    {
        return "nc_processor";
    }
    @SideOnly(Side.CLIENT)
    public Object getLocalGuiContainer(EntityPlayer player, TileEntity tile) {
        return new GuiSteamWasher(player, (TileNCSProcessor) tile, this);
    }

    @SideOnly(Side.CLIENT)
    public Object getLocalGuiContainerConfig(EntityPlayer player, TileEntity tile) {
        return new GuiSteamWasher.SideConfig(player, (TileNCSProcessor) tile, this);
    }

    public Object getGuiContainer(EntityPlayer player, TileEntity tile) {
        return new ContainerSteamWasher(player, (TileNCSProcessor) tile);
    }


    public Object getGuiContainerConfig(EntityPlayer player, TileEntity tile) {
        return new ContainerMachineConfigLegacy(player, (TileNCSProcessor) tile);
    }


    public JEIBasicCategoryLegacy getRecipeCategory(IGuiHelper guiHelper)
    {
        recipeHandler = new JEIHandler(this, NCSteamAdditionsRecipes.processorRecipeHandlers[GUID], Blocks.blocks[GUID], code, SteamWasherCategory.SteamWasherWrapper.class);
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

    public TileEntity getTile()
    {
        return new TileSteamWasher();
    }

    public Class getTileClass()
    {
        return TileSteamWasher.class;
    }

    public static class TileSteamWasher extends TileNCSProcessor {
        public TileSteamWasher() {
            super(
                    ProcessorsRegistry.get().STEAM_WASHER.code,
                    ProcessorsRegistry.get().STEAM_WASHER.inputItems,
                    ProcessorsRegistry.get().STEAM_WASHER.inputFluids,
                    ProcessorsRegistry.get().STEAM_WASHER.outputItems,
                    ProcessorsRegistry.get().STEAM_WASHER.outputFluids,
                    ProcessorsRegistry.get().STEAM_WASHER.GUID
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
