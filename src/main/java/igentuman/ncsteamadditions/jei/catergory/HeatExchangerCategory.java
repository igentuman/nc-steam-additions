package igentuman.ncsteamadditions.jei.catergory;

import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import igentuman.ncsteamadditions.machine.gui.GuiHeatExchanger;
import igentuman.ncsteamadditions.processors.*;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import nc.integration.jei.JEIHelper;
import nc.recipe.*;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.*;

public class HeatExchangerCategory extends ParentProcessorCategory
{
	private HeatExchanger processor;

	protected int getCellSpan()
	{
		return GuiHeatExchanger.cellSpan;
	}

	protected int getItemsLeft()
	{
		return GuiHeatExchanger.inputItemsLeft;
	}

	protected int getFluidsLeft()
	{
		return GuiHeatExchanger.inputFluidsLeft;
	}

	protected int getItemsTop()
	{
		return GuiHeatExchanger.inputItemsTop;
	}

	protected int getFluidsTop()
	{
		return GuiHeatExchanger.inputFluidsTop;
	}

	@Override
	public HeatExchanger getProcessor()
	{
		return processor;
	}

	public HeatExchangerCategory(IGuiHelper guiHelper, IJEIHandler handler, HeatExchanger proc)
	{
		super(guiHelper, handler, proc.code, 24, 7, 148, 56, proc);
		processor = proc;
		IDrawableStatic staticArrow = guiHelper.createDrawable(location, 0, 168, 135, 40);
		animatedArrow = guiHelper.createAnimatedDrawable(staticArrow, 300 , IDrawableAnimated.StartDirection.LEFT, false);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void drawExtras(Minecraft minecraft)
	{
		animatedArrow.draw(minecraft, 26, 11);
	}

	public static class HeatExchangerWrapper extends JEIMachineRecipeWrapper
	{

		public HeatExchangerWrapper(IGuiHelper guiHelper, IJEIHandler jeiHandler, BasicRecipeHandler recipeHandler, BasicRecipe recipe)
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

	public void mapLayout(IRecipeLayout recipeLayout, IIngredients ingredients)
	{
		JEIHelper.RecipeFluidMapper fluidMapper = new JEIHelper.RecipeFluidMapper();

		int x = getFluidsLeft();
		int y = getFluidsTop() - backPosY;
		int c = 0;
		if(getProcessor().getInputFluids() > 0) {
			for (int i = 0; i < getProcessor().getInputFluids(); i++) {
				fluidMapper.map(IngredientSorption.INPUT, i, c++, x - backPosX, y, 16, 16);
				y+=27;
			}
		}

		y = getFluidsTop() - backPosY;
		x = 152;
		if(getProcessor().getOutputFluids() > 0) {
			for (int i = 0; i < getProcessor().getOutputFluids(); i++) {
				fluidMapper.map(IngredientSorption.OUTPUT, i, c++, x - backPosX, y,16, 16);
				y+=27;
			}
		}
		fluidMapper.mapFluidsTo(recipeLayout.getFluidStacks(), ingredients);
	}

}
