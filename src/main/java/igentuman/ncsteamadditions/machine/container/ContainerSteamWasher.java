package igentuman.ncsteamadditions.machine.container;

import igentuman.ncsteamadditions.machine.gui.GuiSteamWasher;
import igentuman.ncsteamadditions.processors.SteamWasher;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import igentuman.ncsteamadditions.tile.TileNCSProcessor;
import nc.container.processor.ContainerItemFluidProcessor;
import nc.container.slot.SlotFurnace;
import nc.container.slot.SlotProcessorInput;
import nc.container.slot.SlotSpecificInput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class ContainerSteamWasher extends ContainerItemFluidProcessor
{

	public ContainerSteamWasher(EntityPlayer player, TileNCSProcessor tileEntity)
	{
		super(player, tileEntity, NCSteamAdditionsRecipes.processorRecipeHandlers[SteamWasher.GUID]);

		int x = GuiSteamWasher.inputFluidsLeft;
		int idCounter = 0;

		if(SteamWasher.inputFluids > 0) {
			for(int i = 0; i < SteamWasher.inputFluids; i++) {
				//addSlotToContainer(new SlotProcessorInput(tileEntity, recipeHandler, idCounter, x, GuiSteamWasher.inputFluidsTop));
				//x += GuiSteamWasher.cellSpan;
				//idCounter++;
			}
		}

		x = GuiSteamWasher.inputItemsLeft;
		if(SteamWasher.inputItems > 0) {
			for (int i = 0; i < SteamWasher.inputItems; i++) {
				addSlotToContainer(new SlotProcessorInput(tileEntity, recipeHandler, idCounter, x, GuiSteamWasher.inputItemsTop));
				x += GuiSteamWasher.cellSpan;
				idCounter++;
			}
		}

		x = 152;
		if(SteamWasher.outputFluids > 0) {
			for (int i = 0; i < SteamWasher.outputFluids; i++) {
				//x += GuiSteamWasher.cellSpan;
				//addSlotToContainer(new SlotFurnace(player, tileEntity, idCounter, x, GuiSteamWasher.inputFluidsTop));
				//idCounter++;
			}
		}
		x = 152;
		if(SteamWasher.outputItems > 0) {
			for (int i = 0; i < SteamWasher.outputItems; i++) {
				addSlotToContainer(new SlotFurnace(player, tileEntity, idCounter, x, GuiSteamWasher.inputItemsTop));
				x += GuiSteamWasher.cellSpan;
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
