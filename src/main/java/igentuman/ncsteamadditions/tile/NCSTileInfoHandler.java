package igentuman.ncsteamadditions.tile;

import igentuman.ncsteamadditions.machine.container.ContainerSteamBoiler;
import igentuman.ncsteamadditions.machine.gui.GuiSteamBoiler;
import igentuman.ncsteamadditions.processors.NCSProcessorContainerInfoBuilder;
import igentuman.ncsteamadditions.processors.SteamBoiler.TileSteamBoiler;
import nc.*;
import nc.container.processor.ContainerProcessorImpl.ContainerManufactory;
import nc.gui.processor.GuiProcessorImpl.GuiManufactory;
import nc.tile.processor.TileProcessorImpl.TileManufactory;

import static nc.handler.TileInfoHandler.registerProcessorInfo;

public class NCSTileInfoHandler {
	
	public static void preInit() {
		registerProcessorInfo(new NCSProcessorContainerInfoBuilder<>(Global.MOD_ID, "steam_boiler", TileSteamBoiler.class, TileSteamBoiler::new, ContainerSteamBoiler.class, ContainerSteamBoiler::new, GuiSteamBoiler.class, GuiSteamBoiler::new).setParticles("flame", "smoke").setDefaultProcessTime(processor_time[0]).setDefaultProcessPower(processor_power[0]).setItemInputSlots(standardSlot(56, 35)).setItemOutputSlots(bigSlot(112, 31)));
	}
	
	public static void init() {
		if (ModCheck.jeiLoaded()) {
		
		}
	}
}
