package igentuman.ncsteamadditions.jei;

import igentuman.ncsteamadditions.NCSteamAdditions;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import nc.integration.jei.NCJEI;
import nc.radiation.RadiationHelper;
import nc.recipe.*;
import nc.util.*;
import nclegacy.jei.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.util.*;

public abstract class JEIProcessorRecipeWrapper<WRAPPER extends JEIProcessorRecipeWrapper<WRAPPER>> extends JEIBasicRecipeWrapperLegacy<WRAPPER> {
    private final int infoX;
    private final int infoY;
    private final int infoWidth;
    private final int infoHeight;
    private static final String BASE_TIME = Lang.localize("jei.nuclearcraft.base_process_time");
    private static final String BASE_POWER = Lang.localize("jei.nuclearcraft.base_process_power");
    private static final String BASE_RADIATION = Lang.localize("jei.nuclearcraft.base_process_radiation");
    public final IDrawableAnimated animatedArrow;

    public JEIProcessorRecipeWrapper(IGuiHelper guiHelper, IJEIHandlerLegacy<WRAPPER> handler, BasicRecipeHandler recipeHandler, BasicRecipe recipe, int backX, int backY, int arrowX, int arrowY, int arrowWidth, int arrowHeight, int arrowPosX, int arrowPosY, int infoX, int infoY, int infoWidth, int infoHeight) {
        super(guiHelper, handler, recipeHandler, recipe, backX, backY, arrowX, arrowY, arrowWidth, arrowHeight, arrowPosX, arrowPosY);
        this.infoX = infoX - backX;
        this.infoY = infoY - backY;
        this.infoWidth = infoWidth;
        this.infoHeight = infoHeight;
        ResourceLocation location = new ResourceLocation(NCSteamAdditions.MOD_ID + ":textures/gui/" + handler.getTextureName()  + ".png");
        IDrawableStatic staticArrow = guiHelper.createDrawable(location, 0, 168, 135, 20);
        animatedArrow = guiHelper.createAnimatedDrawable(staticArrow, 200, IDrawableAnimated.StartDirection.LEFT, false);
    }



    protected int getProgressArrowTime() {
        return (int)(this.getBaseProcessTime() / 4.0D);
    }

    protected abstract double getBaseProcessTime();

    protected abstract double getBaseProcessPower();

    protected double getBaseProcessRadiation() {
        return this.recipe == null ? 0.0D : this.recipe.getBaseProcessRadiation();
    }

    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        List<String> tooltip = new ArrayList();
        if (mouseX >= this.infoX && mouseY >= this.infoY && mouseX < this.infoX + this.infoWidth + 1 && mouseY < this.infoY + this.infoHeight + 1) {
            tooltip.add(TextFormatting.GREEN + BASE_TIME + " " + TextFormatting.WHITE + UnitHelper.applyTimeUnitShort(this.getBaseProcessTime(), 3));
            tooltip.add(TextFormatting.LIGHT_PURPLE + BASE_POWER + " " + TextFormatting.WHITE + UnitHelper.prefix(this.getBaseProcessPower(), 5, "RF/t"));
            double radiation = this.getBaseProcessRadiation();
            if (radiation > 0.0D) {
                tooltip.add(TextFormatting.GOLD + BASE_RADIATION + " " + RadiationHelper.radsColoredPrefix(radiation, true));
            }
        }

        return tooltip;
    }
}
