package igentuman.ncsteamadditions.item;


import igentuman.ncsteamadditions.NCSteamAdditions;
import igentuman.ncsteamadditions.block.BlockPipe;
import nc.enumm.MetaEnums;
import nc.item.IInfoItem;
import nc.item.NCItemMeta;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class Items {

    public static Item[] items;

    public static ItemBlock getItemBlock(Block block)
    {
        if(BlockPipe.class == block.getClass()) {
            BlockPipe.PIPE_ITEM = new ItemPipe(block);
            return (ItemBlock) BlockPipe.PIPE_ITEM;
        }
        return null;
    }

    public static void init()
    {
        items = new Item[2];
        items[0] =  withName(new ItemCopperSheet(), "copper_sheet");
        items[1] =  withName(new ItemCompressedCoal(), "compressed_coal");
    }

    public static void register()
    {
        for(Item item: items) {
            registerItem(item);
        }
    }

    public static  Item withName(Item item, String name)
    {
        item.setTranslationKey(NCSteamAdditions.MOD_ID + "." + name).setRegistryName(new ResourceLocation(NCSteamAdditions.MOD_ID, name));
        return item;
    }

    public static String infoLine(String name)
    {
        return "item." + NCSteamAdditions.MOD_ID + "." + name + ".desc";
    }

    public static void registerItem(Item item)
    {
        ForgeRegistries.ITEMS.register(item);
    }

    public static void registerRenders()
    {
        for(Item item: items) {
            registerRender(item);
        }

    }

    public static void registerRender(Item item)
    {
        ModelLoader.setCustomModelResourceLocation(item, 0,new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    public static void registerRender(Item item, int meta, String type)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(new ResourceLocation(NCSteamAdditions.MOD_ID, "items/" + item.getRegistryName().getPath()), "type=" + type));
    }
}
