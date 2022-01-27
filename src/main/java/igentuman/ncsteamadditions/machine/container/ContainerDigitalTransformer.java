package igentuman.ncsteamadditions.machine.container;

import igentuman.ncsteamadditions.machine.gui.GuiDigitalTransformer;
import igentuman.ncsteamadditions.processors.DigitalTransformer;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import nc.container.processor.ContainerItemFluidProcessor;
import nc.container.slot.SlotFurnace;
import nc.container.slot.SlotProcessorInput;
import nc.container.slot.SlotSpecificInput;
import nc.tile.processor.TileItemFluidProcessor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class ContainerDigitalTransformer extends ContainerItemFluidProcessor
{

	public ContainerDigitalTransformer(EntityPlayer player, TileItemFluidProcessor tileEntity)
	{
		super(player, tileEntity, NCSteamAdditionsRecipes.processorRecipeHandlers[DigitalTransformer.GUID]);

		int x = GuiDigitalTransformer.inputFluidsLeft;
		int idCounter = 0;

		if(DigitalTransformer.inputFluids > 0) {
			for(int i = 0; i < DigitalTransformer.inputFluids; i++) {
				//addSlotToContainer(new SlotProcessorInput(tileEntity, recipeHandler, idCounter, x, GuiDigitalTransformer.inputFluidsTop));
				//x += GuiDigitalTransformer.cellSpan;
				//idCounter++;
			}
		}

		x = GuiDigitalTransformer.inputItemsLeft;
		if(DigitalTransformer.inputItems > 0) {
			for (int i = 0; i < DigitalTransformer.inputItems; i++) {
				addSlotToContainer(new SlotProcessorInput(tileEntity, recipeHandler, idCounter, x, GuiDigitalTransformer.inputItemsTop));
				x += GuiDigitalTransformer.cellSpan;
				idCounter++;
			}
		}

		x = 152;
		if(DigitalTransformer.outputFluids > 0) {
			for (int i = 0; i < DigitalTransformer.outputFluids; i++) {
				//x += GuiDigitalTransformer.cellSpan;
				//addSlotToContainer(new SlotFurnace(player, tileEntity, idCounter, x, GuiDigitalTransformer.inputFluidsTop));
				//idCounter++;
			}
		}
		x = 152;
		if(DigitalTransformer.outputItems > 0) {
			for (int i = 0; i < DigitalTransformer.outputItems; i++) {
				addSlotToContainer(new SlotFurnace(player, tileEntity, idCounter, x, GuiDigitalTransformer.inputItemsTop));
				x += GuiDigitalTransformer.cellSpan;
				idCounter++;
			}
		}

		//addSlotToContainer(new SlotSpecificInput(tileEntity, idCounter, 152, 64, ENERGY_UPGRADE));
		
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
