package igentuman.ncsteamadditions.machine.tile;


import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import igentuman.ncsteamadditions.gui.GUI_ID;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import nc.tile.processor.TileItemFluidProcessor;


public class TileNCSteamAdditionsProcessor
{

	public static class TileSteamTransformer extends TileItemFluidProcessor
	{
		public TileSteamTransformer()
		{

			super("steam_transformer", 1, 3, 3, 0, defaultItemSorptions(1, 3, true), defaultTankCapacities(5000, 3, 0),
					defaultTankSorptions(3, 0), NCSteamAdditionsRecipes.steam_transformer_valid_fluids, NCSteamAdditionsConfig.processor_time[0],
					0, true, NCSteamAdditionsRecipes.steam_transformer, GUI_ID.STEAM_TRANSFORMER, 0);
		}
	}
}
