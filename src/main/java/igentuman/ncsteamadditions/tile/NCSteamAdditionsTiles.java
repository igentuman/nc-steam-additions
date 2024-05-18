package igentuman.ncsteamadditions.tile;

import igentuman.ncsteamadditions.NCSteamAdditions;
import igentuman.ncsteamadditions.processors.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class NCSteamAdditionsTiles
{
	public static void register()
	{
		for(AbstractProcessor processor: ProcessorsRegistry.get().processors()) {
			GameRegistry.registerTileEntity(processor.getTileClass(),
					new ResourceLocation(NCSteamAdditions.MOD_ID, processor.getCode()));
		}
		GameRegistry.registerTileEntity(TilePipe.class,
				new ResourceLocation(NCSteamAdditions.MOD_ID, "pipe"));
	}
}
