package igentuman.ncsteamadditions.enums;

import igentuman.ncsteamadditions.NCSteamAdditions;
import igentuman.ncsteamadditions.block.Blocks;
import igentuman.ncsteamadditions.tab.NCSteamAdditionsTabs;
import igentuman.ncsteamadditions.tab.TabNCSteamAdditionsBlocks;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;

import java.lang.reflect.InvocationTargetException;

public class ProcessorType {
    private String name;
    private int id;

    public ProcessorType(String name, int id, String particle1, String particle2) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }

    public TileEntity getTile(){
        Class cTile = null;
        try {
            cTile = Class.forName(name);
        } catch (ClassNotFoundException e) {
            return null;
        }
        try {
            return (TileEntity) cTile.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }

    public Block getBlock() {
        return Blocks.blocks[id];
    }

    public CreativeTabs getCreativeTab() {
        return NCSteamAdditionsTabs.ITEMS;
    }
}

