package igentuman.ncsteamadditions.machine.container;

import igentuman.ncsteamadditions.machine.gui.GuiSteamTransformer;
import igentuman.ncsteamadditions.processors.SteamFluidTransformer;
import igentuman.ncsteamadditions.processors.SteamTransformer;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import nc.container.processor.ContainerItemFluidProcessor;
import nc.container.slot.SlotFurnace;
import nc.container.slot.SlotProcessorInput;
import nc.container.slot.SlotSpecificInput;
import nc.gui.element.NCButton;
import nc.tile.processor.TileItemFluidProcessor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class ContainerSteamTransformer extends ContainerItemFluidProcessor
{

	public ContainerSteamTransformer(EntityPlayer player, TileItemFluidProcessor tileEntity)
	{
		super(player, tileEntity, NCSteamAdditionsRecipes.processorRecipeHandlers[SteamTransformer.GUID]);

		int x = GuiSteamTransformer.inputFluidsLeft;
		int idCounter = 0;

		if(SteamTransformer.inputFluids > 0) {
			for(int i = 0; i < SteamTransformer.inputFluids; i++) {
				//addSlotToContainer(new SlotProcessorInput(tileEntity, recipeHandler, idCounter, x, GuiSteamTransformer.inputFluidsTop));
				//x += GuiSteamTransformer.cellSpan;
				//idCounter++;
			}
		}

		x = GuiSteamTransformer.inputItemsLeft;
		if(SteamTransformer.inputItems > 0) {
			for (int i = 0; i < SteamTransformer.inputItems; i++) {
				addSlotToContainer(new SlotProcessorInput(tileEntity, recipeHandler, idCounter, x, GuiSteamTransformer.inputItemsTop));
				x += GuiSteamTransformer.cellSpan;
				idCounter++;
			}
		}

		x = 152;
		if(SteamTransformer.outputFluids > 0) {
			for (int i = 0; i < SteamTransformer.outputFluids; i++) {
				//x += GuiSteamTransformer.cellSpan;
				//addSlotToContainer(new SlotFurnace(player, tileEntity, idCounter, x, GuiSteamTransformer.inputFluidsTop));
				//idCounter++;
			}
		}
		x = 152;
		if(SteamTransformer.outputItems > 0) {
			for (int i = 0; i < SteamTransformer.outputItems; i++) {
				addSlotToContainer(new SlotFurnace(player, tileEntity, idCounter, x, GuiSteamTransformer.inputItemsTop));
				x += GuiSteamTransformer.cellSpan;
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
