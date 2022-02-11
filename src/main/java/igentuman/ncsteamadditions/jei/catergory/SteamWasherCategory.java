package igentuman.ncsteamadditions.jei.catergory;

import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import igentuman.ncsteamadditions.machine.gui.GuiSteamWasher;
import igentuman.ncsteamadditions.processors.ProcessorsRegistry;
import igentuman.ncsteamadditions.processors.SteamWasher;
import mezz.jei.api.IGuiHelper;
import nc.integration.jei.JEIMachineRecipeWrapper;
import nc.integration.jei.NCJEI.IJEIHandler;
import nc.recipe.BasicRecipe;
import nc.recipe.BasicRecipeHandler;

public class SteamWasherCategory extends ParentProcessorCategory
{
	private SteamWasher processor;

	protected int getCellSpan()
	{
		return GuiSteamWasher.cellSpan;
	}

	protected int getItemsLeft()
	{
		return GuiSteamWasher.inputItemsLeft;
	}

	protected int getFluidsLeft()
	{
		return GuiSteamWasher.inputFluidsLeft;
	}

	protected int getItemsTop()
	{
		return GuiSteamWasher.inputItemsTop;
	}

	protected int getFluidsTop()
	{
		return GuiSteamWasher.inputFluidsTop;
	}

	@Override
	public SteamWasher getProcessor()
	{
		return processor;
	}

	public SteamWasherCategory(IGuiHelper guiHelper, IJEIHandler handler, SteamWasher proc)
	{
		super(guiHelper, handler, proc.code, 24, 7, 148, 56, proc);
		processor = proc;
	}

	public static class SteamWasherWrapper extends JEIMachineRecipeWrapper
	{

		public SteamWasherWrapper(IGuiHelper guiHelper, IJEIHandler jeiHandler, BasicRecipeHandler recipeHandler, BasicRecipe recipe)
		{
			super(guiHelper, jeiHandler, recipeHandler, recipe, 24, 7, 0, 0, 0, 0, 0, 0, 94, 30, 16, 16);
		}

		@Override
		protected double getBaseProcessTime()
		{
			if (recipe == null)
				return NCSteamAdditionsConfig.processor_time[ProcessorsRegistry.get().STEAM_WASHER.GUID];
			return recipe.getBaseProcessTime(NCSteamAdditionsConfig.processor_time[ProcessorsRegistry.get().STEAM_WASHER.GUID]);
		}

		@Override
		protected double getBaseProcessPower()
		{
			return 0;
		}
	}
}
