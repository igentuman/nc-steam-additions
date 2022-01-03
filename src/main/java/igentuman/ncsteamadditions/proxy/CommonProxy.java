package igentuman.ncsteamadditions.proxy;

import java.util.Locale;
import crafttweaker.CraftTweakerAPI;
import igentuman.ncsteamadditions.block.Blocks;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import igentuman.ncsteamadditions.tile.NCSteamAdditionsTiles;
import nc.ModCheck;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLModIdMappingEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy
{

	public void preInit(FMLPreInitializationEvent preEvent) 
	{
		if (ModCheck.craftTweakerLoaded()) {
			CraftTweakerAPI.tweaker.loadScript(false, "ncsteamadditions_preinit");
		}

		Blocks.init();
		Blocks.register();
		NCSteamAdditionsTiles.register();
		MinecraftForge.EVENT_BUS.register(new NCSteamAdditionsRecipes());
	}
	
	public void init(FMLInitializationEvent event) 
	{
		NCSteamAdditionsRecipes.init();
	}

	public void onIdMapping(FMLModIdMappingEvent idMappingEvent) 
	{
		NCSteamAdditionsRecipes.refreshRecipeCaches();
	}
	
	
	public void registerFluidBlockRendering(Block block, String name) 
	{
		name = name.toLowerCase(Locale.ROOT);
	}
	
	public EntityPlayer getPlayerClient() 
	{
		return null;
	}
	
}
