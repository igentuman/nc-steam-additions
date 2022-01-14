package igentuman.ncsteamadditions.processors;

import igentuman.ncsteamadditions.block.Blocks;
import igentuman.ncsteamadditions.tab.NCSteamAdditionsTabs;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;

import java.lang.reflect.InvocationTargetException;

public class ProcessorType {
    private String name;
    private int id;

    public String getParticle1() {
        return particle1;
    }

    public String getParticle2() {
        return particle2;
    }

    private String particle1;
    private String particle2;

    private AbstractProcessor processor;

    public ProcessorType(String name, int id, String particle1, String particle2) {
        this.name = name;
        this.id = id;
        this.particle1 = particle1;
        this.particle2 = particle2;
    }

    public void setProcessor(AbstractProcessor proc)
    {
        processor = proc;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }

    public TileEntity getTile(){
        try {
            TileEntity tEntity = (TileEntity) processor.getTileClass().getDeclaredConstructor().newInstance();
            return tEntity;
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

