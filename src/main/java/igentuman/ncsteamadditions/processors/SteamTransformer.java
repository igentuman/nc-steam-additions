package igentuman.ncsteamadditions.processors;

import igentuman.ncsteamadditions.block.Blocks;
import igentuman.ncsteamadditions.enums.ProcessorType;
import igentuman.ncsteamadditions.jei.JEIHandler;
import igentuman.ncsteamadditions.jei.catergory.SteamTransformerCategory;
import igentuman.ncsteamadditions.jei.recipe.NCSteamAdditionsRecipeWrapper;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import igentuman.ncsteamadditions.recipes.SteamTransformerRecipes;
import mezz.jei.api.IGuiHelper;
import nc.init.NCBlocks;
import nc.integration.jei.JEIBasicCategory;
import igentuman.ncsteamadditions.machine.tile.TileNCSteamAdditionsProcessor.TileSteamTransformer;

public class SteamTransformer extends AbstractProcessor {

    public static String code = "steam_transformer";

    public static String PORCESSOR_ENUM = "STEAM_TRANSFORMER";

    public static String particle1 = "splash";

    public static String particle2 = "reddust";

    public final static int GUID = 0;

    public final static int SIDEID = 1000+ GUID;

    public static int inputItems = 4;

    public static int inputFluids = 1;

    public static int outputFluids = 0;

    public static int outputItems = 1;

    public static SteamTransformerRecipes recipes;

    public Object[] craftingRecipe = new Object[] {"PRP", "CFC", "PHP", 'P', "plateElite", 'F', "chassis", 'C', NCBlocks.chemical_reactor, 'R', NCBlocks.rock_crusher, 'H', "ingotHardCarbon"};

    public Object[] getCraftingRecipe()
    {
       return this.craftingRecipe;
    }

    public JEIHandler recipeHandler;

    public JEIHandler getRecipeHandler()
    {
        return this.recipeHandler;
    }

    public JEIBasicCategory getRecipeCategory(IGuiHelper guiHelper)
    {
        recipeHandler = new JEIHandler(this, NCSteamAdditionsRecipes.steam_transformer, Blocks.blocks[SteamTransformer.GUID], SteamTransformer.code, NCSteamAdditionsRecipeWrapper.SteamTransformer.class);
        return new SteamTransformerCategory(guiHelper,recipeHandler);
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
        return TileSteamTransformer.class;
    }
}
