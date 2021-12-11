package igentuman.ncsteamadditions.block;

import igentuman.ncsteamadditions.NCSteamAdditions;

import igentuman.ncsteamadditions.enums.BlockTypes.ProcessorType;

import igentuman.ncsteamadditions.machine.block.BlockNCSteamAdditionsProcessor;

import nc.block.item.NCItemBlock;
import nc.block.tile.ITileType;
import nc.util.InfoHelper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;


public class NCSteamAdditionsBlocks
{

	
	public static Block steamTransformer;

	public static void init() 
	{

		steamTransformer = withName(new BlockNCSteamAdditionsProcessor(ProcessorType.STEAM_TRANSFORMER));

	}
	
	public static void register() 
	{

		registerBlock(steamTransformer);

	}

	public static void registerRenders() 
	{

	
		registerRender(steamTransformer);

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