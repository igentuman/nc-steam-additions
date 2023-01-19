package igentuman.ncsteamadditions.processors;

import com.google.common.collect.Sets;
import igentuman.ncsteamadditions.block.Blocks;
import igentuman.ncsteamadditions.jei.JEIHandler;
import igentuman.ncsteamadditions.jei.catergory.SteamCompactorCategory;
import igentuman.ncsteamadditions.machine.container.ContainerSteamCompactor;
import igentuman.ncsteamadditions.machine.gui.GuiSteamCompactor;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import igentuman.ncsteamadditions.tile.TileNCSProcessor;
import mezz.jei.api.IGuiHelper;
import nc.container.processor.ContainerMachineConfig;
import nc.integration.jei.JEIBasicCategory;
import nc.recipe.ingredient.FluidIngredient;
import nc.util.OreDictHelper;
import nc.util.RegistryHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Set;

import static igentuman.ncsteamadditions.NCSteamAdditions.MOD_ID;

public class SteamCompactor extends AbstractProcessor {

    public SteamCompactor()
    {
        code = "steam_compactor";
        particle1 = "splash";
        particle2 = "reddust";
        GUID = 4;
        SIDEID = 1000 + GUID;
        inputItems = 1;
        inputFluids = 1;
        outputFluids = 0;
        outputItems = 1;
        craftingRecipe = new Object[] {
                "BRB", "RFR", "PRP",
                'B', net.minecraft.init.Items.BUCKET,
                'F', "chest",
                'R', net.minecraft.init.Blocks.PISTON,
                'P', RegistryHelper.itemStackFromRegistry(MOD_ID+":copper_sheet")};
    }

    public Object getLocalGuiContainer(EntityPlayer player, TileEntity tile) {
        return new GuiSteamCompactor(player, (TileNCSProcessor) tile, this);
    }

    public String getSound()
    {
        return "compactor_on";
    }
    public Class getGuiClass()
    {
        return GuiSteamCompactor.class;
    }
    public Object getLocalGuiContainerConfig(EntityPlayer player, TileEntity tile) {
       return new GuiSteamCompactor.SideConfig(player, (TileNCSProcessor) tile, this);
    }

    public Object getGuiContainer(EntityPlayer player, TileEntity tile) {
        return new ContainerSteamCompactor(player, (TileNCSProcessor) tile);
    }


    public Object getGuiContainerConfig(EntityPlayer player, TileEntity tile) {
        return new ContainerMachineConfig(player, (TileNCSProcessor) tile);
    }

    public JEIBasicCategory getRecipeCategory(IGuiHelper guiHelper)
    {
        recipeHandler = new JEIHandler(this, NCSteamAdditionsRecipes.processorRecipeHandlers[GUID], Blocks.blocks[GUID], code, SteamCompactorCategory.SteamCompactorWrapper.class);
        return new SteamCompactorCategory(guiHelper,recipeHandler, this);
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
        return new TileSteamCompactor();
    }

    public Class getTileClass()
    {
        return TileSteamCompactor.class;
    }

    public static class TileSteamCompactor extends TileNCSProcessor {
        public TileSteamCompactor() {
            super(
                    ProcessorsRegistry.get().STEAM_COMPACTOR.code,
                    ProcessorsRegistry.get().STEAM_COMPACTOR.inputItems,
                    ProcessorsRegistry.get().STEAM_COMPACTOR.inputFluids,
                    ProcessorsRegistry.get().STEAM_COMPACTOR.outputItems,
                    ProcessorsRegistry.get().STEAM_COMPACTOR.outputFluids,
                    ProcessorsRegistry.get().STEAM_COMPACTOR.GUID
            );
        }
    }

    public SteamCompactor.RecipeHandler getRecipes()
    {
        return new SteamCompactor.RecipeHandler();
    }

    public String getBlockType()
    {
        return "nc_processor";
    }


    public class RecipeHandler extends AbstractProcessor.RecipeHandler {
        public RecipeHandler()
        {
            super(code, inputItems, inputFluids, outputItems, outputFluids);
        }
        @Override
        public void addRecipes()
        {
            addRecipe(new Object[]{
                    oreStack("coal", 4),
                    fluidStack("low_pressure_steam", 250),
                    oreStack("compressedCoal", 1)}
            );
            addRecipe(new Object[]{
                    oreStack("copperSheet", 1),
                    fluidStack("low_pressure_steam", 250),
                    oreStack("wireCopper", 1)}
            );
            addPlatePressingRecipes();
        }

        public void addPlatePressingRecipes() {
            String[] var1 = OreDictionary.getOreNames();
            int var2 = var1.length;
            FluidIngredient steam = fluidStack("low_pressure_steam", 250);
            Set<String> PLATE_BLACKLIST = Sets.newHashSet(new String[]{"Graphite"});
            for(int var3 = 0; var3 < var2; ++var3) {
                String ore = var1[var3];
                String plate;
                if (ore.startsWith("plate")) {
                    plate = ore.substring(5);
                    if (PLATE_BLACKLIST.contains(plate)) {
                        continue;
                    }

                    String ingot = "ingot" + plate;
                    String gem = "gem" + plate;
                    if (OreDictHelper.oreExists(ingot)) {
                        this.addRecipe(new Object[]{ingot, steam, ore, 1.0D, 1.0D});
                    } else if (OreDictHelper.oreExists(gem)) {
                        this.addRecipe(new Object[]{gem,steam, ore, 1.0D, 1.0D});
                    }
                }

                if (ore.startsWith("plateDense")) {
                    plate = "plate" + ore.substring(10);
                    if (OreDictHelper.oreExists(plate)) {
                        this.addRecipe(new Object[]{oreStack(plate, 9), steam, ore, 2.0D, 2.0D});
                    }
                }
            }

        }
    }
}
