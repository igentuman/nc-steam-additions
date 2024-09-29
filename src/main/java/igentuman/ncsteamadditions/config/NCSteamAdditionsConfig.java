package igentuman.ncsteamadditions.config;

import igentuman.ncsteamadditions.NCSteamAdditions;
import nc.util.Lang;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.*;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.*;

import java.io.File;
import java.util.*;

import static nc.config.NCConfig.sync;


public class NCSteamAdditionsConfig {

	protected static final boolean LIST = false, ARRAY = true;
	private static Configuration config = null;
	public static int[] processor_time;

	public static final String CATEGORY_PROCESSORS = "processors";
	public static final String CATEGORY_SOLAR = "solar_panels";
	public static final String CATEGORY_PIPES = "pipes";
	public static final String CATEGORY_RECIPES = "recipes";
	public static final String CATEGORY_WORLDGEN = "worldgen";
	public static final String CUSTOM = "custom";

	public static Configuration getConfig()
	{
		return config;
	}
	public static int pipeCapacity;
	public static boolean overrideNcRecipes;
	public static int pipeTransfer;
	public static int turbineRF;
	public static int efficiencyCap;
	public static int efficiencyChangeSpeed;
	public static int digitalTransformerRF;
	public static int digitalTransformerResetTime;
	public static int digitalTransformerEfficienctCap;
	public static int[] ore_dims;
	public static boolean ore_dims_list_type;
	public static boolean[] ore_gen;
	public static int[] ore_size;
	public static int[] ore_rate;
	public static int[] ore_min_height;
	public static int[] ore_max_height;
	public static boolean spawn_villager;

	public static boolean[] ore_drops;
	public static boolean ore_hide_disabled;
	public static int[] ore_harvest_levels;

	public static double turbineConversion;
	public static double boilerConversion;

	public static double processorsSoundVolume;

	public static boolean makeHXalive;

	public static int[] solar_power;

	public static void preInit()
	{
		config = new Configuration(new File(Loader.instance().getConfigDir(), "ncsteamadditions.cfg"));
		syncConfig(true, true);
	}
	
	public static void clientPreInit()
	{
		MinecraftForge.EVENT_BUS.register(new ClientConfigEventHandler());
	}


