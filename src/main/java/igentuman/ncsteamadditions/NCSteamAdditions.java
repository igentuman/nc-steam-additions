package igentuman.ncsteamadditions;

import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import igentuman.ncsteamadditions.gui.GUIHandler;
import igentuman.ncsteamadditions.proxy.CommonProxy;
import igentuman.ncsteamadditions.util.Util;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLModIdMappingEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = NCSteamAdditions.MOD_ID, name = NCSteamAdditions.MOD_NAME, version = NCSteamAdditions.VERSION, acceptedMinecraftVersions = NCSteamAdditions.MCVERSION, dependencies = "after:nuclearcraft", guiFactory = "igentuman.ncsteamadditions.config.NCSteamAdditionsConfigGUIFactory")
public class NCSteamAdditions
{
	public static final String MOD_NAME = "Quantum Minecraft Dynamics";
	public static final String MOD_ID = "ncsteamadditions";
	public static final String VERSION = "@VERSION@";
	public static final String MCVERSION = "1.12.2";

	
	@Instance(MOD_ID)
	public static NCSteamAdditions instance;
	
	
	@SidedProxy(clientSide = "igentuman.ncsteamadditions.proxy.ClientProxy", serverSide = "igentuman.ncsteamadditions.proxy.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Util.getLogger().info("PreInitialization");
		NCSteamAdditionsConfig.preInit();
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