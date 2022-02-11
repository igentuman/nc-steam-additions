package igentuman.ncsteamadditions.jei.catergory;

import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import igentuman.ncsteamadditions.machine.gui.GuiDigitalTransformer;
import igentuman.ncsteamadditions.processors.DigitalTransformer;
import igentuman.ncsteamadditions.processors.ProcessorsRegistry;
import mezz.jei.api.IGuiHelper;
import nc.integration.jei.JEIMachineRecipeWrapper;
import nc.integration.jei.NCJEI.IJEIHandler;
import nc.recipe.BasicRecipe;
import nc.recipe.BasicRecipeHandler;

public class DigitalTransformerCategory extends ParentProcessorCategory
{
	private DigitalTransformer processor;

	protected int getCellSpan()
	{
		return GuiDigitalTransformer.cellSpan;
	}

	protected int getItemsLeft()
	{
		return GuiDigitalTransformer.inputItemsLeft;
	}

	protected int getFluidsLeft()
	{
		return GuiDigitalTransformer.inputFluidsLeft;
	}

	protected int getItemsTop()
	{
		return GuiDigitalTransformer.inputItemsTop;
	}

	protected int getFluidsTop()
	{
		return GuiDigitalTransformer.inputFluidsTop;
	}

	@Override
	public DigitalTransformer getProcessor()
	{
		return processor;
	}

	public DigitalTransformerCategory(IGuiHelper guiHelper, IJEIHandler handler, DigitalTransformer proc)
	{
		super(guiHelper, handler, proc.code, 24, 7, 148, 56, proc);
		processor = proc;
	}

	public static class DigitalTransformerWrapper extends JEIMachineRecipeWrapper
	{

		public DigitalTransformerWrapper(IGuiHelper guiHelper, IJEIHandler jeiHandler, BasicRecipeHandler recipeHandler, BasicRecipe recipe)
		{
			super(guiHelper, jeiHandler, recipeHandler, recipe, 24, 7, 0, 0, 0, 0, 0, 0, 94, 30, 16, 16);
		}

		@Override
		protected double getBaseProcessTime()
		{
			if (recipe == null)
				return NCSteamAdditionsConfig.processor_time[ProcessorsRegistry.get().DIGITAL_TRANSFORMER.GUID];
			return recipe.getBaseProcessTime(NCSteamAdditionsConfig.processor_time[ProcessorsRegistry.get().DIGITAL_TRANSFORMER.GUID]);
		}

		@Override
		protected double getBaseProcessPower()
		{
			return NCSteamAdditionsConfig.digitalTransformerRF;
		}
	}
}
