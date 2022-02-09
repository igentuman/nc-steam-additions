package igentuman.ncsteamadditions.machine.container;

import igentuman.ncsteamadditions.machine.gui.GuiSteamBlender;
import igentuman.ncsteamadditions.processors.SteamBlender;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import igentuman.ncsteamadditions.tile.TileNCSProcessor;
import nc.container.processor.ContainerItemFluidProcessor;
import nc.container.slot.SlotFurnace;
import nc.container.slot.SlotProcessorInput;
import nc.container.slot.SlotSpecificInput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class ContainerSteamBlender extends ContainerItemFluidProcessor
{

	public ContainerSteamBlender(EntityPlayer player, TileNCSProcessor tileEntity)
	{
		super(player, tileEntity, NCSteamAdditionsRecipes.processorRecipeHandlers[SteamBlender.GUID]);

		int x = GuiSteamBlender.inputFluidsLeft;
		int idCounter = 0;

		if(SteamBlender.inputFluids > 0) {
			for(int i = 0; i < SteamBlender.inputFluids; i++) {
				//addSlotToContainer(new SlotProcessorInput(tileEntity, recipeHandler, idCounter, x, GuiSteamBlender.inputFluidsTop));
				//x += GuiSteamBlender.cellSpan;
				//idCounter++;
			}
		}

		x = GuiSteamBlender.inputItemsLeft;
		if(SteamBlender.inputItems > 0) {
			for (int i = 0; i < SteamBlender.inputItems; i++) {
				addSlotToContainer(new SlotProcessorInput(tileEntity, recipeHandler, idCounter, x, GuiSteamBlender.inputItemsTop));
				x += GuiSteamBlender.cellSpan;
				idCounter++;
			}
		}

		x = 152;
		if(SteamBlender.outputFluids > 0) {
			for (int i = 0; i < SteamBlender.outputFluids; i++) {
				//x += GuiSteamBlender.cellSpan;
				//addSlotToContainer(new SlotFurnace(player, tileEntity, idCounter, x, GuiSteamBlender.inputFluidsTop));
				//idCounter++;
			}
		}
		x = 152;
		if(SteamBlender.outputItems > 0) {
			for (int i = 0; i < SteamBlender.outputItems; i++) {
				addSlotToContainer(new SlotFurnace(player, tileEntity, idCounter, x, GuiSteamBlender.inputItemsTop));
				x += GuiSteamBlender.cellSpan;
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
