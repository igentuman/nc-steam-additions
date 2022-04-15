package igentuman.ncsteamadditions.jei.catergory;

import igentuman.ncsteamadditions.NCSteamAdditions;
import igentuman.ncsteamadditions.processors.AbstractProcessor;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import nc.integration.jei.JEIBasicCategory;
import nc.integration.jei.JEIBasicRecipeWrapper;
import nc.integration.jei.JEIHelper;
import nc.integration.jei.JEIMachineRecipeWrapper;
import nc.integration.jei.NCJEI.IJEIHandler;
import nc.recipe.IngredientSorption;
import nc.recipe.ingredient.ChanceFluidIngredient;
import nc.recipe.ingredient.ChanceItemIngredient;
import nc.util.Lang;
import nc.util.NCMath;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public abstract class JEINCSteamAdditionsMachineCategory<WRAPPER extends JEIBasicRecipeWrapper<WRAPPER>> extends JEIBasicCategory<WRAPPER>
{

    private final IDrawable background;
    protected String recipeTitle;
    protected final int backPosX, backPosY;

    public JEINCSteamAdditionsMachineCategory(IGuiHelper guiHelper, IJEIHandler handler, String title, int backX, int backY, int backWidth, int backHeight)
    {
        this(guiHelper, handler, title, "", backX, backY, backWidth, backHeight);
    }

    public JEINCSteamAdditionsMachineCategory(IGuiHelper guiHelper, IJEIHandler handler, String title, String guiExtra, int backX, int backY, int backWidth, int backHeight)
    {
        super(handler);
        ResourceLocation location = new ResourceLocation(NCSteamAdditions.MOD_ID + ":textures/gui/" + handler.getTextureName() + guiExtra + ".png");
        background = guiHelper.createDrawable(location, backX, backY, backWidth, backHeight);
        recipeTitle = Lang.localise("tile." + NCSteamAdditions.MOD_ID + "." + title + ".name");
        backPosX = backX + 1;
        backPosY = backY + 1;
    }

    @Override
    public void drawExtras(Minecraft minecraft)
    {

    }

    @Override
    public String getModName()
    {
        return NCSteamAdditions.MOD_NAME;
    }

    @Override
    public IDrawable getBackground()
    {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, WRAPPER recipeWrapper, IIngredients ingredients)
    {
        recipeLayout.getItemStacks().addTooltipCallback((slotIndex, input, ingredient, tooltip) ->
        {
            int outputIndex = slotIndex - recipeWrapper.recipeHandler.getItemInputSize();
            if (outputIndex >= 0 && outputIndex <= recipeWrapper.recipeHandler.getItemOutputSize() && recipeWrapper.recipe.getItemProducts().get(outputIndex) instanceof ChanceItemIngredient)
            {
                ChanceItemIngredient chanceIngredient = (ChanceItemIngredient)recipeWrapper.recipe.getItemProducts().get(outputIndex);
                tooltip.add(TextFormatting.WHITE + Lang.localise("jei.nuclearcraft.chance_output", chanceIngredient.minStackSize, chanceIngredient.getMaxStackSize(0), NCMath.decimalPlaces(chanceIngredient.meanStackSize, 2)));
            }
        });

        recipeLayout.getFluidStacks().addTooltipCallback((slotIndex, input, ingredient, tooltip) ->
        {
            int outputIndex = slotIndex - recipeWrapper.recipeHandler.getFluidInputSize();
            if (outputIndex >= 0 && outputIndex <= recipeWrapper.recipeHandler.getFluidOutputSize() && recipeWrapper.recipe.getFluidProducts().get(outputIndex) instanceof ChanceFluidIngredient)
            {
                ChanceFluidIngredient chanceIngredient = (ChanceFluidIngredient)recipeWrapper.recipe.getFluidProducts().get(outputIndex);
                tooltip.add(TextFormatting.WHITE + Lang.localise("jei.nuclearcraft.chance_output", chanceIngredient.minStackSize, chanceIngredient.getMaxStackSize(0), NCMath.decimalPlaces(chanceIngredient.meanStackSize, 2)));
            }
        });

        JEIHelper.RecipeItemMapper itemMapper = new JEIHelper.RecipeItemMapper();
        JEIHelper.RecipeFluidMapper fluidMapper = new JEIHelper.RecipeFluidMapper();
        int x = getFluidsLeft();
        if(getProcessor().getInputFluids() > 0) {
            for (int i = 0; i < getProcessor().getInputFluids(); i++) {
                fluidMapper.map(IngredientSorption.INPUT, i, i+1, x - backPosX, getFluidsTop() - backPosY, 16, 16);
                x+=getCellSpan();
            }
        }

        x = getItemsLeft();
        if(getProcessor().getInputItems() > 0) {
            for (int i = 0; i < getProcessor().getInputItems(); i++) {
                itemMapper.map(IngredientSorption.INPUT, i, i+1, x - backPosX, getItemsTop() - backPosY);
                x+=getCellSpan();
            }
        }

        x = 152;
        if(getProcessor().getOutputFluids() > 0) {
            for (int i = 0; i < getProcessor().getOutputFluids(); i++) {
                fluidMapper.map(IngredientSorption.OUTPUT, i, i, x - backPosX, getFluidsTop() - backPosY,16, 16);
                x+=getCellSpan();

            }
        }

        x = 152;
        if(getProcessor().getOutputItems() > 0) {
            for (int i = 0; i < getProcessor().getOutputItems(); i++) {
                itemMapper.map(IngredientSorption.OUTPUT, i, i, x - backPosX, getItemsTop() - backPosY);
                x+=getCellSpan();
            }
        }

        itemMapper.mapItemsTo(recipeLayout.getItemStacks(), ingredients);
        fluidMapper.mapFluidsTo(recipeLayout.getFluidStacks(), ingredients);
    }

    public AbstractProcessor processor;

    public AbstractProcessor getProcessor()
    {
        return processor;
    }

    protected abstract int getItemsLeft();

    protected abstract int getFluidsLeft();

    protected abstract int getItemsTop();

    protected abstract int getFluidsTop();

    protected abstract int getCellSpan();


    @Override
    public String getTitle()
    {
        return recipeTitle;
    }
}