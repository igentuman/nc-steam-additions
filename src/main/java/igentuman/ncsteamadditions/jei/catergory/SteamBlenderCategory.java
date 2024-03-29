package igentuman.ncsteamadditions.jei.catergory;

import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import igentuman.ncsteamadditions.machine.gui.GuiSteamBlender;
import igentuman.ncsteamadditions.processors.ProcessorsRegistry;
import igentuman.ncsteamadditions.processors.SteamBlender;
import mezz.jei.api.IGuiHelper;
import nc.integration.jei.JEIMachineRecipeWrapper;
import nc.integration.jei.NCJEI.IJEIHandler;
import nc.recipe.BasicRecipe;
import nc.recipe.BasicRecipeHandler;

public class SteamBlenderCategory extends ParentProcessorCategory
{
	private SteamBlender processor;

	protected int getCellSpan()
	{
		return GuiSteamBlender.cellSpan;
	}

	protected int getItemsLeft()
	{
		return GuiSteamBlender.inputItemsLeft;
	}

	protected int getFluidsLeft()
	{
		return GuiSteamBlender.inputFluidsLeft;
	}

	protected int getItemsTop()
	{
		return GuiSteamBlender.inputItemsTop;
	}

	protected int getFluidsTop()
	{
		return GuiSteamBlender.inputFluidsTop;
	}

	@Override
	public SteamBlender getProcessor()
	{
		return processor;
	}

	public SteamBlenderCategory(IGuiHelper guiHelper, IJEIHandler handler, SteamBlender proc)
	{
		super(guiHelper, handler, proc.code, 24, 7, 148, 56, proc);
		processor = proc;
	}

	public static class SteamBlenderWrapper extends JEIMachineRecipeWrapper
	{

		public SteamBlenderWrapper(IGuiHelper guiHelper, IJEIHandler jeiHandler, BasicRecipeHandler recipeHandler, BasicRecipe recipe)
		{
			super(guiHelper, jeiHandler, recipeHandler, recipe, 24, 7, 0, 0, 0, 0, 0, 0, 94, 30, 16, 16);

		}

		@Override
		protected double getBaseProcessTime()
		{
			if (recipe == null)
				return NCSteamAdditionsConfig.processor_time[ProcessorsRegistry.get().STEAM_BLENDER.GUID];
			return recipe.getBaseProcessTime(NCSteamAdditionsConfig.processor_time[ProcessorsRegistry.get().STEAM_BLENDER.GUID]);
		}

		@Override
		protected double getBaseProcessPower()
		{
			return 0;
		}
	}
}
