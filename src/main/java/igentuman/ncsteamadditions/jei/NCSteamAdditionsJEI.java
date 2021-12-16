package igentuman.ncsteamadditions.jei;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import igentuman.ncsteamadditions.block.Blocks;
import igentuman.ncsteamadditions.jei.catergory.SteamTransformerCategory;
import igentuman.ncsteamadditions.jei.recipe.NCSteamAdditionsRecipeWrapper;
import igentuman.ncsteamadditions.processors.AbstractProcessor;
import igentuman.ncsteamadditions.processors.ProcessorsList;
import igentuman.ncsteamadditions.processors.SteamTransformer;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import nc.integration.jei.JEIBasicCategory;
import nc.integration.jei.JEIBasicRecipeWrapper;
import nc.integration.jei.JEIHelper;
import nc.integration.jei.NCJEI.IJEIHandler;
import nc.recipe.BasicRecipeHandler;
import nc.util.StackHelper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class NCSteamAdditionsJEI implements IModPlugin
{
	public void registerCategories(IRecipeCategoryRegistration registry)
	{
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		registry.addRecipeCategories(
				JEIHandler.STEAM_TRANSFORMER.getCategory(guiHelper)
				);
		
	}

	public void register(IModRegistry registry)
	{
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

		registry.addRecipes(JEIHandler.STEAM_TRANSFORMER.getJEIRecipes(guiHelper), JEIHandler.STEAM_TRANSFORMER.getUid());
		registry.addRecipeCatalyst(JEIHandler.STEAM_TRANSFORMER.getCrafters().get(0),JEIHandler.STEAM_TRANSFORMER.getUid());

	}

	
	public enum JEIHandler implements IJEIHandler
	{

		STEAM_TRANSFORMER(NCSteamAdditionsRecipes.steam_transformer, Blocks.blocks[SteamTransformer.GUID], SteamTransformer.code, NCSteamAdditionsRecipeWrapper.SteamTransformer.class);

		private BasicRecipeHandler recipeHandler;
		private Class<? extends JEIBasicRecipeWrapper> recipeWrapper;
		private boolean enabled;
		private List<ItemStack> crafters;
		private String textureName;
		
		JEIHandler(BasicRecipeHandler recipeHandler, Block crafter, String textureName, Class<? extends JEIBasicRecipeWrapper> recipeWrapper) 
		{
			this(recipeHandler, Lists.newArrayList(crafter), textureName, recipeWrapper);
		}
		

		JEIHandler(BasicRecipeHandler recipeHandler, List<Block> crafters, String textureName, Class<? extends JEIBasicRecipeWrapper> recipeWrapper) 
		{
			this.recipeHandler = recipeHandler;
			this.recipeWrapper = recipeWrapper;
			this.crafters = new ArrayList<ItemStack>();
			for (Block crafter : crafters) this.crafters.add(StackHelper.fixItemStack(crafter));
			this.textureName = textureName;
		}
		
		@Override
		public JEIBasicCategory getCategory(IGuiHelper guiHelper) 
		{
			switch (this)
			{
			case STEAM_TRANSFORMER:
				return new SteamTransformerCategory(guiHelper, this);
			default:
				return new SteamTransformerCategory(guiHelper, this);
			}
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
		public List<JEIBasicRecipeWrapper> getJEIRecipes(IGuiHelper guiHelper) 
		{
			return JEIHelper.getJEIRecipes(guiHelper, this, getRecipeHandler(), getRecipeWrapperClass());
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
	
	
	
	
	

}
