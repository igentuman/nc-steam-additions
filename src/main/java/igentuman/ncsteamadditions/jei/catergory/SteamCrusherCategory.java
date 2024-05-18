package igentuman.ncsteamadditions.jei.catergory;

import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import igentuman.ncsteamadditions.machine.gui.GuiSteamCrusher;
import igentuman.ncsteamadditions.processors.*;
import mezz.jei.api.IGuiHelper;
import nc.recipe.*;

public class SteamCrusherCategory extends ParentProcessorCategory
{
	private SteamCrusher processor;

	protected int getCellSpan()
	{
		return GuiSteamCrusher.cellSpan;
	}

	protected int getItemsLeft()
	{
		return GuiSteamCrusher.inputItemsLeft;
	}

	protected int getFluidsLeft()
	{
		return GuiSteamCrusher.inputFluidsLeft;
	}

	protected int getItemsTop()
	{
		return GuiSteamCrusher.inputItemsTop;
	}

	protected int getFluidsTop()
	{
		return GuiSteamCrusher.inputFluidsTop;
	}

	@Override
	public SteamCrusher getProcessor()
	{
		return processor;
	}

	public SteamCrusherCategory(IGuiHelper guiHelper, IJEIHandler handler, SteamCrusher proc)
	{
		super(guiHelper, handler, proc.code, 24, 7, 148, 56, proc);
		processor = proc;
	}

	public static class SteamCrusherWrapper extends JEIMachineRecipeWrapper
	{

		public SteamCrusherWrapper(IGuiHelper guiHelper, IJEIHandler jeiHandler, BasicRecipeHandler recipeHandler, BasicRecipe recipe)
		{
			super(guiHelper, jeiHandler, recipeHandler, recipe, 24, 7, 0, 0, 0, 0, 0, 0, 94, 30, 16, 16);
		}

		@Override
		protected double getBaseProcessTime()
		{
			if (recipe == null)
				return NCSteamAdditionsConfig.processor_time[ProcessorsRegistry.get().STEAM_CRUSHER.GUID];
			return recipe.getBaseProcessTime(NCSteamAdditionsConfig.processor_time[ProcessorsRegistry.get().STEAM_CRUSHER.GUID]);
		}

		@Override
		protected double getBaseProcessPower()
		{
			return 0;
		}
	}
}
