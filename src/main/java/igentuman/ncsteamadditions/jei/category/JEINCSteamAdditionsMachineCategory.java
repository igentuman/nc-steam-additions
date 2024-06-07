package igentuman.ncsteamadditions.jei.category;

import igentuman.ncsteamadditions.NCSteamAdditions;
import igentuman.ncsteamadditions.processors.AbstractProcessor;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import nc.recipe.ingredient.*;
import nc.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.*;

public abstract class JEINCSteamAdditionsMachineCategory<WRAPPER extends JEIMachineRecipeWrapper<WRAPPER>> extends JEIBasicCategory<WRAPPER>
{

    private final IDrawable background;
    protected String recipeTitle;
    protected final int backPosX, backPosY;
    public IDrawableAnimated animatedArrow;
    public ResourceLocation location;
    public JEINCSteamAdditionsMachineCategory(IGuiHelper guiHelper, IJEIHandler handler, String title, int backX, int backY, int backWidth, int backHeight)
    {
        this(guiHelper, handler, title, "", backX, backY, backWidth, backHeight);
    }

    public JEINCSteamAdditionsMachineCategory(IGuiHelper guiHelper, IJEIHandler handler, String title, String guiExtra, int backX, int backY, int backWidth, int backHeight)
    {
        super(handler);
        location = new ResourceLocation(NCSteamAdditions.MOD_ID + ":textures/gui/" + handler.getTextureName() + guiExtra + ".png");
        background = guiHelper.createDrawable(location, backX, backY, backWidth, backHeight);
        recipeTitle = Lang.localize("tile." + NCSteamAdditions.MOD_ID + "." + title + ".name");
        backPosX = backX + 1;
        backPosY = backY + 1;
        IDrawableStatic staticArrow = guiHelper.createDrawable(location, 0, 168, 135, 20);
        animatedArrow = guiHelper.createAnimatedDrawable(staticArrow, 300 , IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawExtras(Minecraft minecraft)
    {
        animatedArrow.draw(minecraft, 6, 26);
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
            int outputIndex = slotIndex - recipeWrapper.recipeHandler.getItemInputSize() - 1;
            if (outputIndex >= 0 && outputIndex <= recipeWrapper.recipeHandler.getItemOutputSize() && recipeWrapper.recipe.getItemProducts().get(outputIndex) instanceof ChanceItemIngredient)
            {
                ChanceItemIngredient chanceIngredient = (ChanceItemIngredient)recipeWrapper.recipe.getItemProducts().get(outputIndex);
                tooltip.add(TextFormatting.WHITE + Lang.localize("jei.nuclearcraft.chance_output", chanceIngredient.minStackSize, chanceIngredient.getMaxStackSize(0), NCMath.decimalPlaces(chanceIngredient.meanStackSize, 2)));
            }
        });

        recipeLayout.getFluidStacks().addTooltipCallback((slotIndex, input, ingredient, tooltip) ->
        {
            int outputIndex = slotIndex - recipeWrapper.recipeHandler.getFluidInputSize();
            if (outputIndex >= 0 && outputIndex <= recipeWrapper.recipeHandler.getFluidOutputSize() && recipeWrapper.recipe.getFluidProducts().get(outputIndex) instanceof ChanceFluidIngredient)
            {
                ChanceFluidIngredient chanceIngredient = (ChanceFluidIngredient)recipeWrapper.recipe.getFluidProducts().get(outputIndex);
                tooltip.add(TextFormatting.WHITE + Lang.localize("jei.nuclearcraft.chance_output", chanceIngredient.minStackSize, chanceIngredient.getMaxStackSize(0), NCMath.decimalPlaces(chanceIngredient.meanStackSize, 2)));
            }
        });

        mapLayout(recipeLayout, ingredients);
    }

    public abstract void mapLayout(IRecipeLayout recipeLayout, IIngredients ingredients);

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
