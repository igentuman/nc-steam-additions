package igentuman.ncsteamadditions.machine.container;

import igentuman.ncsteamadditions.machine.gui.GuiSteamBoiler;
import igentuman.ncsteamadditions.processors.SteamBoiler;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import igentuman.ncsteamadditions.tile.TileNCSProcessor;
import nc.container.processor.ContainerItemFluidProcessor;
import nc.container.slot.SlotFurnace;
import nc.container.slot.SlotProcessorInput;
import nc.container.slot.SlotSpecificInput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class ContainerSteamBoiler extends ContainerItemFluidProcessor
{

	public ContainerSteamBoiler(EntityPlayer player, TileNCSProcessor tileEntity)
	{
		super(player, tileEntity, NCSteamAdditionsRecipes.processorRecipeHandlers[SteamBoiler.GUID]);

		int x = GuiSteamBoiler.inputFluidsLeft;
		int idCounter = 0;

		if(SteamBoiler.inputFluids > 0) {
			for(int i = 0; i < SteamBoiler.inputFluids; i++) {
				//addSlotToContainer(new SlotProcessorInput(tileEntity, recipeHandler, idCounter, x, GuiSteamBoiler.inputFluidsTop));
				//x += GuiSteamBoiler.cellSpan;
				//idCounter++;
			}
		}

		x = GuiSteamBoiler.inputItemsLeft;
		if(SteamBoiler.inputItems > 0) {
			for (int i = 0; i < SteamBoiler.inputItems; i++) {
				addSlotToContainer(new SlotProcessorInput(tileEntity, recipeHandler, idCounter, x, GuiSteamBoiler.inputItemsTop));
				x += GuiSteamBoiler.cellSpan;
				idCounter++;
			}
		}

		x = 152;
		if(SteamBoiler.outputFluids > 0) {
			for (int i = 0; i < SteamBoiler.outputFluids; i++) {
				//x += GuiSteamBoiler.cellSpan;
				//addSlotToContainer(new SlotFurnace(player, tileEntity, idCounter, x, GuiSteamBoiler.inputFluidsTop));
				//idCounter++;
			}
		}
		x = 152;
		if(SteamBoiler.outputItems > 0) {
			for (int i = 0; i < SteamBoiler.outputItems; i++) {
				addSlotToContainer(new SlotFurnace(player, tileEntity, idCounter, x, GuiSteamBoiler.inputItemsTop));
				x += GuiSteamBoiler.cellSpan;
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
