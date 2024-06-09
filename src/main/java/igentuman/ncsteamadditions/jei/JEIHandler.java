package igentuman.ncsteamadditions.jei;

import com.google.common.collect.Lists;
import igentuman.ncsteamadditions.processors.AbstractProcessor;
import mezz.jei.api.IGuiHelper;
import nc.integration.jei.*;
import nc.recipe.BasicRecipeHandler;
import nc.util.StackHelper;
import nclegacy.jei.*;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import java.util.*;

public class JEIHandler implements IJEIHandlerLegacy
{

    private BasicRecipeHandler recipeHandler;
    private Class<? extends JEIBasicRecipeWrapperLegacy> recipeWrapper;
    private boolean enabled;
    private List<ItemStack> crafters;
    private String textureName;
    private AbstractProcessor processor;

    public JEIHandler(AbstractProcessor processor, BasicRecipeHandler recipeHandler, Block crafter, String textureName, Class<? extends JEIBasicRecipeWrapperLegacy> recipeWrapper)
    {
        this(recipeHandler, Lists.newArrayList(crafter), textureName, recipeWrapper);
        this.processor = processor;
    }

    JEIHandler(BasicRecipeHandler recipeHandler, Block crafter, String textureName, Class<? extends JEIBasicRecipeWrapperLegacy> recipeWrapper)
    {
        this(recipeHandler, Lists.newArrayList(crafter), textureName, recipeWrapper);
    }

    JEIHandler(BasicRecipeHandler recipeHandler, List<Block> crafters, String textureName, Class<? extends JEIBasicRecipeWrapperLegacy> recipeWrapper)
    {
        this.recipeHandler = recipeHandler;
        this.recipeWrapper = recipeWrapper;
        this.crafters = new ArrayList<ItemStack>();
        for (Block crafter : crafters) this.crafters.add(StackHelper.fixItemStack(crafter));
        this.textureName = textureName;
    }

    @Override
    public JEIBasicCategoryLegacy getCategory(IGuiHelper guiHelper)
    {
        return processor.getRecipeCategory(guiHelper);
    }

    @Override
    public BasicRecipeHandler getRecipeHandler()
    {
        return recipeHandler;
    }

    @Override
    public Class getRecipeWrapperClass()
    {
        return recipeWrapper;
    }

    @Override
    public List<JEIBasicRecipeWrapperLegacy> getJEIRecipes(IGuiHelper guiHelper)
    {
        return JEIHelperLegacy.getJEIRecipes(guiHelper, this, getRecipeHandler(), getRecipeWrapperClass());
    }


    @Override
    public String getUid()
    {
        return getRecipeHandler().getName();
    }

    @Override
    public boolean getEnabled()
    {
        return true;
    }

    @Override
    public List<ItemStack> getCrafters()
    {
        return crafters;
    }

    @Override
    public String getTextureName()
    {
        return textureName;
    }
}
