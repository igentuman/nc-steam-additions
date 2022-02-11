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
	@ZenClass("mods.ncsteamadditions.digital_transformer")
	@ZenRegister
	public static class DigitalTransformerHandler
	{

		@ZenMethod
		public static void addRecipe(IIngredient input1,IIngredient input2,IIngredient input3,IIngredient input4,IIngredient input5,IIngredient input6, IIngredient input7,IIngredient input8, IIngredient output1, IIngredient output2, @Optional(valueDouble = 1D) double timeMultiplier,@Optional(valueDouble = 1D) double powerMultiplier)
		{
			CraftTweakerAPI.apply(new CTAddRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[ProcessorsRegistry.get().DIGITAL_TRANSFORMER.GUID], Lists.newArrayList(input1, input2, input3, input4,input5,input6,input7,input8, output1,output2, timeMultiplier, powerMultiplier)));
		}

		@ZenMethod
		public static void removeRecipeWithInput(IIngredient input1,IIngredient input2,IIngredient input3,IIngredient input4,IIngredient input5,IIngredient input6, IIngredient input7,IIngredient input8)
		{
			CraftTweakerAPI.apply(new CTRemoveRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[ProcessorsRegistry.get().DIGITAL_TRANSFORMER.GUID], IngredientSorption.INPUT, Lists.newArrayList(input1,input2,input3,input4,input5,input6,input7,input8)));
		}

		@ZenMethod
		public static void removeRecipeWithOutput(IIngredient output1)
		{
			CraftTweakerAPI.apply(new CTRemoveRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[ProcessorsRegistry.get().DIGITAL_TRANSFORMER.GUID], IngredientSorption.OUTPUT, Lists.newArrayList(output1)));
		}

		@ZenMethod
		public static void removeAllRecipes()
		{
			CraftTweakerAPI.apply(new CTClearRecipes(NCSteamAdditionsRecipes.processorRecipeHandlers[ProcessorsRegistry.get().DIGITAL_TRANSFORMER.GUID]));
		}
	}

	@ZenClass("mods.ncsteamadditions.steam_transformer")
	@ZenRegister
	public static class SteamTransformerHandler 
	{
		
		@ZenMethod
		public static void addRecipe(IIngredient input1,IIngredient input2,IIngredient input3,IIngredient input4,IIngredient input5, IIngredient output1, @Optional(valueDouble = 1D) double timeMultiplier)
		{
			CraftTweakerAPI.apply(new CTAddRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[ProcessorsRegistry.get().STEAM_TRANSFORMER.GUID], Lists.newArrayList(input1, input2, input3, input4,input5, output1, timeMultiplier)));
		}
		
		@ZenMethod
		public static void removeRecipeWithInput(IIngredient input1,IIngredient input2,IIngredient input3,IIngredient input4,IIngredient input5)
		{
			CraftTweakerAPI.apply(new CTRemoveRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[ProcessorsRegistry.get().STEAM_TRANSFORMER.GUID], IngredientSorption.INPUT, Lists.newArrayList(input1,input2,input3,input4,input5)));
		}
		
		@ZenMethod
		public static void removeRecipeWithOutput(IIngredient output1)
		{
			CraftTweakerAPI.apply(new CTRemoveRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[ProcessorsRegistry.get().STEAM_TRANSFORMER.GUID], IngredientSorption.OUTPUT, Lists.newArrayList(output1)));
		}
		
		@ZenMethod
		public static void removeAllRecipes() 
		{
			CraftTweakerAPI.apply(new CTClearRecipes(NCSteamAdditionsRecipes.processorRecipeHandlers[ProcessorsRegistry.get().STEAM_TRANSFORMER.GUID]));
		}
	}

	@ZenClass("mods.ncsteamadditions.steam_crusher")
	@ZenRegister
	public static class SteamCrusherHandler
	{

		@ZenMethod
		public static void addRecipe(IIngredient input1,IIngredient input2, IIngredient output1, @Optional(valueDouble = 1D) double timeMultiplier)
		{
			CraftTweakerAPI.apply(new CTAddRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[ProcessorsRegistry.get().STEAM_CRUSHER.GUID], Lists.newArrayList(input1, input2, output1, timeMultiplier)));
		}

		@ZenMethod
		public static void removeRecipeWithInput(IIngredient input1,IIngredient input2)
		{
			CraftTweakerAPI.apply(new CTRemoveRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[ProcessorsRegistry.get().STEAM_CRUSHER.GUID], IngredientSorption.INPUT, Lists.newArrayList(input1,input2)));
		}

		@ZenMethod
		public static void removeRecipeWithOutput(IIngredient output1)
		{
			CraftTweakerAPI.apply(new CTRemoveRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[ProcessorsRegistry.get().STEAM_CRUSHER.GUID], IngredientSorption.OUTPUT, Lists.newArrayList(output1)));
		}

		@ZenMethod
		public static void removeAllRecipes()
		{
			CraftTweakerAPI.apply(new CTClearRecipes(NCSteamAdditionsRecipes.processorRecipeHandlers[ProcessorsRegistry.get().STEAM_CRUSHER.GUID]));
		}
	}

	@ZenClass("mods.ncsteamadditions.steam_boiler")
	@ZenRegister
	public static class SteamBoilerHandler
	{

		@ZenMethod
		public static void addRecipe(IIngredient input1,IIngredient input2, IIngredient output1, @Optional(valueDouble = 1D) double timeMultiplier)
		{
			CraftTweakerAPI.apply(new CTAddRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[ProcessorsRegistry.get().STEAM_BOILER.GUID], Lists.newArrayList(input1, input2, output1, timeMultiplier)));
		}

		@ZenMethod
		public static void removeRecipeWithInput(IIngredient input1,IIngredient input2)
		{
			CraftTweakerAPI.apply(new CTRemoveRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[ProcessorsRegistry.get().STEAM_BOILER.GUID], IngredientSorption.INPUT, Lists.newArrayList(input1,input2)));
		}

		@ZenMethod
		public static void removeRecipeWithOutput(IIngredient output1)
		{
			CraftTweakerAPI.apply(new CTRemoveRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[ProcessorsRegistry.get().STEAM_BOILER.GUID], IngredientSorption.OUTPUT, Lists.newArrayList(output1)));
		}

		@ZenMethod
		public static void removeAllRecipes()
		{
			CraftTweakerAPI.apply(new CTClearRecipes(NCSteamAdditionsRecipes.processorRecipeHandlers[ProcessorsRegistry.get().STEAM_BOILER.GUID]));
		}
	}
	@ZenClass("mods.ncsteamadditions.steam_compactor")
	@ZenRegister
	public static class SteamCompactorHandler
	{

		@ZenMethod
		public static void addRecipe(IIngredient input1,IIngredient input2, IIngredient output1, @Optional(valueDouble = 1D) double timeMultiplier)
		{
			CraftTweakerAPI.apply(new CTAddRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[ProcessorsRegistry.get().STEAM_COMPACTOR.GUID], Lists.newArrayList(input1, input2, output1, timeMultiplier)));
		}

		@ZenMethod
		public static void removeRecipeWithInput(IIngredient input1,IIngredient input2)
		{
			CraftTweakerAPI.apply(new CTRemoveRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[ProcessorsRegistry.get().STEAM_COMPACTOR.GUID], IngredientSorption.INPUT, Lists.newArrayList(input1,input2)));
		}

		@ZenMethod
		public static void removeRecipeWithOutput(IIngredient output1)
		{
			CraftTweakerAPI.apply(new CTRemoveRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[ProcessorsRegistry.get().STEAM_COMPACTOR.GUID], IngredientSorption.OUTPUT, Lists.newArrayList(output1)));
		}

		@ZenMethod
		public static void removeAllRecipes()
		{
			CraftTweakerAPI.apply(new CTClearRecipes(NCSteamAdditionsRecipes.processorRecipeHandlers[ProcessorsRegistry.get().STEAM_COMPACTOR.GUID]));
		}
	}

	@ZenClass("mods.ncsteamadditions.steam_fluid_transformer")
	@ZenRegister
	public static class SteamFluidTransformerHandler
	{

		@ZenMethod
		public static void addRecipe(IIngredient input1,IIngredient input2, IIngredient input3, IIngredient input4, IIngredient output1, @Optional(valueDouble = 1D) double timeMultiplier)
		{
			CraftTweakerAPI.apply(new CTAddRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[ProcessorsRegistry.get().STEAM_FLUID_TRANSFORMER.GUID], Lists.newArrayList(input1, input2, input3, input4, output1, timeMultiplier)));
		}

		@ZenMethod
		public static void removeRecipeWithInput(IIngredient input1,IIngredient input2,IIngredient input3,IIngredient input4)
		{
			CraftTweakerAPI.apply(new CTRemoveRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[ProcessorsRegistry.get().STEAM_FLUID_TRANSFORMER.GUID], IngredientSorption.INPUT, Lists.newArrayList(input1,input2, input3, input4)));
		}

		@ZenMethod
		public static void removeRecipeWithOutput(IIngredient output1)
		{
			CraftTweakerAPI.apply(new CTRemoveRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[ProcessorsRegistry.get().STEAM_FLUID_TRANSFORMER.GUID], IngredientSorption.OUTPUT, Lists.newArrayList(output1)));
		}

		@ZenMethod
		public static void removeAllRecipes()
		{
			CraftTweakerAPI.apply(new CTClearRecipes(NCSteamAdditionsRecipes.processorRecipeHandlers[ProcessorsRegistry.get().STEAM_FLUID_TRANSFORMER.GUID]));
		}
	}

	@ZenClass("mods.ncsteamadditions.steam_blender")
	@ZenRegister
	public static class SteamBlenderHandler
	{

		@ZenMethod
		public static void addRecipe(IIngredient input1,IIngredient input2, IIngredient input3, IIngredient input4, IIngredient output1, @Optional(valueDouble = 1D) double timeMultiplier)
		{
			CraftTweakerAPI.apply(new CTAddRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[ProcessorsRegistry.get().STEAM_BLENDER.GUID], Lists.newArrayList(input1, input2, input3, input4, output1, timeMultiplier)));
		}

		@ZenMethod
		public static void removeRecipeWithInput(IIngredient input1,IIngredient input2,IIngredient input3,IIngredient input4)
		{
			CraftTweakerAPI.apply(new CTRemoveRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[ProcessorsRegistry.get().STEAM_BLENDER.GUID], IngredientSorption.INPUT, Lists.newArrayList(input1,input2, input3, input4)));
		}

		@ZenMethod
		public static void removeRecipeWithOutput(IIngredient output1)
		{
			CraftTweakerAPI.apply(new CTRemoveRecipe(NCSteamAdditionsRecipes.processorRecipeHandlers[ProcessorsRegistry.get().STEAM_BLENDER.GUID], IngredientSorption.OUTPUT, Lists.newArrayList(output1)));
		}

		@ZenMethod
		public static void removeAllRecipes()
		{
			CraftTweakerAPI.apply(new CTClearRecipes(NCSteamAdditionsRecipes.processorRecipeHandlers[ProcessorsRegistry.get().STEAM_BLENDER.GUID]));
		}
	}
}
