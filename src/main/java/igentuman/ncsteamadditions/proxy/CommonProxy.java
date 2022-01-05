package igentuman.ncsteamadditions.proxy;

import java.util.Locale;
import crafttweaker.CraftTweakerAPI;
import igentuman.ncsteamadditions.NCSOreDictionary;
import igentuman.ncsteamadditions.NCSteamAdditions;
import igentuman.ncsteamadditions.block.Blocks;
import igentuman.ncsteamadditions.item.ItemCompressedCoal;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import igentuman.ncsteamadditions.tile.NCSteamAdditionsTiles;
import igentuman.ncsteamadditions.item.Items;
import nc.ModCheck;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLModIdMappingEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
@Mod.EventBusSubscriber(modid = NCSteamAdditions.MOD_ID)
public class CommonProxy
{

	public void preInit(FMLPreInitializationEvent preEvent) 
	{
		if (ModCheck.craftTweakerLoaded()) {
			CraftTweakerAPI.tweaker.loadScript(false, "ncsteamadditions_preinit");
		}
		Items.init();
		Blocks.init();
		Blocks.register();
		Items.register();
		NCSteamAdditionsTiles.register();
		NCSOreDictionary.register();
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

	@SubscribeEvent
	public static void modifyFuelBurnTime(FurnaceFuelBurnTimeEvent e) {
		if (e.getItemStack().getItem().getClass() == ItemCompressedCoal.class)
			e.setBurnTime(12800);
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
