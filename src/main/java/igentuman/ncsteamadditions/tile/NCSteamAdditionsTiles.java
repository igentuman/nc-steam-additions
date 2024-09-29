package igentuman.ncsteamadditions.tile;

import igentuman.ncsteamadditions.NCSteamAdditions;
import igentuman.ncsteamadditions.processors.*;
import nc.block.tile.info.BlockSimpleTileInfo;
import nc.tab.NCTabs;
import nc.tile.dummy.TileMachineInterface;
import nc.tile.generator.TileSolarPanel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static igentuman.ncsteamadditions.NCSteamAdditions.MOD_ID;
import static nc.handler.TileInfoHandler.registerBlockTileInfo;
import static nc.init.NCTiles.registerTile;

public class NCSteamAdditionsTiles
{
	public static void registerSolar()
	{
		registerBlockTileInfo(new BlockSimpleTileInfo(MOD_ID, "quantum_solar_panel", TileNCSolarPanel.Quantum.class, TileNCSolarPanel.Quantum::new, NCTabs.machine));
		registerBlockTileInfo(new BlockSimpleTileInfo(MOD_ID, "hyper_solar_panel", TileNCSolarPanel.Hyper.class, TileNCSolarPanel.Hyper::new, NCTabs.machine));
		registerBlockTileInfo(new BlockSimpleTileInfo(MOD_ID, "fusion_solar_panel", TileNCSolarPanel.Fusion.class, TileNCSolarPanel.Fusion::new, NCTabs.machine));
		registerBlockTileInfo(new BlockSimpleTileInfo(MOD_ID, "nova_solar_panel", TileNCSolarPanel.Nova.class, TileNCSolarPanel.Nova::new, NCTabs.machine));
		registerBlockTileInfo(new BlockSimpleTileInfo(MOD_ID, "stellar_solar_array", TileNCSolarPanel.Stellar.class, TileNCSolarPanel.Stellar::new, NCTabs.machine));
		registerBlockTileInfo(new BlockSimpleTileInfo(MOD_ID, "photon_amplifier_panel", TileNCSolarPanel.Photon.class, TileNCSolarPanel.Photon::new, NCTabs.machine));
		registerBlockTileInfo(new BlockSimpleTileInfo(MOD_ID, "nebula_solar_collector", TileNCSolarPanel.Nebula.class, TileNCSolarPanel.Nebula::new, NCTabs.machine));
		registerBlockTileInfo(new BlockSimpleTileInfo(MOD_ID, "graviton_solar_harvester", TileNCSolarPanel.Graviton.class, TileNCSolarPanel.Graviton::new, NCTabs.machine));
	}

	public static void register()
	{
		registerTile(MOD_ID, "quantum_solar_panel", TileNCSolarPanel.Quantum.class);
		registerTile(MOD_ID, "hyper_solar_panel", TileNCSolarPanel.Hyper.class);
		registerTile(MOD_ID, "fusion_solar_panel", TileNCSolarPanel.Fusion.class);
		registerTile(MOD_ID, "nova_solar_panel", TileNCSolarPanel.Nova.class);
		registerTile(MOD_ID, "stellar_solar_array", TileNCSolarPanel.Stellar.class);
		registerTile(MOD_ID, "photon_amplifier_panel", TileNCSolarPanel.Photon.class);
		registerTile(MOD_ID, "nebula_solar_collector", TileNCSolarPanel.Nebula.class);
		registerTile(MOD_ID, "graviton_solar_harvester", TileNCSolarPanel.Graviton.class);
		for(AbstractProcessor processor: ProcessorsRegistry.get().processors()) {
			GameRegistry.registerTileEntity(processor.getTileClass(),
					new ResourceLocation(MOD_ID, processor.getCode()));
		}
		GameRegistry.registerTileEntity(TilePipe.class,
				new ResourceLocation(MOD_ID, "pipe"));
	}
}
