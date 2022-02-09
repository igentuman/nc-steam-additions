package igentuman.ncsteamadditions.machine.container;

import igentuman.ncsteamadditions.machine.gui.GuiSteamTurbine;
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
		super(player, tileEntity, NCSteamAdditionsRecipes.processorRecipeHandlers[SteamTurbine.GUID]);

		int x = GuiSteamTurbine.inputFluidsLeft;
		int idCounter = 0;

		if(SteamTurbine.inputFluids > 0) {
			for(int i = 0; i < SteamTurbine.inputFluids; i++) {
				//addSlotToContainer(new SlotProcessorInput(tileEntity, recipeHandler, idCounter, x, GuiSteamTurbine.inputFluidsTop));
				//x += GuiSteamTurbine.cellSpan;
				//idCounter++;
			}
		}

		x = GuiSteamTurbine.inputItemsLeft;
		if(SteamTurbine.inputItems > 0) {
			for (int i = 0; i < SteamTurbine.inputItems; i++) {
				addSlotToContainer(new SlotProcessorInput(tileEntity, recipeHandler, idCounter, x, GuiSteamTurbine.inputItemsTop));
				x += GuiSteamTurbine.cellSpan;
				idCounter++;
			}
		}

		x = 152;
		if(SteamTurbine.outputFluids > 0) {
			for (int i = 0; i < SteamTurbine.outputFluids; i++) {
				//x += GuiSteamTurbine.cellSpan;
				//addSlotToContainer(new SlotFurnace(player, tileEntity, idCounter, x, GuiSteamTurbine.inputFluidsTop));
				//idCounter++;
			}
		}
		x = 152;
		if(SteamTurbine.outputItems > 0) {
			for (int i = 0; i < SteamTurbine.outputItems; i++) {
				addSlotToContainer(new SlotFurnace(player, tileEntity, idCounter, x, GuiSteamTurbine.inputItemsTop));
				x += GuiSteamTurbine.cellSpan;
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
