package igentuman.ncsteamadditions.jei.catergory;

import igentuman.ncsteamadditions.jei.recipe.NCSteamAdditionsRecipeWrapper;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import nc.integration.jei.JEIHelper.RecipeFluidMapper;
import nc.integration.jei.JEIHelper.RecipeItemMapper;
import nc.integration.jei.NCJEI.IJEIHandler;
import nc.recipe.IngredientSorption;

public class SteamTransformerCategory extends JEINCSteamAdditionsMachineCategory<NCSteamAdditionsRecipeWrapper.SteamTransformer>
{
	
	public SteamTransformerCategory(IGuiHelper guiHelper, IJEIHandler handler) 
	{
		super(guiHelper, handler, "steam_transformer", 30, 7, 142, 56);
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, NCSteamAdditionsRecipeWrapper.SteamTransformer recipeWrapper, IIngredients ingredients)
	{
		super.setRecipe(recipeLayout, recipeWrapper, ingredients);
		
		RecipeItemMapper itemMapper = new RecipeItemMapper();
		RecipeFluidMapper fluidMapper = new RecipeFluidMapper();
		itemMapper.map(IngredientSorption.INPUT, 0, 0, 36 - backPosX, 11 - backPosY);
		fluidMapper.map(IngredientSorption.INPUT, 0, 0, 36 - backPosX, 42 - backPosY, 16, 16);
		fluidMapper.map(IngredientSorption.INPUT, 1, 1, 56 - backPosX, 42 - backPosY, 16, 16);
		fluidMapper.map(IngredientSorption.INPUT, 2, 2, 76 - backPosX, 42 - backPosY, 16, 16);
		itemMapper.map(IngredientSorption.OUTPUT, 0, 1, 112 - backPosX, 42 - backPosY);
		itemMapper.map(IngredientSorption.OUTPUT, 1, 2, 132 - backPosX, 42 - backPosY);
		itemMapper.map(IngredientSorption.OUTPUT, 2, 3, 152 - backPosX, 42 - backPosY);
		itemMapper.mapItemsTo(recipeLayout.getItemStacks(), ingredients);
		fluidMapper.mapFluidsTo(recipeLayout.getFluidStacks(), ingredients);
	}
}
