package igentuman.ncsteamadditions.jei.catergory;

import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import igentuman.ncsteamadditions.machine.gui.GuiSteamBoiler;
import igentuman.ncsteamadditions.processors.ProcessorsRegistry;
import igentuman.ncsteamadditions.processors.SteamBoiler;
import mezz.jei.api.IGuiHelper;
import nc.integration.jei.JEIMachineRecipeWrapper;
import nc.integration.jei.NCJEI.IJEIHandler;
import nc.recipe.BasicRecipe;
import nc.recipe.BasicRecipeHandler;

public class SteamBoilerCategory extends ParentProcessorCategory
{
	private SteamBoiler processor;

	protected int getCellSpan()
	{
		return GuiSteamBoiler.cellSpan;
	}

	protected int getItemsLeft()
	{
		return GuiSteamBoiler.inputItemsLeft;
	}

	protected int getFluidsLeft()
	{
		return GuiSteamBoiler.inputFluidsLeft;
	}

	protected int getItemsTop()
	{
		return GuiSteamBoiler.inputItemsTop;
	}

	protected int getFluidsTop()
	{
		return GuiSteamBoiler.inputFluidsTop;
	}

	@Override
	public SteamBoiler getProcessor()
	{
		return processor;
	}

	public SteamBoilerCategory(IGuiHelper guiHelper, IJEIHandler handler, SteamBoiler proc)
	{
		super(guiHelper, handler, proc.code, 24, 7, 148, 56, proc);
		processor = proc;
	}

	public static class SteamBoilerWrapper extends JEIMachineRecipeWrapper
	{

		public SteamBoilerWrapper(IGuiHelper guiHelper, IJEIHandler jeiHandler, BasicRecipeHandler recipeHandler, BasicRecipe recipe)
		{
			super(guiHelper, jeiHandler, recipeHandler, recipe, 24, 7, 0, 0, 0, 0, 0, 0, 94, 30, 16, 16);
		}

		@Override
		protected double getBaseProcessTime()
		{
			if (recipe == null)
				return NCSteamAdditionsConfig.processor_time[ProcessorsRegistry.get().STEAM_BOILER.GUID];
			return recipe.getBaseProcessTime(NCSteamAdditionsConfig.processor_time[ProcessorsRegistry.get().STEAM_BOILER.GUID]);
		}

		@Override
		protected double getBaseProcessPower()
		{
			return 0;
		}
	}
}
