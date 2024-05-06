package igentuman.ncsteamadditions.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import igentuman.ncsteamadditions.NCSteamAdditions;
import nc.util.Lang;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.GuiConfigEntries.CategoryEntry;
import net.minecraftforge.fml.client.config.GuiConfigEntries.IConfigEntry;
import net.minecraftforge.fml.client.config.IConfigElement;

public class NCSteamAdditionsConfigGUIFactory implements IModGuiFactory
{

	@Override
	public void initialize(Minecraft minecraftInstance)
	{
	}

	public Class<? extends GuiScreen> mainConfigGuiClass()
	{
		return NCSteamAdditionsConfigGui.class;
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
	{
		return null;
	}

	public static class NCSteamAdditionsConfigGui extends GuiConfig
	{

		public NCSteamAdditionsConfigGui(GuiScreen parentScreen)
		{
			super(parentScreen, getConfigElements(), NCSteamAdditions.MOD_ID, false, false,
					Lang.localize("gui.ncsteamadditions.config.main_title"));
		}

		private static List<IConfigElement> getConfigElements()
		{
			List<IConfigElement> list = new ArrayList<>();
			list.add(categoryElement(NCSteamAdditionsConfig.CATEGORY_PROCESSORS, CategoryEntryProcessors.class));
			list.add(categoryElement(NCSteamAdditionsConfig.CATEGORY_WORLDGEN, CategoryEntryWorldgen.class));
			list.add(categoryElement(NCSteamAdditionsConfig.CATEGORY_PIPES, CategoryEntryPipes.class));
			list.add(categoryElement(NCSteamAdditionsConfig.CATEGORY_RECIPES, CategoryEntryRecipes.class));
			return list;
		}

		private static DummyCategoryElement categoryElement(String categoryName,
				Class<? extends IConfigEntry> categoryClass)
		{
			return new DummyCategoryElement(Lang.localize("gui.ncsteamadditions.config.category." + categoryName),
					"gui.ncsteamadditions.config.category." + categoryName, categoryClass);
		}

		public static class CategoryEntryProcessors extends CategoryEntry implements INCSteamAdditionsConfigCategory
		{

			public CategoryEntryProcessors(GuiConfig owningScreen, GuiConfigEntries owningEntryList,
					IConfigElement configElement)
			{
				super(owningScreen, owningEntryList, configElement);
			}

			@Override
			protected GuiScreen buildChildScreen()
			{
				return buildChildScreen(NCSteamAdditionsConfig.CATEGORY_PROCESSORS, owningScreen, configElement);
			}
		}

		public static class CategoryEntryPipes extends CategoryEntry implements INCSteamAdditionsConfigCategory
		{

			public CategoryEntryPipes(GuiConfig owningScreen, GuiConfigEntries owningEntryList,
										   IConfigElement configElement)
			{
				super(owningScreen, owningEntryList, configElement);
			}

			@Override
			protected GuiScreen buildChildScreen()
			{
				return buildChildScreen(NCSteamAdditionsConfig.CATEGORY_PIPES, owningScreen, configElement);
			}
		}

		public static class CategoryEntryRecipes extends CategoryEntry implements INCSteamAdditionsConfigCategory
		{

			public CategoryEntryRecipes(GuiConfig owningScreen, GuiConfigEntries owningEntryList,
									  IConfigElement configElement)
			{
				super(owningScreen, owningEntryList, configElement);
			}

			@Override
			protected GuiScreen buildChildScreen()
			{
				return buildChildScreen(NCSteamAdditionsConfig.CATEGORY_RECIPES, owningScreen, configElement);
			}
		}

		public static class CategoryEntryWorldgen extends CategoryEntry implements INCSteamAdditionsConfigCategory
		{

			public CategoryEntryWorldgen(GuiConfig owningScreen, GuiConfigEntries owningEntryList,
										   IConfigElement configElement)
			{
				super(owningScreen, owningEntryList, configElement);
			}

			@Override
			protected GuiScreen buildChildScreen()
			{
				return buildChildScreen(NCSteamAdditionsConfig.CATEGORY_WORLDGEN, owningScreen, configElement);
			}
		}

	}

	@Override
	public boolean hasConfigGui()
	{
		return true;
	}

	@Override
	public GuiScreen createConfigGui(GuiScreen parentScreen)
	{
		return new NCSteamAdditionsConfigGui(parentScreen);
	}
}
