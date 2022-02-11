package igentuman.ncsteamadditions.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import igentuman.ncsteamadditions.NCSteamAdditions;
import nc.util.Lang;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NCSteamAdditionsConfig {

	private static Configuration config = null;
	public static int[] processor_time;
	public static final String CATEGORY_PROCESSORS = "processors";
	public static final String CATEGORY_PIPES = "pipes";
	public static Configuration getConfig()
	{
		return config;
	}
	public static int pipeCapacity;
	public static int pipeTransfer;
	public static int turbineRF;
	public static int efficiencyCap;
	public static int efficiencyChangeSpeed;
	public static int digitalTransformerRF;
	public static double turbineConversion;
	public static double boilerConversion;

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

		Property propertyProcessorTime = config.get(CATEGORY_PROCESSORS, "time", new int[] {800,400,400,800,400,400,500,400,12000}, Lang.localise("gui.ncsteamadditions.config.processors.time.comment"), 0, 32767);
		propertyProcessorTime.setLanguageKey("gui.ncsteamadditions.config.processors.time");

		Property propertyBoilerConversion = config.get(CATEGORY_PROCESSORS, "boiler_conversion", 1.25F, Lang.localise("gui.ncsteamadditions.config.processors.boiler_conversion.comment"),0,32767);
		propertyBoilerConversion.setLanguageKey("gui.ncsteamadditions.config.processors.boiler_conversion");

		Property propertyTurbineRF = config.get(CATEGORY_PROCESSORS, "turbine_rf", 12, Lang.localise("gui.ncsteamadditions.config.processors.turbine_rf.comment"),0,32767);
		propertyTurbineRF.setLanguageKey("gui.ncsteamadditions.config.processors.turbine_rf");

		Property propertyTurbineConversion = config.get(CATEGORY_PROCESSORS, "turbine_conversion", 0.50, Lang.localise("gui.ncsteamadditions.config.processors.turbine_conversion.comment"),0,32767);
		propertyTurbineRF.setLanguageKey("gui.ncsteamadditions.config.processors.turbine_conversion");

		Property capacity = config.get(CATEGORY_PIPES, "capacity", 5000, Lang.localise("gui.ncsteamadditions.config.pipes.capacity.comment"),0,32767);
		capacity.setLanguageKey("gui.ncsteamadditions.config.pipes.capacity");

		Property transfer = config.get(CATEGORY_PIPES, "transfer", 1000, Lang.localise("gui.ncsteamadditions.config.pipes.transfer.comment"),0,32767);
		transfer.setLanguageKey("gui.ncsteamadditions.config.pipes.transfer");

		Property digitalTransformerBasePower = config.get(CATEGORY_PROCESSORS, "digital_transformer_rf", 1024, Lang.localise("gui.ncsteamadditions.config.processors.digital_transformer_rf.comment"),0,32767);
		propertyTurbineRF.setLanguageKey("gui.ncsteamadditions.config.processors.digital_transformer_rf");

		Property efficiency_cap = config.get(CATEGORY_PROCESSORS, "efficiency_cap", 150, Lang.localise("gui.ncsteamadditions.config.processors.efficiency_cap.comment"),0,5000);
		propertyTurbineRF.setLanguageKey("gui.ncsteamadditions.config.processors.efficiency_cap");

		Property efficiency_change_speed = config.get(CATEGORY_PROCESSORS, "efficiency_change_speed", 100, Lang.localise("gui.ncsteamadditions.config.processors.efficiency_change_speed.comment"),0,200);
		propertyTurbineRF.setLanguageKey("gui.ncsteamadditions.config.processors.efficiency_change_speed");

		efficiencyChangeSpeed = efficiency_change_speed.getInt();
		efficiencyCap = efficiency_cap.getInt();


		digitalTransformerRF = digitalTransformerBasePower.getInt();

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
