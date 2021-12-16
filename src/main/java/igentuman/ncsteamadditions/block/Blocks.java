package igentuman.ncsteamadditions.block;

import igentuman.ncsteamadditions.NCSteamAdditions;
import igentuman.ncsteamadditions.enums.ProcessorType;
import igentuman.ncsteamadditions.machine.block.BlockNCSteamAdditionsProcessor;
import igentuman.ncsteamadditions.processors.*;
import nc.block.item.NCItemBlock;
import nc.block.tile.ITileType;
import nc.util.InfoHelper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.lang.reflect.InvocationTargetException;

public class Blocks
{

	public static Block[] blocks;

	public static AbstractProcessor getProcessorObject(String name)
	{
		Class cTile = null;
		try {
			cTile = Class.forName(name);
		} catch (ClassNotFoundException e) {
			return null;
		}
		try {
			return (AbstractProcessor) cTile.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			return null;
		}
	}

	public static void init() 
	{
		for (String processorName: ProcessorsList.processors) {
			AbstractProcessor processor = getProcessorObject(processorName);
			blocks[processor.GUID] = withName(new BlockNCSteamAdditionsProcessor(processor));
		}
	}
	
	public static void register() 
	{
		for(Block block: blocks) {
			registerBlock(block);
		}
	}

	public static void registerRenders() 
	{
		for(Block block: blocks) {
			registerRender(block);
		}
	}
	
	public static Block withName(Block block, String name) {
		return block.setTranslationKey(NCSteamAdditions.MOD_ID + "." + name).setRegistryName(new ResourceLocation(NCSteamAdditions.MOD_ID, name));
	}
	
	public static <T extends Block & ITileType> Block withName(T block) {
		return block.setTranslationKey(NCSteamAdditions.MOD_ID + "." + block.getTileName()).setRegistryName(new ResourceLocation(NCSteamAdditions.MOD_ID, block.getTileName()));
	}
	
	public static String fixedLine(String name) {
		return "tile." + NCSteamAdditions.MOD_ID + "." + name + ".fixd";
	}
	
	public static String infoLine(String name) {
		return "tile." + NCSteamAdditions.MOD_ID + "." + name + ".desc";
	}
	
	public static void registerBlock(Block block, TextFormatting[] fixedColors, String[] fixedTooltip, TextFormatting infoColor, String... tooltip) {
		ForgeRegistries.BLOCKS.register(block);
		ForgeRegistries.ITEMS.register(new NCItemBlock(block, fixedColors, fixedTooltip, infoColor, tooltip).setRegistryName(block.getRegistryName()));
	}
	
	public static void registerBlock(Block block, TextFormatting fixedColor, String[] fixedTooltip, TextFormatting infoColor, String... tooltip) {
		ForgeRegistries.BLOCKS.register(block);
		ForgeRegistries.ITEMS.register(new NCItemBlock(block, fixedColor, fixedTooltip, infoColor, tooltip).setRegistryName(block.getRegistryName()));
	}
	
	public static void registerBlock(Block block, String... tooltip) {
		registerBlock(block, TextFormatting.RED, InfoHelper.EMPTY_ARRAY, TextFormatting.AQUA, tooltip);
	}
	
	public static void registerBlock(Block block, ItemBlock itemBlock) {
		ForgeRegistries.BLOCKS.register(block);
		ForgeRegistries.ITEMS.register(itemBlock.setRegistryName(block.getRegistryName()));
	}
	
	public static void registerRender(Block block) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}
	
	public static void registerRender(Block block, int meta, String type) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, new ModelResourceLocation(new ResourceLocation(NCSteamAdditions.MOD_ID, block.getRegistryName().getPath()), "type=" + type));
	}
}