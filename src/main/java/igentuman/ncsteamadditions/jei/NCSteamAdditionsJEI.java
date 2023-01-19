package igentuman.ncsteamadditions.jei;

import igentuman.ncsteamadditions.processors.AbstractProcessor;
import igentuman.ncsteamadditions.processors.ProcessorsRegistry;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import nc.gui.processor.GuiManufactory;
import nc.integration.jei.NCJEI;

import static nc.config.NCConfig.register_processor;

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
