package igentuman.ncsteamadditions.jei.recipe;

import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import mezz.jei.api.IGuiHelper;
import nc.integration.jei.JEIMachineRecipeWrapper;
import nc.integration.jei.NCJEI.IJEIHandler;
import nc.recipe.BasicRecipe;
import nc.recipe.BasicRecipeHandler;

public class NCSteamAdditionsRecipeWrapper
{

	public static class SteamTransformer extends JEIMachineRecipeWrapper
	{

		public SteamTransformer(IGuiHelper guiHelper, IJEIHandler jeiHandler, BasicRecipeHandler recipeHandler, BasicRecipe recipe)
		{
			super(guiHelper, jeiHandler, recipeHandler, recipe, 30, 7, 0, 0, 0, 0, 0, 0, 94, 42, 16, 16);
		}

		@Override
		protected double getBaseProcessTime()
		{
			if (recipe == null)
				return NCSteamAdditionsConfig.processor_time[igentuman.ncsteamadditions.processors.SteamTransformer.GUID];
			return recipe.getBaseProcessTime(NCSteamAdditionsConfig.processor_time[igentuman.ncsteamadditions.processors.SteamTransformer.GUID]);
		}

		@Override
		protected double getBaseProcessPower()
		{
			return 0;
		}
	}
}
