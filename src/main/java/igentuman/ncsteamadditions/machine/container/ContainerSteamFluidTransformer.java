package igentuman.ncsteamadditions.machine.container;

import igentuman.ncsteamadditions.machine.gui.GuiSteamFluidTransformer;
import igentuman.ncsteamadditions.processors.SteamFluidTransformer;
import igentuman.ncsteamadditions.processors.SteamTransformer;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import nc.container.processor.ContainerItemFluidProcessor;
import nc.container.slot.SlotFurnace;
import nc.container.slot.SlotProcessorInput;
import nc.container.slot.SlotSpecificInput;
import nc.tile.processor.TileItemFluidProcessor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class ContainerSteamFluidTransformer extends ContainerItemFluidProcessor
{

	public ContainerSteamFluidTransformer(EntityPlayer player, TileItemFluidProcessor tileEntity)
	{
		super(player, tileEntity, NCSteamAdditionsRecipes.processorRecipeHandlers[SteamFluidTransformer.GUID]);

		int x = GuiSteamFluidTransformer.inputFluidsLeft;
		int idCounter = 0;

		if(SteamFluidTransformer.inputFluids > 0) {
			for(int i = 0; i < SteamFluidTransformer.inputFluids; i++) {
				//addSlotToContainer(new SlotProcessorInput(tileEntity, recipeHandler, idCounter, x, GuiSteamFluidTransformer.inputFluidsTop));
				//x += GuiSteamFluidTransformer.cellSpan;
				//idCounter++;
			}
		}

		x = GuiSteamFluidTransformer.inputItemsLeft;
		if(SteamFluidTransformer.inputItems > 0) {
			for (int i = 0; i < SteamFluidTransformer.inputItems; i++) {
				addSlotToContainer(new SlotProcessorInput(tileEntity, recipeHandler, idCounter, x, GuiSteamFluidTransformer.inputItemsTop));
				x += GuiSteamFluidTransformer.cellSpan;
				idCounter++;
			}
		}

		x = 152;
		if(SteamFluidTransformer.outputFluids > 0) {
			for (int i = 0; i < SteamFluidTransformer.outputFluids; i++) {
				//x += GuiSteamFluidTransformer.cellSpan;
				//addSlotToContainer(new SlotFurnace(player, tileEntity, idCounter, x, GuiSteamFluidTransformer.inputFluidsTop));
				//idCounter++;
			}
		}
		x = 152;
		if(SteamFluidTransformer.outputItems > 0) {
			for (int i = 0; i < SteamFluidTransformer.outputItems; i++) {
				addSlotToContainer(new SlotFurnace(player, tileEntity, idCounter, x, GuiSteamFluidTransformer.inputItemsTop));
				x += GuiSteamFluidTransformer.cellSpan;
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
