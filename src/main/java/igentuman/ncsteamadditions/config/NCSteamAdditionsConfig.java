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
	public static int[] processor_power;
	public static int[] processor_time;

	public static final String CATEGORY_PROCESSORS = "processors";
	
	
	public static Configuration getConfig()
	{
		return config;
	}

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

		Property propertyProcessorPower = config.get(CATEGORY_PROCESSORS, "power", new int[] {50,100}, Lang.localise("gui.ncsteamadditions.config.processors.power.comment"), 0, 32767);
		propertyProcessorPower.setLanguageKey("gui.ncsteamadditions.config.processors.power");
		
		Property propertyProcessorTime = config.get(CATEGORY_PROCESSORS, "time", new int[] {400,200}, Lang.localise("gui.ncsteamadditions.config.processors.time.comment"), 0, 32767);
		propertyProcessorTime.setLanguageKey("gui.ncsteamadditions.config.processors.time");
		

		List<String> propertyOrderProcessors = new ArrayList<String>();
		propertyOrderProcessors.add(propertyProcessorPower.getName());
		propertyOrderProcessors.add(propertyProcessorTime.getName());

		config.setCategoryPropertyOrder(CATEGORY_PROCESSORS, propertyOrderProcessors);
		
		

		
		
		if (setFromConfig) 
		{
			processor_power = readIntegerArrayFromConfig(propertyProcessorPower);
			processor_time = readIntegerArrayFromConfig(propertyProcessorTime);

		}
		propertyProcessorPower.set(processor_power);
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
