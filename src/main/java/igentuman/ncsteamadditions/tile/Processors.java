package igentuman.ncsteamadditions.tile;

import igentuman.ncsteamadditions.NCSteamAdditions;
import igentuman.ncsteamadditions.machine.tile.TileNCSteamAdditionsProcessor;
import igentuman.ncsteamadditions.processors.SteamTransformer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Processors
{
	public static void register() 
	{
		GameRegistry.registerTileEntity(TileNCSteamAdditionsProcessor.TileSteamTransformer.class,
				new ResourceLocation(NCSteamAdditions.MOD_ID, SteamTransformer.code));
	}
}
