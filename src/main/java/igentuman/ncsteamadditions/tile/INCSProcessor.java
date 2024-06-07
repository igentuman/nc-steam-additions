package igentuman.ncsteamadditions.tile;

import igentuman.ncsteamadditions.network.NCSProcessorUpdatePacket;
import igentuman.ncsteamadditions.processors.NCSProcessorContainerInfo;
import nc.init.NCItems;
import nc.tile.ITileInstallable;
import nc.tile.processor.IProcessor;
import nc.tile.processor.info.ProcessorContainerInfoImpl;
import nc.util.PrimitiveFunction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraftforge.items.*;

public interface INCSProcessor<TILE extends TileEntity & INCSProcessor<TILE>> extends IProcessor<TILE, NCSProcessorUpdatePacket, NCSProcessorContainerInfo<TILE>>, ITileInstallable {
	
	default boolean tryInstall(EntityPlayer player, EnumHand hand, EnumFacing facing) {
		ItemStack held = player.getHeldItem(hand);
		
		PrimitiveFunction.ToBooleanBiFunction<Integer, ItemStack> tryInstallUpgrade = (x, y) -> {
			if (held.isItemEqual(y)) {
				IItemHandler inv = getTile().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
				if (inv != null && inv.isItemValid(x, held)) {
					if (player.isSneaking()) {
						player.setHeldItem(EnumHand.MAIN_HAND, inv.insertItem(x, held, false));
						return true;
					}
					else {
						if (inv.insertItem(x, y, false).isEmpty()) {
							player.getHeldItem(hand).shrink(1);
							return true;
						}
					}
				}
			}
			return false;
		};
		
		NCSProcessorContainerInfo<TILE> info = getContainerInfo();
		return tryInstallUpgrade.applyAsBoolean(info.speedUpgradeSlot, new ItemStack(NCItems.upgrade, 1, 0)) || tryInstallUpgrade.applyAsBoolean(info.energyUpgradeSlot, new ItemStack(NCItems.upgrade, 1, 1));
	}
}
