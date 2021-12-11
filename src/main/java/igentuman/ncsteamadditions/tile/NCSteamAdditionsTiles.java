package igentuman.ncsteamadditions.tile;

import igentuman.ncsteamadditions.NCSteamAdditions;
import igentuman.ncsteamadditions.machine.tile.TileNCSteamAdditionsProcessor;
import igentuman.ncsteamadditions.util.Util;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class NCSteamAdditionsTiles
{
	public static void register() 
	{
		//machines
		GameRegistry.registerTileEntity(TileNCSteamAdditionsProcessor.TileSteamTransformer.class,new ResourceLocation(NCSteamAdditions.MOD_ID,"steam_transformer"));
	}
}
