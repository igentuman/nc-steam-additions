package igentuman.ncsteamadditions.jei.catergory;

import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import igentuman.ncsteamadditions.machine.gui.GuiSteamFluidTransformer;
import igentuman.ncsteamadditions.processors.ProcessorsRegistry;
import igentuman.ncsteamadditions.processors.SteamFluidTransformer;
import mezz.jei.api.IGuiHelper;
import nc.integration.jei.JEIMachineRecipeWrapper;
import nc.integration.jei.NCJEI.IJEIHandler;
import nc.recipe.BasicRecipe;
import nc.recipe.BasicRecipeHandler;

public class SteamFluidTransformerCategory extends ParentProcessorCategory
{
	private SteamFluidTransformer processor;

	protected int getCellSpan()
	{
		return GuiSteamFluidTransformer.cellSpan;
	}

	protected int getItemsLeft()
	{
		return GuiSteamFluidTransformer.inputItemsLeft;
	}

	protected int getFluidsLeft()
	{
		return GuiSteamFluidTransformer.inputFluidsLeft;
	}

	protected int getItemsTop()
	{
		return GuiSteamFluidTransformer.inputItemsTop;
	}

	protected int getFluidsTop()
	{
		return GuiSteamFluidTransformer.inputFluidsTop;
	}

	@Override
	public SteamFluidTransformer getProcessor()
	{
		return processor;
	}

	public SteamFluidTransformerCategory(IGuiHelper guiHelper, IJEIHandler handler, SteamFluidTransformer proc)
	{
		super(guiHelper, handler, proc.code, 24, 7, 148, 56, proc);
		processor = proc;
	}

	public static class SteamFluidTransformerWrapper extends JEIMachineRecipeWrapper
	{

		public SteamFluidTransformerWrapper(IGuiHelper guiHelper, IJEIHandler jeiHandler, BasicRecipeHandler recipeHandler, BasicRecipe recipe)
		{
			super(guiHelper, jeiHandler, recipeHandler, recipe, 24, 7, 0, 0, 0, 0, 0, 0, 94, 30, 16, 16);
		}

		@Override
		protected double getBaseProcessTime()
		{
			if (recipe == null)
				return NCSteamAdditionsConfig.processor_time[ProcessorsRegistry.get().STEAM_FLUID_TRANSFORMER.GUID];
			return recipe.getBaseProcessTime(NCSteamAdditionsConfig.processor_time[ProcessorsRegistry.get().STEAM_FLUID_TRANSFORMER.GUID]);
		}

		@Override
		protected double getBaseProcessPower()
		{
			return 0;
		}
	}
}
