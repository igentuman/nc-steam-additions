package igentuman.ncsteamadditions.jei.catergory;

import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import igentuman.ncsteamadditions.machine.gui.GuiSteamCompactor;
import igentuman.ncsteamadditions.processors.*;
import mezz.jei.api.IGuiHelper;
import nc.recipe.*;
import nclegacy.jei.*;

public class SteamCompactorCategory extends ParentProcessorCategory
{
	private SteamCompactor processor;

	protected int getCellSpan()
	{
		return GuiSteamCompactor.cellSpan;
	}

	protected int getItemsLeft()
	{
		return GuiSteamCompactor.inputItemsLeft;
	}

	protected int getFluidsLeft()
	{
		return GuiSteamCompactor.inputFluidsLeft;
	}

	protected int getItemsTop()
	{
		return GuiSteamCompactor.inputItemsTop;
	}

	protected int getFluidsTop()
	{
		return GuiSteamCompactor.inputFluidsTop;
	}

	@Override
	public SteamCompactor getProcessor()
	{
		return processor;
	}

	public SteamCompactorCategory(IGuiHelper guiHelper, IJEIHandlerLegacy handler, SteamCompactor proc)
	{
		super(guiHelper, handler, proc.code, 24, 7, 148, 56, proc);
		processor = proc;
	}

	public static class SteamCompactorWrapper extends JEIMachineRecipeWrapperLegacy
	{

		public SteamCompactorWrapper(IGuiHelper guiHelper, IJEIHandlerLegacy jeiHandler, BasicRecipeHandler recipeHandler, BasicRecipe recipe)
		{
			super(guiHelper, jeiHandler, recipeHandler, recipe, 24, 7, 0, 0, 0, 0, 0, 0, 94, 30, 16, 16);
		}

		@Override
		protected double getBaseProcessTime()
		{
			if (recipe == null)
				return NCSteamAdditionsConfig.processor_time[ProcessorsRegistry.get().STEAM_COMPACTOR.GUID];
			return recipe.getBaseProcessTime(NCSteamAdditionsConfig.processor_time[ProcessorsRegistry.get().STEAM_COMPACTOR.GUID]);
		}

		@Override
		protected double getBaseProcessPower()
		{
			return 0;
		}
	}
}
