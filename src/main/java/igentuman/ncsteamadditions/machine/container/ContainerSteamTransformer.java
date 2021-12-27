package igentuman.ncsteamadditions.machine.container;

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
		super(player, tileEntity, NCSteamAdditionsRecipes.steam_transformer);

		int x = 15;
		int toLimit = 0;

		if(SteamTransformer.inputFluids > 0) {
			toLimit = SteamTransformer.inputFluids;
			for(int i = 0; i < toLimit; i++) {
				x += 20;
				addSlotToContainer(new SlotProcessorInput(tileEntity, recipeHandler, i, x, 11));
			}
		}

		if(SteamTransformer.inputItems > 0) {
			toLimit += SteamTransformer.inputItems - 1;
			for (int i = toLimit - SteamTransformer.inputItems; i < toLimit; i++) {
				x += 20;
				addSlotToContainer(new SlotProcessorInput(tileEntity, recipeHandler, i, x, 11));
			}
		}

		if(SteamTransformer.outputFluids > 0) {
			toLimit += SteamTransformer.outputFluids - 1;
			for (int i = toLimit - SteamTransformer.outputFluids; i < toLimit; i++) {
				x += 20;
				//addSlotToContainer(new SlotFurnace(player, tileEntity, i, x, 42));
			}
		}

		if(SteamTransformer.outputItems > 0) {
			toLimit += SteamTransformer.outputItems - 1;
			for (int i = toLimit - SteamTransformer.outputItems; i < toLimit; i++) {
				x += 20;
				addSlotToContainer(new SlotFurnace(player, tileEntity, i, x, 42));
			}
		}

		
		addSlotToContainer(new SlotSpecificInput(tileEntity, toLimit, 132, 64, SPEED_UPGRADE));
		
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
