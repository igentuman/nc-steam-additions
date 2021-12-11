package igentuman.ncsteamadditions.container.slot;

import igentuman.ncsteamadditions.recipe.NCSteamAdditionsRecipeHandler;
import nc.tile.inventory.ITileInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotNCSteamAdditionsProcessorInput extends Slot
{

	public final NCSteamAdditionsRecipeHandler recipeHandler;
	public final int stackSize;

	public SlotNCSteamAdditionsProcessorInput(ITileInventory tile, NCSteamAdditionsRecipeHandler recipeHandler, int slotIndex, int xPosition, int yPosition)
	{
		super(tile, slotIndex, xPosition, yPosition);
		this.recipeHandler = recipeHandler;
		stackSize = 64;
	}
	
	public SlotNCSteamAdditionsProcessorInput(ITileInventory tile, NCSteamAdditionsRecipeHandler recipeHandler, int slotIndex, int xPosition, int yPosition, int stackSize)
	{
		super(tile, slotIndex, xPosition, yPosition);
		this.recipeHandler = recipeHandler;
		this.stackSize = stackSize;
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return true;
	}

	public int getSlotStackLimit()
	{
		return stackSize;
	}




}