	private static void syncConfig(boolean loadFromFile, boolean setFromConfig)
	{
		if (loadFromFile) config.load();

		Property solarPower = config.get(CATEGORY_SOLAR, "solar_power", new int[] {1280, 5120, 20480, 81920, 327680, 1310720, 5242880, 20971520}, Lang.localize("gui.ncsteamadditions.config.solar_power.generation.comment"), 0, 32767);
		solarPower.setLanguageKey("gui.ncsteamadditions.config.solar_power.generation");

		Property propertyProcessorTime = config.get(CATEGORY_PROCESSORS, "time", new int[] {800,400,400,800,400,400,500,400,12000,100}, Lang.localize("gui.ncsteamadditions.config.processors.time.comment"), 0, 32767);
		propertyProcessorTime.setLanguageKey("gui.ncsteamadditions.config.processors.time");

		Property efficiency_cap = config.get(CATEGORY_PROCESSORS, "efficiency_cap", 150, Lang.localize("gui.ncsteamadditions.config.processors.efficiency_cap.comment"),0,5000);
		efficiency_cap.setLanguageKey("gui.ncsteamadditions.config.processors.efficiency_cap");

		Property processors_sound_volume = config.get(CATEGORY_PROCESSORS, "processors_sound_volume", 0.5, Lang.localize("gui.ncsteamadditions.config.processors.processors_sound_volume.comment"),0,1);
		processors_sound_volume.setLanguageKey("gui.ncsteamadditions.config.processors.processors_sound_volume");

		Property propertyBoilerConversion = config.get(CATEGORY_PROCESSORS, "boiler_conversion", 1.25F, Lang.localize("gui.ncsteamadditions.config.processors.boiler_conversion.comment"),0,32767);
		propertyBoilerConversion.setLanguageKey("gui.ncsteamadditions.config.processors.boiler_conversion");

		Property propertyTurbineRF = config.get(CATEGORY_PROCESSORS, "turbine_rf", 12, Lang.localize("gui.ncsteamadditions.config.processors.turbine_rf.comment"),0,32767);
		propertyTurbineRF.setLanguageKey("gui.ncsteamadditions.config.processors.turbine_rf");

		Property propertyTurbineConversion = config.get(CATEGORY_PROCESSORS, "turbine_conversion", 0.50, Lang.localize("gui.ncsteamadditions.config.processors.turbine_conversion.comment"),0,32767);
		propertyTurbineConversion.setLanguageKey("gui.ncsteamadditions.config.processors.turbine_conversion");

		Property capacity = config.get(CATEGORY_PIPES, "capacity", 5000, Lang.localize("gui.ncsteamadditions.config.pipes.capacity.comment"),0,32767);
		capacity.setLanguageKey("gui.ncsteamadditions.config.pipes.capacity");

		Property transfer = config.get(CATEGORY_PIPES, "transfer", 1000, Lang.localize("gui.ncsteamadditions.config.pipes.transfer.comment"),0,32767);
		transfer.setLanguageKey("gui.ncsteamadditions.config.pipes.transfer");

		Property digitalTransformerBasePower = config.get(CATEGORY_PROCESSORS, "digital_transformer_reset_time", 20, Lang.localize("gui.ncsteamadditions.config.processors.digital_transformer_reset_time.comment"),0,32767);
		digitalTransformerBasePower.setLanguageKey("gui.ncsteamadditions.config.processors.digital_transformer_reset_time");

		Property digitalTransformerReset = config.get(CATEGORY_PROCESSORS, "digital_transformer_rf", 1024, Lang.localize("gui.ncsteamadditions.config.processors.digital_transformer_rf.comment"),0,32767);
		digitalTransformerReset.setLanguageKey("gui.ncsteamadditions.config.processors.digital_transformer_rf");

		Property digital_transformer_efficiency_cap = config.get(CATEGORY_PROCESSORS, "digital_transformer_efficiency_cap", 1500, Lang.localize("gui.ncsteamadditions.config.processors.efficiency_cap.comment"),0,2300);
		digital_transformer_efficiency_cap.setLanguageKey("gui.ncsteamadditions.config.processors.digital_transformer_efficiency_cap");



		Property efficiency_change_speed = config.get(CATEGORY_PROCESSORS, "efficiency_change_speed", 50, Lang.localize("gui.ncsteamadditions.config.processors.efficiency_change_speed.comment"),0,200);
		efficiency_change_speed.setLanguageKey("gui.ncsteamadditions.config.processors.efficiency_change_speed");

		Property override_nc_recipes = config.get(CATEGORY_RECIPES, "override_nc_recipes", "true", Lang.localize("gui.ncsteamadditions.config.recipes.override_nc_recipes.comment"));
		
		override_nc_recipes.setLanguageKey("gui.ncsteamadditions.config.recipes.override_nc_recipes");

		//WORLDGEN
		Property propertyOreDims = config.get(CATEGORY_WORLDGEN, "ore_dims", new int[]{0, 2, -6, -100, 4598, -9999, -11325}, Lang.localize("gui.ncsteamadditions.config.ore_dims.comment"), -2147483648, 2147483647);
		propertyOreDims.setLanguageKey("gui.ncsteamadditions.config.ore_dims");

		Property propertyOreDimsListType = config.get(CATEGORY_WORLDGEN, "ore_dims_list_type", false, Lang.localize("gui.ncsteamadditions.config.ore_dims_list_type.comment"));
		propertyOreDimsListType.setLanguageKey("gui.ncsteamadditions.config.ore_dims_list_type");

		Property propertyOreGen = config.get(CATEGORY_WORLDGEN, "ore_gen", new boolean[]{true}, Lang.localize("gui.ncsteamadditions.config.ore_gen.comment"));
		propertyOreGen.setLanguageKey("gui.ncsteamadditions.config.ore_gen");

		Property propertyOreSize = config.get(CATEGORY_WORLDGEN, "ore_size", new int[]{6}, Lang.localize("gui.ncsteamadditions.config.ore_size.comment"), 1, 2147483647);
		propertyOreSize.setLanguageKey("gui.ncsteamadditions.config.ore_size");

		Property propertyOreRate = config.get(CATEGORY_WORLDGEN, "ore_rate", new int[]{5}, Lang.localize("gui.ncsteamadditions.config.ore_rate.comment"), 1, 2147483647);
		propertyOreRate.setLanguageKey("gui.ncsteamadditions.config.ore_rate");

		Property propertyOreMinHeight = config.get(CATEGORY_WORLDGEN, "ore_min_height", new int[]{0}, Lang.localize("gui.ncsteamadditions.config.ore_min_height.comment"), 1, 255);
		propertyOreMinHeight.setLanguageKey("gui.ncsteamadditions.config.ore_min_height");

		Property propertyOreMaxHeight = config.get(CATEGORY_WORLDGEN, "ore_max_height", new int[]{52}, Lang.localize("gui.ncsteamadditions.config.ore_max_height.comment"), 1, 255);
		propertyOreMaxHeight.setLanguageKey("gui.ncsteamadditions.config.ore_max_height");

		Property propertyOreDrops = config.get(CATEGORY_WORLDGEN, "ore_drops", new boolean[]{false}, Lang.localize("gui.ncsteamadditions.config.ore_drops.comment"));
		propertyOreDrops.setLanguageKey("gui.ncsteamadditions.config.ore_drops");

		Property propertyOreHideDisabled = config.get(CATEGORY_WORLDGEN, "ore_hide_disabled", false, Lang.localize("gui.ncsteamadditions.config.ore_hide_disabled.comment"));
		propertyOreHideDisabled.setLanguageKey("gui.ncsteamadditions.config.ore_hide_disabled");

		Property propertyOreHarvestLevels = config.get(CATEGORY_WORLDGEN, "ore_harvest_levels", new int[]{2}, Lang.localize("gui.ncsteamadditions.config.ore_harvest_levels.comment"), 0, 15);
		propertyOreHarvestLevels.setLanguageKey("gui.ncsteamadditions.config.ore_harvest_levels");

		Property spawnVillager = config.get(CATEGORY_WORLDGEN, "spawn_villager", true, Lang.localize("gui.ncsteamadditions.config.spawn_villager.comment"));
		propertyOreGen.setLanguageKey("gui.ncsteamadditions.config.spawn_villager");

		Property makeHxAlive = config.get(CUSTOM, "make_hx_alive", true, Lang.localize("gui.ncsteamadditions.config.make_hx_alive.comment"));
		makeHxAlive.setLanguageKey("gui.ncsteamadditions.config.make_hx_alive");

		solar_power = solarPower.getIntList();
		makeHXalive = makeHxAlive.getBoolean();
		spawn_villager = spawnVillager.getBoolean();
		ore_dims = propertyOreDims.getIntList();
		ore_dims_list_type = propertyOreDimsListType.getBoolean();
		ore_gen = readBooleanArrayFromConfig(propertyOreGen);
		ore_size = readIntegerArrayFromConfig(propertyOreSize);
		ore_rate = readIntegerArrayFromConfig(propertyOreRate);
		ore_min_height = readIntegerArrayFromConfig(propertyOreMinHeight);
		ore_max_height = readIntegerArrayFromConfig(propertyOreMaxHeight);
		ore_drops = readBooleanArrayFromConfig(propertyOreDrops);
		ore_hide_disabled = propertyOreHideDisabled.getBoolean();
		ore_harvest_levels = readIntegerArrayFromConfig(propertyOreHarvestLevels);
		
		overrideNcRecipes = override_nc_recipes.getBoolean();

		efficiencyChangeSpeed = efficiency_change_speed.getInt();
		efficiencyCap = efficiency_cap.getInt();

		processorsSoundVolume = processors_sound_volume.getDouble();


		digitalTransformerRF = digitalTransformerBasePower.getInt();
		digitalTransformerEfficienctCap = digital_transformer_efficiency_cap.getInt();
		digitalTransformerResetTime = digitalTransformerReset.getInt();

		pipeCapacity = capacity.getInt();
		pipeTransfer = transfer.getInt();

		turbineRF = propertyTurbineRF.getInt();
		turbineConversion = propertyTurbineConversion.getDouble();

		boilerConversion = propertyBoilerConversion.getDouble();

		List<String> propertyOrderProcessors = new ArrayList<String>();
		propertyOrderProcessors.add(propertyProcessorTime.getName());

		config.setCategoryPropertyOrder(CATEGORY_PROCESSORS, propertyOrderProcessors);
		
		if (setFromConfig)
		{
			processor_time = readIntegerArrayFromConfig(propertyProcessorTime);
		}
		propertyProcessorTime.set(processor_time);

		if (config.hasChanged()) config.save();
	}

