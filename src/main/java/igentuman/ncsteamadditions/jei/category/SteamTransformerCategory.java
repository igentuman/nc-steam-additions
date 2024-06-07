package igentuman.ncsteamadditions.jei.category;

import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import igentuman.ncsteamadditions.machine.gui.GuiSteamTransformer;
import igentuman.ncsteamadditions.processors.*;
import mezz.jei.api.IGuiHelper;
import nc.recipe.*;

public class SteamTransformerCategory extends ParentProcessorCategory
{
	private SteamTransformer processor;

	protected int getCellSpan()
	{
		return GuiSteamTransformer.cellSpan;
	}

	protected int getItemsLeft()
	{
		return GuiSteamTransformer.inputItemsLeft;
	}

	protected int getFluidsLeft()
	{
		return GuiSteamTransformer.inputFluidsLeft;
	}

	protected int getItemsTop()
	{
		return GuiSteamTransformer.inputItemsTop;
	}

	protected int getFluidsTop()
	{
		return GuiSteamTransformer.inputFluidsTop;
	}

	@Override
	public SteamTransformer getProcessor()
	{
		return processor;
	}

	public SteamTransformerCategory(IGuiHelper guiHelper, IJEIHandler handler, SteamTransformer proc)
	{
		super(guiHelper, handler, proc.code, 24, 7, 148, 56, proc);
		processor = proc;
	}

	public static class SteamTransformerWrapper extends JEIMachineRecipeWrapper
	{

		public SteamTransformerWrapper(IGuiHelper guiHelper, IJEIHandler jeiHandler, BasicRecipeHandler recipeHandler, BasicRecipe recipe)
		{
			super(guiHelper, jeiHandler, recipeHandler, recipe, 24, 7, 0, 0, 0, 0, 0, 0, 94, 30, 16, 16);
		}

		@Override
		protected double getBaseProcessTime()
		{
			if (recipe == null)
				return NCSteamAdditionsConfig.processor_time[ProcessorsRegistry.get().STEAM_TRANSFORMER.GUID];
			return recipe.getBaseProcessTime(NCSteamAdditionsConfig.processor_time[ProcessorsRegistry.get().STEAM_TRANSFORMER.GUID]);
		}

		@Override
		protected double getBaseProcessPower()
		{
			return 0;
		}
	}
}
