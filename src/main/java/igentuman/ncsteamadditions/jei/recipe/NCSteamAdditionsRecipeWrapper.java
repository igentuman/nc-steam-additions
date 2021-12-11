package igentuman.ncsteamadditions.jei.recipe;

import java.util.ArrayList;
import java.util.List;

import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import mezz.jei.api.IGuiHelper;
import nc.integration.jei.JEIBasicRecipeWrapper;
import nc.integration.jei.JEIMachineRecipeWrapper;
import nc.integration.jei.NCJEI.IJEIHandler;
import nc.recipe.BasicRecipe;
import nc.recipe.BasicRecipeHandler;
import nc.util.Lang;
import net.minecraft.util.text.TextFormatting;

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
				return NCSteamAdditionsConfig.processor_time[0];
			return recipe.getBaseProcessTime(NCSteamAdditionsConfig.processor_time[0]);
		}

		@Override
		protected double getBaseProcessPower()
		{
			if (recipe == null)
				return NCSteamAdditionsConfig.processor_power[0];
			return recipe.getBaseProcessPower(NCSteamAdditionsConfig.processor_power[0]);
		}
	}
	


}
