package igentuman.ncsteamadditions.machine.container;

import igentuman.ncsteamadditions.machine.gui.GuiItemFluidMachine;
import igentuman.ncsteamadditions.processors.DigitalTransformer;
import igentuman.ncsteamadditions.processors.ProcessorsRegistry;
import igentuman.ncsteamadditions.processors.SteamTurbine;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import igentuman.ncsteamadditions.tile.TileNCSProcessor;
import nc.container.processor.ContainerItemFluidProcessor;
import nc.container.slot.SlotFurnace;
import nc.container.slot.SlotProcessorInput;
import nc.container.slot.SlotSpecificInput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class ContainerSteamTurbine extends ContainerItemFluidProcessor
{
	public ContainerSteamTurbine(EntityPlayer player, TileNCSProcessor tileEntity)
	{
		super(player, tileEntity, NCSteamAdditionsRecipes.processorRecipeHandlers[ProcessorsRegistry.get().STEAM_TURBINE.GUID]);
		int idCounter = 0;
		int x = GuiItemFluidMachine.inputItemsLeft;
		SteamTurbine processor = ProcessorsRegistry.get().STEAM_TURBINE;
		if(processor.inputItems > 0) {
			for (int i = 0; i < processor.inputItems; i++) {
				addSlotToContainer(new SlotProcessorInput(tileEntity, recipeHandler, idCounter, x, GuiItemFluidMachine.inputItemsTop));
				x += GuiItemFluidMachine.cellSpan;
				idCounter++;
			}
		}

		x = 152;
		if(processor.outputItems > 0) {
			for (int i = 0; i < processor.outputItems; i++) {
				addSlotToContainer(new SlotFurnace(player, tileEntity, idCounter, x, GuiItemFluidMachine.inputItemsTop));
				x += GuiItemFluidMachine.cellSpan;
				idCounter++;
			}
		}

		addSlotToContainer(new SlotSpecificInput(tileEntity, idCounter, 152, 64, SPEED_UPGRADE));

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(player.inventory, j + 9*i + 9, 8 + 18*j, 84 + 18*i));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(player.inventory, i, 8 + 18*i, 142));
		}
	}
}