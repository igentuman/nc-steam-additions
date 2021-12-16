package igentuman.ncsteamadditions.machine.container;

import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import nc.container.processor.ContainerItemFluidProcessor;
import nc.container.slot.SlotFurnace;
import nc.container.slot.SlotProcessorInput;
import nc.container.slot.SlotSpecificInput;
import nc.tile.processor.TileItemFluidProcessor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class ContainerSteamTransformer extends ContainerItemFluidProcessor
{

	public ContainerSteamTransformer(EntityPlayer player, TileItemFluidProcessor tileEntity)
	{
		super(player, tileEntity, NCSteamAdditionsRecipes.steam_transformer);
		
		addSlotToContainer(new SlotProcessorInput(tileEntity, recipeHandler, 0, 36, 11));

		addSlotToContainer(new SlotProcessorInput(tileEntity, recipeHandler, 1, 56, 11));
		addSlotToContainer(new SlotProcessorInput(tileEntity, recipeHandler, 2, 36, 31));
		addSlotToContainer(new SlotProcessorInput(tileEntity, recipeHandler, 3, 56, 31));
		
		addSlotToContainer(new SlotSpecificInput(tileEntity, 4, 132, 64, SPEED_UPGRADE));
		
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
