package igentuman.ncsteamadditions;

import igentuman.ncsteamadditions.config.*;
import igentuman.ncsteamadditions.machine.gui.GUIHandler;
import igentuman.ncsteamadditions.proxy.CommonProxy;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import igentuman.ncsteamadditions.util.*;
import igentuman.ncsteamadditions.villager.NCSVillagerHandler;
import net.minecraft.client.model.*;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Mod.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = NCSteamAdditions.MOD_ID, name = NCSteamAdditions.MOD_NAME, version = NCSteamAdditions.VERSION, acceptedMinecraftVersions = NCSteamAdditions.MCVERSION, dependencies = NCSteamAdditions.DEPENDENCIES, guiFactory = "igentuman.ncsteamadditions.config.NCSteamAdditionsConfigGUIFactory")
public class NCSteamAdditions
{
	public static final String MOD_NAME = "Steam Additions for NuclearCraft";
	public static final String MOD_ID = "ncsteamadditions";
	public static final String VERSION = "0.3.6";
	public static final String MCVERSION = "1.12.2";
	public static final String DEPENDENCIES = "required-after:nuclearcraft;required-after:nclegacy";

	
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
		MinecraftForge.EVENT_BUS.register(new NcsEventHandler());
		NCSVillagerHandler.initVillagerHouse();
		NCSVillagerHandler.initVillagerTrades();
	}

	@SubscribeEvent()
	public void onRenderLivingPre(RenderLivingEvent.Pre event)
	{
		if(event.getEntity().getEntityData().hasKey("headshot"))
		{
			ModelBase model = event.getRenderer().getMainModel();
			if(model instanceof ModelBiped)
				((ModelBiped)model).bipedHead.showModel = false;
			else if(model instanceof ModelVillager)
				((ModelVillager)model).villagerHead.showModel = false;
		}
	}

	@SubscribeEvent()
	public void onRenderLivingPost(RenderLivingEvent.Post event)
	{
		if(event.getEntity().getEntityData().hasKey("headshot"))
		{
			ModelBase model = event.getRenderer().getMainModel();
			if(model instanceof ModelBiped)
				((ModelBiped)model).bipedHead.showModel = true;
			else if(model instanceof ModelVillager)
				((ModelVillager)model).villagerHead.showModel = true;
		}
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event)
	{
		Util.getLogger().info("Server Load");
		
	}

	@EventHandler
	public void onIdMapping(FMLModIdMappingEvent idMappingEvent)
	{
		NCSteamAdditionsRecipes.refreshRecipeCaches();
	}
}
