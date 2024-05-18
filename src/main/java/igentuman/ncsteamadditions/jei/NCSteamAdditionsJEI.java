package igentuman.ncsteamadditions.jei;

import igentuman.ncsteamadditions.processors.*;
import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;

@JEIPlugin
public class NCSteamAdditionsJEI implements IModPlugin
{

	public void registerCategories(IRecipeCategoryRegistration registry)
	{
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		for (AbstractProcessor processor: ProcessorsRegistry.get().processors()) {
			registry.addRecipeCategories(processor.getRecipeCategory(guiHelper));
		}
	}

	public void register(IModRegistry registry)
	{
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

		for (AbstractProcessor processor: ProcessorsRegistry.get().processors()) {
			registry.addRecipes(processor.getRecipeHandler().getJEIRecipes(guiHelper), processor.getRecipeHandler().getUid());
			registry.addRecipeCatalyst(processor.getRecipeHandler().getCrafters().get(0), processor.getRecipeHandler().getUid());
			registry.addRecipeClickArea(processor.getGuiClass(), 53, 34, 57, 18, processor.getRecipeHandler().getUid());
		}
	}
}
