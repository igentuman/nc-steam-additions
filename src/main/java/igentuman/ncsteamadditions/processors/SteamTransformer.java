package igentuman.ncsteamadditions.processors;

import igentuman.ncsteamadditions.block.Blocks;
import igentuman.ncsteamadditions.enums.ProcessorType;
import igentuman.ncsteamadditions.jei.JEIHandler;
import igentuman.ncsteamadditions.jei.NCSteamAdditionsJEI;
import igentuman.ncsteamadditions.jei.catergory.SteamTransformerCategory;
import igentuman.ncsteamadditions.jei.recipe.NCSteamAdditionsRecipeWrapper;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import igentuman.ncsteamadditions.recipes.SteamTransformerRecipes;
import mezz.jei.api.IGuiHelper;
import nc.init.NCBlocks;
import nc.integration.jei.JEIBasicCategory;
import nc.integration.jei.JEIMachineRecipeWrapper;
import nc.integration.jei.NCJEI;

public class SteamTransformer extends AbstractProcessor {

    public static String code = "steam_transformer";

    public static String PORCESSOR_ENUM = "STEAM_TRANSFORMER";

    public static String particle1 = "splash";

    public static String particle2 = "reddust";

    public final static int GUID = 1;

    public final static int SIDEID = 1000+ GUID;

    public static int inputItems = 4;

    public static int inputFluids = 1;

    public static int outputFluids = 0;

    public static int outputItems = 1;

    public static SteamTransformerRecipes recipes;

    public static Object craftingRecipe = new Object[] {"PRP", "CFC", "PHP", 'P', "plateElite", 'F', "chassis", 'C', NCBlocks.chemical_reactor, 'R', NCBlocks.rock_crusher, 'H', "ingotHardCarbon"};

    public JEIHandler recipeHandler;

    public JEIBasicCategory getRecipeCategory(IGuiHelper guiHelper)
    {
        recipeHandler = new JEIHandler(this, NCSteamAdditionsRecipes.steam_transformer, Blocks.blocks[SteamTransformer.GUID], SteamTransformer.code, NCSteamAdditionsRecipeWrapper.SteamTransformer.class);
        return new SteamTransformerCategory(guiHelper,recipeHandler);
    }
}
