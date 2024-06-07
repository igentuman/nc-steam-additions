package igentuman.ncsteamadditions.processors;

import igentuman.ncsteamadditions.network.NCSProcessorUpdatePacket;
import igentuman.ncsteamadditions.tile.*;
import nc.container.ContainerFunction;
import nc.gui.*;
import nc.tile.processor.info.ProcessorContainerInfoImpl.BasicUpgradableProcessorContainerInfo;
import nc.tile.processor.info.builder.ProcessorContainerInfoBuilder;
import nc.util.ContainerInfoHelper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

import java.util.function.Supplier;

public class NCSProcessorContainerInfoBuilder<TILE extends TileEntity & INCSProcessor<TILE>> extends ProcessorContainerInfoBuilder<TILE, NCSProcessorUpdatePacket, NCSProcessorContainerInfo<TILE>, NCSProcessorContainerInfoBuilder<TILE>> {
	
	protected int[] speedUpgradeGuiXYWH = ContainerInfoHelper.standardSlot(132, 64);
	
	public NCSProcessorContainerInfoBuilder(String modId, String name, Class<TILE> tileClass, Supplier<TILE> tileSupplier, Class<? extends Container> containerClass, ContainerFunction<TILE> containerFunction, Class<? extends GuiContainer> guiClass, GuiInfoTileFunction<TILE> guiFunction) {
		super(modId, name, tileClass, tileSupplier, containerClass, containerFunction, guiClass, guiFunction);
	}
	
	@Override
	public NCSProcessorContainerInfo<TILE> buildContainerInfo() {
		return new NCSProcessorContainerInfo<>(modId, name, tileClass, containerClass, containerFunction, guiClass, guiFunction, configContainerFunction, configGuiFunction, recipeHandlerName, inputTankCapacity, outputTankCapacity, defaultProcessTime, defaultProcessPower, isGenerator, consumesInputs, losesProgress, ocComponentName, guiWH, itemInputGuiXYWH, fluidInputGuiXYWH, itemOutputGuiXYWH, fluidOutputGuiXYWH, playerGuiXY, progressBarGuiXYWHUV, energyBarGuiXYWHUV, machineConfigGuiXY, redstoneControlGuiXY, jeiCategoryEnabled, jeiCategoryUid, jeiTitle, jeiTexture, jeiBackgroundXYWH, jeiTooltipXYWH, jeiClickAreaXYWH, speedUpgradeGuiXYWH);
	}
	
	public NCSProcessorContainerInfoBuilder<TILE> setSpeedUpgradeSlot(int x, int y, int w, int h) {
		speedUpgradeGuiXYWH = new int[] {x, y, w, h};
		return getThis();
	}
	
	@Override
	public NCSProcessorContainerInfoBuilder<TILE> standardExtend(int x, int y) {
		super.standardExtend(x, y);
		
		speedUpgradeGuiXYWH[0] += x;
		speedUpgradeGuiXYWH[1] += y;
		
		return getThis();
	}
}
