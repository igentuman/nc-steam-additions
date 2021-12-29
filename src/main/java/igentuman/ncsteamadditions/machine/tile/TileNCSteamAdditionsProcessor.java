package igentuman.ncsteamadditions.machine.tile;

import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import igentuman.ncsteamadditions.processors.SteamTransformer;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import nc.tile.processor.TileItemFluidProcessor;

public class TileNCSteamAdditionsProcessor
{
	public static class TileSteamTransformer extends TileItemFluidProcessor
	{
		public TileSteamTransformer()
		{
			super(
					SteamTransformer.code,
					SteamTransformer.inputItems,
					SteamTransformer.inputFluids,
					SteamTransformer.outputItems,
					SteamTransformer.outputFluids,
					defaultItemSorptions(SteamTransformer.inputItems, SteamTransformer.outputItems, true),
					defaultTankCapacities(5000, SteamTransformer.inputFluids, SteamTransformer.outputFluids),
					defaultTankSorptions(SteamTransformer.inputFluids, SteamTransformer.outputFluids),
					NCSteamAdditionsRecipes.steam_transformer_valid_fluids,
					NCSteamAdditionsConfig.processor_time[SteamTransformer.GUID],
					0, true,
					NCSteamAdditionsRecipes.steam_transformer,
					SteamTransformer.GUID+1, 0
			);
		}
	}
}