	private static boolean[] readBooleanArrayFromConfig(Property property) {
		int currentLength = property.getBooleanList().length;
		int defaultLength = property.getDefaults().length;
		if (currentLength == defaultLength) {
			return property.getBooleanList();
		}
		boolean[] newArray = new boolean[defaultLength];
		if (currentLength > defaultLength) {
			for (int i = 0; i < defaultLength; i++) {
				newArray[i] = property.getBooleanList()[i];
			}
		} else {
			for (int i = 0; i < currentLength; i++) {
				newArray[i] = property.getBooleanList()[i];
			}
			for (int i = currentLength; i < defaultLength; i++) {
				newArray[i] = property.setToDefault().getBooleanList()[i];
			}
		}
		return newArray;
	}
	
	private static int[] readIntegerArrayFromConfig(Property property) {
		int currentLength = property.getIntList().length;
		int defaultLength = property.getDefaults().length;
		if (currentLength == defaultLength) {
			return property.getIntList();
		}
		int[] newArray = new int[defaultLength];
		if (currentLength > defaultLength) {
			for (int i = 0; i < defaultLength; i++) {
				newArray[i] = property.getIntList()[i];
			}
		} else {
			for (int i = 0; i < currentLength; i++) {
				newArray[i] = property.getIntList()[i];
			}
			for (int i = currentLength; i < defaultLength; i++) {
				newArray[i] = property.setToDefault().getIntList()[i];
			}
		}
		return newArray;
	}
	
	private static double[] readDoubleArrayFromConfig(Property property) {
		int currentLength = property.getDoubleList().length;
		int defaultLength = property.getDefaults().length;
		if (currentLength == defaultLength) {
			return property.getDoubleList();
		}
		double[] newArray = new double[defaultLength];
		if (currentLength > defaultLength) {
			for (int i = 0; i < defaultLength; i++) {
				newArray[i] = property.getDoubleList()[i];
			}
		} else {
			for (int i = 0; i < currentLength; i++) {
				newArray[i] = property.getDoubleList()[i];
			}
			for (int i = currentLength; i < defaultLength; i++) {
				newArray[i] = property.setToDefault().getDoubleList()[i];
			}
		}
		return newArray;
	}

	private static class ClientConfigEventHandler
	{

		@SubscribeEvent(priority = EventPriority.LOWEST)
		public void onEvent(OnConfigChangedEvent event)
		{
			if (event.getModID().equals(NCSteamAdditions.MOD_ID))
			{
				syncConfig(false, true);
			}
		}
	}
	

}
