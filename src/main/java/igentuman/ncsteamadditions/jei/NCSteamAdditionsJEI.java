package igentuman.ncsteamadditions.jei;

import igentuman.ncsteamadditions.processors.AbstractProcessor;
import igentuman.ncsteamadditions.processors.ProcessorsRegistry;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
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
			registry.addRecipes(processor.recipeHandler.getJEIRecipes(guiHelper), processor.recipeHandler.getUid());
			registry.addRecipeCatalyst(processor.recipeHandler.getCrafters().get(0), processor.recipeHandler.getUid());
		}
	}
}
