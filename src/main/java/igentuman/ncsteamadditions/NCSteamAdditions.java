package igentuman.ncsteamadditions;

import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import igentuman.ncsteamadditions.config.TransformerRecipesConfig;
import igentuman.ncsteamadditions.gui.GUIHandler;
import igentuman.ncsteamadditions.proxy.CommonProxy;
import igentuman.ncsteamadditions.util.Util;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLModIdMappingEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = NCSteamAdditions.MOD_ID, name = NCSteamAdditions.MOD_NAME, version = NCSteamAdditions.VERSION, acceptedMinecraftVersions = NCSteamAdditions.MCVERSION, dependencies = NCSteamAdditions.DEPENDENCIES, guiFactory = "igentuman.ncsteamadditions.config.NCSteamAdditionsConfigGUIFactory")
public class NCSteamAdditions
{
	public static final String MOD_NAME = "Steam Additions for NuclearCraft";
	public static final String MOD_ID = "ncsteamadditions";
	public static final String VERSION = "@VERSION@";
	public static final String MCVERSION = "1.12.2";
	public static final String DEPENDENCIES = "required-after:nuclearcraft@[2o.6.0,2o.6.1,2o.6.2]";

	
	@Instance(MOD_ID)
	public static NCSteamAdditions instance;
	
	
	@SidedProxy(clientSide = "igentuman.ncsteamadditions.proxy.ClientProxy", serverSide = "igentuman.ncsteamadditions.proxy.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Util.getLogger().info("PreInitialization");
		NCSteamAdditionsConfig.preInit();
		TransformerRecipesConfig.preInit();
		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		Util.getLogger().info("Initialization");
		proxy.init(event);
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GUIHandler());
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event)
	{
		Util.getLogger().info("Server Load");
		
	}

	@EventHandler
	public void onIdMapping(FMLModIdMappingEvent idMappingEvent) 
	{
		proxy.onIdMapping(idMappingEvent);
	}
}