package igentuman.ncsteamadditions.jei.catergory;

import igentuman.ncsteamadditions.processors.AbstractProcessor;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import nc.integration.jei.JEIHelper;
import nc.integration.jei.JEIHelper.RecipeFluidMapper;
import nc.integration.jei.JEIHelper.RecipeItemMapper;
import nc.integration.jei.JEIMachineRecipeWrapper;
import nc.integration.jei.NCJEI.IJEIHandler;
import nc.recipe.IngredientSorption;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ParentProcessorCategory extends JEINCSteamAdditionsMachineCategory
{


	public ParentProcessorCategory(IGuiHelper guiHelper, IJEIHandler handler, String name, int x, int y, int l, int h, AbstractProcessor proc)
	{
		super(guiHelper, handler, name, x, y, l, h);
		processor = proc;
	}

	public void mapLayout(IRecipeLayout recipeLayout, IIngredients ingredients)
	{
		JEIHelper.RecipeItemMapper itemMapper = new JEIHelper.RecipeItemMapper();
		JEIHelper.RecipeFluidMapper fluidMapper = new JEIHelper.RecipeFluidMapper();
		int x = getFluidsLeft();
		int c = 0;

		if(getProcessor().getInputFluids() > 0) {
			for (int i = 0; i < getProcessor().getInputFluids(); i++) {
				fluidMapper.map(IngredientSorption.INPUT, i, c++, x - backPosX, getFluidsTop() - backPosY, 16, 16);
				x+=getCellSpan();
			}
		}

		x = getItemsLeft();
		if(getProcessor().getInputItems() > 0) {
			for (int i = 0; i < getProcessor().getInputItems(); i++) {
				itemMapper.map(IngredientSorption.INPUT, i, c++, x - backPosX, getItemsTop() - backPosY);
				x+=getCellSpan();
			}
		}

		x = 152;
		if(getProcessor().getOutputFluids() > 0) {
			for (int i = 0; i < getProcessor().getOutputFluids(); i++) {
				fluidMapper.map(IngredientSorption.OUTPUT, i, c++, x - backPosX, getFluidsTop() - backPosY,16, 16);
				x+=getCellSpan();

			}
		}

		x = 152;
		if(getProcessor().getOutputItems() > 0) {
			for (int i = 0; i < getProcessor().getOutputItems(); i++) {
				itemMapper.map(IngredientSorption.OUTPUT, i, c++, x - backPosX, getItemsTop() - backPosY);
				x+=getCellSpan();
			}
		}

		itemMapper.mapItemsTo(recipeLayout.getItemStacks(), ingredients);
		fluidMapper.mapFluidsTo(recipeLayout.getFluidStacks(), ingredients);
	}


}
