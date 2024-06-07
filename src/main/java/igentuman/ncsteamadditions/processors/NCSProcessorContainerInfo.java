package igentuman.ncsteamadditions.processors;

import igentuman.ncsteamadditions.network.NCSProcessorUpdatePacket;
import igentuman.ncsteamadditions.tile.*;
import nc.container.ContainerFunction;
import nc.gui.GuiFunction;
import nc.tile.internal.inventory.ItemSorption;
import nc.tile.processor.info.ProcessorContainerInfo;
import nc.util.ContainerInfoHelper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

import java.util.List;

public class NCSProcessorContainerInfo<TILE extends TileEntity & INCSProcessor<TILE>> extends ProcessorContainerInfo<TILE, NCSProcessorUpdatePacket, NCSProcessorContainerInfo<TILE>> {
	
	public final int speedUpgradeSlot;
	
	public final int[] speedUpgradeGuiXYWH;
	
	public final int[] speedUpgradeStackXY;
	
	public final int speedUpgradeSorptionButtonID;
	
	protected NCSProcessorContainerInfo(String modId, String name, Class<TILE> tileClass, Class<? extends Container> containerClass, ContainerFunction<TILE> containerFunction, Class<? extends GuiContainer> guiClass, GuiFunction<TILE> guiFunction, ContainerFunction<TILE> configContainerFunction, GuiFunction<TILE> configGuiFunction, String recipeHandlerName, int inputTankCapacity, int outputTankCapacity, double defaultProcessTime, double defaultProcessPower, boolean isGenerator, boolean consumesInputs, boolean losesProgress, String ocComponentName, int[] guiWH, List<int[]> itemInputGuiXYWH, List<int[]> fluidInputGuiXYWH, List<int[]> itemOutputGuiXYWH, List<int[]> fluidOutputGuiXYWH, int[] playerGuiXY, int[] progressBarGuiXYWHUV, int[] energyBarGuiXYWHUV, int[] machineConfigGuiXY, int[] redstoneControlGuiXY, boolean jeiCategoryEnabled, String jeiCategoryUid, String jeiTitle, String jeiTexture, int[] jeiBackgroundXYWH, int[] jeiTooltipXYWH, int[] jeiClickAreaXYWH, int[] speedUpgradeGuiXYWH) {
		super(modId, name, tileClass, containerClass, containerFunction, guiClass, guiFunction, configContainerFunction, configGuiFunction, recipeHandlerName, inputTankCapacity, outputTankCapacity, defaultProcessTime, defaultProcessPower, isGenerator, consumesInputs, losesProgress, ocComponentName, guiWH, itemInputGuiXYWH, fluidInputGuiXYWH, itemOutputGuiXYWH, fluidOutputGuiXYWH, playerGuiXY, progressBarGuiXYWHUV, energyBarGuiXYWHUV, machineConfigGuiXY, redstoneControlGuiXY, jeiCategoryEnabled, jeiCategoryUid, jeiTitle, jeiTexture, jeiBackgroundXYWH, jeiTooltipXYWH, jeiClickAreaXYWH);
		
		speedUpgradeSlot = itemInputSize + itemOutputSize;
		
		this.speedUpgradeGuiXYWH = speedUpgradeGuiXYWH;
		
		speedUpgradeStackXY = ContainerInfoHelper.stackXY(speedUpgradeGuiXYWH);
		
		speedUpgradeSorptionButtonID = itemInputSize + fluidInputSize + itemOutputSize + fluidOutputSize;
	}
	
	@Override
	public int getInventorySize() {
		return itemInputSize + itemOutputSize + 1;
	}
	
	@Override
	public List<ItemSorption> defaultItemSorptions() {
		List<ItemSorption> itemSorptions = super.defaultItemSorptions();
		itemSorptions.add(ItemSorption.IN);
		return itemSorptions;
	}
}
