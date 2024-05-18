package igentuman.ncsteamadditions.jei.catergory;

import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import igentuman.ncsteamadditions.machine.gui.GuiSteamTurbine;
import igentuman.ncsteamadditions.processors.*;
import mezz.jei.api.IGuiHelper;
import nc.recipe.*;

public class SteamTurbineCategory extends ParentProcessorCategory
{
	private SteamTurbine processor;

	protected int getCellSpan()
	{
		return GuiSteamTurbine.cellSpan;
	}

	protected int getItemsLeft()
	{
		return GuiSteamTurbine.inputItemsLeft;
	}

	protected int getFluidsLeft()
	{
		return GuiSteamTurbine.inputFluidsLeft;
	}

	protected int getItemsTop()
	{
		return GuiSteamTurbine.inputItemsTop;
	}

	protected int getFluidsTop()
	{
		return GuiSteamTurbine.inputFluidsTop;
	}

	@Override
	public SteamTurbine getProcessor()
	{
		return processor;
	}

	public SteamTurbineCategory(IGuiHelper guiHelper, IJEIHandler handler, SteamTurbine proc)
	{
		super(guiHelper, handler, proc.code, 24, 7, 148, 56, proc);
		processor = proc;
	}

	public static class SteamTurbineWrapper extends JEIMachineRecipeWrapper
	{

		public SteamTurbineWrapper(IGuiHelper guiHelper, IJEIHandler jeiHandler, BasicRecipeHandler recipeHandler, BasicRecipe recipe)
		{
			super(guiHelper, jeiHandler, recipeHandler, recipe, 24, 7, 0, 0, 0, 0, 0, 0, 94, 30, 16, 16);
		}

		@Override
		protected double getBaseProcessTime()
		{
			if (recipe == null)
				return NCSteamAdditionsConfig.processor_time[ProcessorsRegistry.get().STEAM_TURBINE.GUID];
			return recipe.getBaseProcessTime(NCSteamAdditionsConfig.processor_time[ProcessorsRegistry.get().STEAM_TURBINE.GUID]);
		}

		@Override
		protected double getBaseProcessPower()
		{
			return 0;
		}
	}
}
