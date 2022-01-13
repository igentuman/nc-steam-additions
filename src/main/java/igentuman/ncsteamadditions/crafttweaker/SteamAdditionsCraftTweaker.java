package igentuman.ncsteamadditions.crafttweaker;

import com.google.common.collect.Lists;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import igentuman.ncsteamadditions.processors.*;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import nc.integration.crafttweaker.CTAddRecipe;
import nc.integration.crafttweaker.CTClearRecipes;
import nc.integration.crafttweaker.CTRemoveRecipe;
import nc.recipe.IngredientSorption;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

public class SteamAdditionsCraftTweaker
{
	@ZenClass("mods.ncsteamadditions.steam_transformer")
	@ZenRegister
	public static class SteamTransformerHandler 
	{
		
		@ZenMethod
		public static void addRecipe(IIngredient input1,IIngredient input2,IIngredient input3,IIngredient input4,IIngredient input5, IIngredient output1, @Optional(valueDouble = 1D) double timeMultiplier)
		{
			CraftTweakerAPI.apply(new CTAddRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[SteamTransformer.GUID], Lists.newArrayList(input1, input2, input3, input4,input5, output1, timeMultiplier)));
		}
		
		@ZenMethod
		public static void removeRecipeWithInput(IIngredient input1,IIngredient input2,IIngredient input3,IIngredient input4,IIngredient input5)
		{
			CraftTweakerAPI.apply(new CTRemoveRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[SteamTransformer.GUID], IngredientSorption.INPUT, Lists.newArrayList(input1,input2,input3,input4,input5)));
		}
		
		@ZenMethod
		public static void removeRecipeWithOutput(IIngredient output1)
		{
			CraftTweakerAPI.apply(new CTRemoveRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[SteamTransformer.GUID], IngredientSorption.OUTPUT, Lists.newArrayList(output1)));
		}
		
		@ZenMethod
		public static void removeAllRecipes() 
		{
			CraftTweakerAPI.apply(new CTClearRecipes(NCSteamAdditionsRecipes.processorRecipeHandlers[SteamTransformer.GUID]));
		}
	}

	@ZenClass("mods.ncsteamadditions.steam_crusher")
	@ZenRegister
	public static class SteamCrusherHandler
	{

		@ZenMethod
		public static void addRecipe(IIngredient input1,IIngredient input2, IIngredient output1, @Optional(valueDouble = 1D) double timeMultiplier)
		{
			CraftTweakerAPI.apply(new CTAddRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[SteamCrusher.GUID], Lists.newArrayList(input1, input2, output1, timeMultiplier)));
		}

		@ZenMethod
		public static void removeRecipeWithInput(IIngredient input1,IIngredient input2)
		{
			CraftTweakerAPI.apply(new CTRemoveRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[SteamCrusher.GUID], IngredientSorption.INPUT, Lists.newArrayList(input1,input2)));
		}

		@ZenMethod
		public static void removeRecipeWithOutput(IIngredient output1)
		{
			CraftTweakerAPI.apply(new CTRemoveRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[SteamCrusher.GUID], IngredientSorption.OUTPUT, Lists.newArrayList(output1)));
		}

		@ZenMethod
		public static void removeAllRecipes()
		{
			CraftTweakerAPI.apply(new CTClearRecipes(NCSteamAdditionsRecipes.processorRecipeHandlers[SteamCrusher.GUID]));
		}
	}

	@ZenClass("mods.ncsteamadditions.steam_boiler")
	@ZenRegister
	public static class SteamBoilerHandler
	{

		@ZenMethod
		public static void addRecipe(IIngredient input1,IIngredient input2, IIngredient output1, @Optional(valueDouble = 1D) double timeMultiplier)
		{
			CraftTweakerAPI.apply(new CTAddRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[SteamBoiler.GUID], Lists.newArrayList(input1, input2, output1, timeMultiplier)));
		}

		@ZenMethod
		public static void removeRecipeWithInput(IIngredient input1,IIngredient input2)
		{
			CraftTweakerAPI.apply(new CTRemoveRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[SteamBoiler.GUID], IngredientSorption.INPUT, Lists.newArrayList(input1,input2)));
		}

		@ZenMethod
		public static void removeRecipeWithOutput(IIngredient output1)
		{
			CraftTweakerAPI.apply(new CTRemoveRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[SteamBoiler.GUID], IngredientSorption.OUTPUT, Lists.newArrayList(output1)));
		}

		@ZenMethod
		public static void removeAllRecipes()
		{
			CraftTweakerAPI.apply(new CTClearRecipes(NCSteamAdditionsRecipes.processorRecipeHandlers[SteamBoiler.GUID]));
		}
	}
	@ZenClass("mods.ncsteamadditions.steam_compactor")
	@ZenRegister
	public static class SteamCompactorHandler
	{

		@ZenMethod
		public static void addRecipe(IIngredient input1,IIngredient input2, IIngredient output1, @Optional(valueDouble = 1D) double timeMultiplier)
		{
			CraftTweakerAPI.apply(new CTAddRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[SteamCompactor.GUID], Lists.newArrayList(input1, input2, output1, timeMultiplier)));
		}

		@ZenMethod
		public static void removeRecipeWithInput(IIngredient input1,IIngredient input2)
		{
			CraftTweakerAPI.apply(new CTRemoveRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[SteamCompactor.GUID], IngredientSorption.INPUT, Lists.newArrayList(input1,input2)));
		}

		@ZenMethod
		public static void removeRecipeWithOutput(IIngredient output1)
		{
			CraftTweakerAPI.apply(new CTRemoveRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[SteamCompactor.GUID], IngredientSorption.OUTPUT, Lists.newArrayList(output1)));
		}

		@ZenMethod
		public static void removeAllRecipes()
		{
			CraftTweakerAPI.apply(new CTClearRecipes(NCSteamAdditionsRecipes.processorRecipeHandlers[SteamCompactor.GUID]));
		}
	}

	@ZenClass("mods.ncsteamadditions.steam_fluid_transformer")
	@ZenRegister
	public static class SteamFluidTransformerHandler
	{

		@ZenMethod
		public static void addRecipe(IIngredient input1,IIngredient input2, IIngredient input3, IIngredient input4, IIngredient output1, @Optional(valueDouble = 1D) double timeMultiplier)
		{
			CraftTweakerAPI.apply(new CTAddRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[SteamFluidTransformer.GUID], Lists.newArrayList(input1, input2, input3, input4, output1, timeMultiplier)));
		}

		@ZenMethod
		public static void removeRecipeWithInput(IIngredient input1,IIngredient input2,IIngredient input3,IIngredient input4)
		{
			CraftTweakerAPI.apply(new CTRemoveRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[SteamFluidTransformer.GUID], IngredientSorption.INPUT, Lists.newArrayList(input1,input2, input3, input4)));
		}

		@ZenMethod
		public static void removeRecipeWithOutput(IIngredient output1)
		{
			CraftTweakerAPI.apply(new CTRemoveRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[SteamFluidTransformer.GUID], IngredientSorption.OUTPUT, Lists.newArrayList(output1)));
		}

		@ZenMethod
		public static void removeAllRecipes()
		{
			CraftTweakerAPI.apply(new CTClearRecipes(NCSteamAdditionsRecipes.processorRecipeHandlers[SteamFluidTransformer.GUID]));
		}
	}

	@ZenClass("mods.ncsteamadditions.steam_blender")
	@ZenRegister
	public static class SteamBlenderHandler
	{

		@ZenMethod
		public static void addRecipe(IIngredient input1,IIngredient input2, IIngredient input3, IIngredient input4, IIngredient output1, @Optional(valueDouble = 1D) double timeMultiplier)
		{
			CraftTweakerAPI.apply(new CTAddRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[SteamBlender.GUID], Lists.newArrayList(input1, input2, input3, input4, output1, timeMultiplier)));
		}

		@ZenMethod
		public static void removeRecipeWithInput(IIngredient input1,IIngredient input2,IIngredient input3,IIngredient input4)
		{
			CraftTweakerAPI.apply(new CTRemoveRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[SteamBlender.GUID], IngredientSorption.INPUT, Lists.newArrayList(input1,input2, input3, input4)));
		}

		@ZenMethod
		public static void removeRecipeWithOutput(IIngredient output1)
		{
			CraftTweakerAPI.apply(new CTRemoveRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[SteamBlender.GUID], IngredientSorption.OUTPUT, Lists.newArrayList(output1)));
		}

		@ZenMethod
		public static void removeAllRecipes()
		{
			CraftTweakerAPI.apply(new CTClearRecipes(NCSteamAdditionsRecipes.processorRecipeHandlers[SteamBlender.GUID]));
		}
	}
}
