package igentuman.ncsteamadditions.machine.container;

import igentuman.ncsteamadditions.processors.*;
import igentuman.ncsteamadditions.tile.TileNCSProcessor;
import nc.container.slot.SlotSpecificInput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class ContainerHeatExchanger extends ProcessorContainer
{
	public ContainerHeatExchanger(EntityPlayer player, TileNCSProcessor tileEntity)
	{
		super(player, tileEntity, ProcessorsRegistry.get().HEAT_EXCHANGER);
	}

	public void renderConteiner(EntityPlayer player, TileNCSProcessor tileEntity, AbstractProcessor processor)
	{
		int idCounter = 0;

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
