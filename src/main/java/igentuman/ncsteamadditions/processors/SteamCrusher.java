package igentuman.ncsteamadditions.processors;

import com.google.common.collect.Sets;
import igentuman.ncsteamadditions.block.Blocks;
import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import igentuman.ncsteamadditions.item.Items;
import igentuman.ncsteamadditions.jei.JEIHandler;
import igentuman.ncsteamadditions.jei.catergory.SteamCrusherCategory;
import igentuman.ncsteamadditions.machine.container.ContainerSteamCrusher;
import igentuman.ncsteamadditions.machine.gui.GuiSteamCrusher;
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

public class SteamCrusher extends AbstractProcessor {

    public SteamCrusher()
    {
        code = "steam_crusher";
        particle1 = "splash";
        particle2 = "reddust";
        GUID = 1;
        SIDEID = 1000 + GUID;
        inputItems = 1;
        inputFluids = 1;
        outputFluids = 0;
        outputItems = 1;
        craftingRecipe = new Object[] {
                "BRB", "CFC", "PRP",
                'B', net.minecraft.init.Items.BUCKET,
                'F', "chest",
                'C', RegistryHelper.itemStackFromRegistry(MOD_ID+":copper_sheet"),
                'R', net.minecraft.init.Blocks.PISTON,
                'P', net.minecraft.init.Items.DIAMOND_PICKAXE};
    }

    public String getBlockType()
    {
        return "nc_processor";
    }

    public Object getLocalGuiContainer(EntityPlayer player, TileEntity tile) {
        return new GuiSteamCrusher(player, (TileNCSProcessor) tile, this);
    }


    public Object getLocalGuiContainerConfig(EntityPlayer player, TileEntity tile) {
        return new GuiSteamCrusher.SideConfig(player, (TileNCSProcessor) tile, this);
    }

    public Object getGuiContainer(EntityPlayer player, TileEntity tile) {
        return new ContainerSteamCrusher(player, (TileNCSProcessor) tile);
    }


    public Object getGuiContainerConfig(EntityPlayer player, TileEntity tile) {
        return new ContainerMachineConfig(player, (TileNCSProcessor) tile);
    }

    public JEIBasicCategory getRecipeCategory(IGuiHelper guiHelper)
    {
        recipeHandler = new JEIHandler(this, NCSteamAdditionsRecipes.processorRecipeHandlers[GUID], Blocks.blocks[GUID], code, SteamCrusherCategory.SteamCrusherWrapper.class);
        return new SteamCrusherCategory(guiHelper,recipeHandler, this);
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
        return new TileSteamCrusher();
    }

    public Class getTileClass()
    {
        return TileSteamCrusher.class;
    }

    public static class TileSteamCrusher extends TileNCSProcessor {
        public TileSteamCrusher() {
            super(
                    ProcessorsRegistry.get().STEAM_CRUSHER.code,
                    ProcessorsRegistry.get().STEAM_CRUSHER.inputItems,
                    ProcessorsRegistry.get().STEAM_CRUSHER.inputFluids,
                    ProcessorsRegistry.get().STEAM_CRUSHER.outputItems,
                    ProcessorsRegistry.get().STEAM_CRUSHER.outputFluids,
                    ProcessorsRegistry.get().STEAM_CRUSHER.GUID
            );
        }
    }

    public SteamCrusher.RecipeHandler getRecipes()
    {
        return new SteamCrusher.RecipeHandler();
    }


    public class RecipeHandler extends AbstractProcessor.RecipeHandler {
        public RecipeHandler()
        {
            super(code, inputItems, inputFluids, outputItems, outputFluids);
        }

        @Override
        public void addRecipes()
        {
            addDustRecipes();
        }

        public void addDustRecipes() {
            String[] var1 = OreDictionary.getOreNames();
            int var2 = var1.length;
            FluidIngredient steam = fluidStack("low_pressure_steam", 250);
            Set<String> DUST_BL = Sets.newHashSet(new String[]{"Graphite"});
            for(int var3 = 0; var3 < var2; ++var3) {
                String oreEntry = var1[var3];
                String dust;
                if (oreEntry.startsWith("dust")) {
                    dust = oreEntry.substring(4);
                    if (DUST_BL.contains(dust)) {
                        continue;
                    }

                    String ingot = "ingot" + dust;
                    String gem = "gem" + dust;
                    String ore = "ore" + dust;
                    if (OreDictHelper.oreExists(ingot)) {
                        this.addRecipe(new Object[]{ingot, steam, oreEntry, 1.0D, 1.0D});
                    } else if (OreDictHelper.oreExists(gem)) {
                        this.addRecipe(new Object[]{gem,steam, oreEntry, 1.0D, 1.0D});
                    } else if (OreDictHelper.oreExists(ore)) {
                        this.addRecipe(new Object[]{ore,steam, oreEntry, 1.0D, 1.0D});
                    }
                }
            }

        }
    }
}
