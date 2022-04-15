package igentuman.ncsteamadditions.jei.catergory;

import igentuman.ncsteamadditions.processors.AbstractProcessor;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import nc.integration.jei.JEIHelper.RecipeFluidMapper;
import nc.integration.jei.JEIHelper.RecipeItemMapper;
import nc.integration.jei.JEIMachineRecipeWrapper;
import nc.integration.jei.NCJEI.IJEIHandler;
import nc.recipe.IngredientSorption;

public abstract class ParentProcessorCategory extends JEINCSteamAdditionsMachineCategory
{


	public ParentProcessorCategory(IGuiHelper guiHelper, IJEIHandler handler, String name, int x, int y, int l, int h, AbstractProcessor proc)
	{
		super(guiHelper, handler, name, x, y, l, h);
		processor = proc;
	}



}
