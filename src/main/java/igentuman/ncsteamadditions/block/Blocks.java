package igentuman.ncsteamadditions.block;

import igentuman.ncsteamadditions.NCSteamAdditions;
import igentuman.ncsteamadditions.item.Items;
import igentuman.ncsteamadditions.machine.block.BlockNCSteamAdditionsProcessor;
import igentuman.ncsteamadditions.processors.*;
import nc.block.item.ItemBlockMeta;
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


public class Blocks
{

	public static Block[] blocks;
	public static Block[] otherBlocks;
	public static Block ore;
	public static Block ingot_block;

	public static void init() 
	{
		otherBlocks = new Block[1];
		otherBlocks[0] = withName(new BlockPipe(),"pipe");

		AbstractProcessor[] processors = ProcessorsRegistry.get().processors();
		blocks = new Block[processors.length];

		ore = withName(new BlockMeta.BlockOre(), "ore");
		ingot_block = withName(new BlockMeta.BlockIngot(), "ingot_block");

		for (AbstractProcessor processor: processors) {
			if(processor.getBlockType().equals("nc_processor")) {
				BlockNCSteamAdditionsProcessor processorBlock = new BlockNCSteamAdditionsProcessor(processor);
				blocks[processor.getGuid()] = withName(processorBlock);
			} else {
				BlockEnergyProcessor processorBlock = new BlockEnergyProcessor(processor);
				blocks[processor.getGuid()] = withName(processorBlock);
			}

		}
	}
	
	public static void register() 
	{
		registerBlock(ingot_block, new ItemBlockMeta(ingot_block, MetaEnums.IngotType.class, TextFormatting.AQUA));
		registerBlock(ore, new ItemBlockMeta(ore, MetaEnums.OreType.class, TextFormatting.AQUA));
		for(Block block: blocks) {
			registerBlock(block);
		}
		for(Block block: otherBlocks) {
			registerBlock(block,Items.getItemBlock(block));
		}
	}

	public static void registerRenders() 
	{
		int i;
		for(i = 0; i < MetaEnums.OreType.values().length; ++i) {
			registerRender(ore, i, MetaEnums.OreType.values()[i].getName());
		}

		for(i = 0; i < MetaEnums.IngotType.values().length; ++i) {
			registerRender(ingot_block, i, MetaEnums.IngotType.values()[i].getName());
		}

		for(Block block: blocks) {
			registerRender(block);
		}
		for(Block block: otherBlocks) {
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
		if(null == itemBlock) {
			registerBlock(block);
			return;
		}
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