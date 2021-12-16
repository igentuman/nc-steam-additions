package igentuman.ncsteamadditions.processors;

import igentuman.ncsteamadditions.enums.ProcessorType;
import igentuman.ncsteamadditions.recipes.SteamTransformerRecipes;
import nc.init.NCBlocks;

public class SteamTransformer extends AbstractProcessor {

    public static String code = "steam_transformer";

    public static String PORCESSOR_ENUM = "STEAM_TRANSFORMER";

    public static String particle1 = "splash";

    public static String particle2 = "reddust";

    public static int GUID = 1;

    public static int SIDEID = 1000+ GUID;

    public static int inputItems = 4;

    public static int inputFluids = 1;

    public static int outputFluids = 0;

    public static int outputItems = 1;

    public static SteamTransformerRecipes recipes;

    public static Object craftingRecipe = new Object[] {"PRP", "CFC", "PHP", 'P', "plateElite", 'F', "chassis", 'C', NCBlocks.chemical_reactor, 'R', NCBlocks.rock_crusher, 'H', "ingotHardCarbon"};
}
