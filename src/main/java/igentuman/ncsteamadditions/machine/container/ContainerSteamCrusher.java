package igentuman.ncsteamadditions.machine.container;

import igentuman.ncsteamadditions.machine.gui.GuiSteamCrusher;
import igentuman.ncsteamadditions.processors.SteamCrusher;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import nc.container.processor.ContainerItemFluidProcessor;
import nc.container.slot.SlotFurnace;
import nc.container.slot.SlotProcessorInput;
import nc.container.slot.SlotSpecificInput;
import nc.tile.processor.TileItemFluidProcessor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class ContainerSteamCrusher extends ContainerItemFluidProcessor
{

	public ContainerSteamCrusher(EntityPlayer player, TileItemFluidProcessor tileEntity)
	{
		super(player, tileEntity, NCSteamAdditionsRecipes.steam_crusher);

		int x = GuiSteamCrusher.inputFluidsLeft;
		int idCounter = 0;

		if(SteamCrusher.inputFluids > 0) {
			for(int i = 0; i < SteamCrusher.inputFluids; i++) {
				//addSlotToContainer(new SlotProcessorInput(tileEntity, recipeHandler, idCounter, x, GuiSteamCrusher.inputFluidsTop));
				//x += GuiSteamCrusher.cellSpan;
				//idCounter++;
			}
		}

		x = GuiSteamCrusher.inputItemsLeft;
		if(SteamCrusher.inputItems > 0) {
			for (int i = 0; i < SteamCrusher.inputItems; i++) {
				addSlotToContainer(new SlotProcessorInput(tileEntity, recipeHandler, idCounter, x, GuiSteamCrusher.inputItemsTop));
				x += GuiSteamCrusher.cellSpan;
				idCounter++;
			}
		}

		x = 152;
		if(SteamCrusher.outputFluids > 0) {
			for (int i = 0; i < SteamCrusher.outputFluids; i++) {
				//x += GuiSteamCrusher.cellSpan;
				//addSlotToContainer(new SlotFurnace(player, tileEntity, idCounter, x, GuiSteamCrusher.inputFluidsTop));
				//idCounter++;
			}
		}
		x = 152;
		if(SteamCrusher.outputItems > 0) {
			for (int i = 0; i < SteamCrusher.outputItems; i++) {
				addSlotToContainer(new SlotFurnace(player, tileEntity, idCounter, x, GuiSteamCrusher.inputItemsTop));
				x += GuiSteamCrusher.cellSpan;
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
