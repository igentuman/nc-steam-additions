package igentuman.ncsteamadditions.reflection;

import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import nc.multiblock.heatExchanger.tile.TileCondenserTube;

public class TileCondenserTubeReflection {

    public static void update(TileCondenserTube inst) {
        if (!inst.getWorld().isRemote) {
            inst.setIsHeatExchangerOn();
            boolean wasProcessing = inst.isProcessing;
            inst.isProcessing = inst.isProcessing();
            boolean shouldUpdate = false;
            if (inst.isProcessing) {
                inst.process();
            }
            if(NCSteamAdditionsConfig.makeHXalive) {
                inst.pushFluid();
                inst.refreshRecipe();
                inst.refreshActivity();
            }
            if (wasProcessing != inst.isProcessing) {
                shouldUpdate = true;
            }

            if (shouldUpdate) {
                inst.markDirty();
            }
        }
    }
}
