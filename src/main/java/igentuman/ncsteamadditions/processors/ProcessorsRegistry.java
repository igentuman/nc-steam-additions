package igentuman.ncsteamadditions.processors;

public class ProcessorsRegistry {

    private static ProcessorsRegistry instance;

    protected AbstractProcessor[] processorList;

    protected int processorsCount = 9;

    public SteamBoiler STEAM_BOILER                     = new SteamBoiler();
    public SteamCrusher STEAM_CRUSHER                   = new SteamCrusher();
    public SteamCompactor STEAM_COMPACTOR               = new SteamCompactor();
    public SteamWasher STEAM_WASHER                     = new SteamWasher();
    public SteamBlender STEAM_BLENDER                   = new SteamBlender();
    public SteamTransformer STEAM_TRANSFORMER           = new SteamTransformer();
    public SteamFluidTransformer STEAM_FLUID_TRANSFORMER = new SteamFluidTransformer();
    public SteamTurbine STEAM_TURBINE                   = new SteamTurbine();
    public DigitalTransformer DIGITAL_TRANSFORMER       = new DigitalTransformer();

    private ProcessorsRegistry()
    {

    }

    public static ProcessorsRegistry get()
    {
        if (instance == null) {
            instance = new ProcessorsRegistry();
        }
        return instance;
    }

    public AbstractProcessor[] processors()
    {
        if(processorList == null || processorList.length == 0) {
            processorList = new AbstractProcessor[processorsCount];

            processorList[STEAM_BOILER.getGuid()]               = STEAM_BOILER;
            processorList[STEAM_CRUSHER.getGuid()]              = STEAM_CRUSHER;
            processorList[STEAM_COMPACTOR.getGuid()]            = STEAM_COMPACTOR;
            processorList[STEAM_WASHER.getGuid()]               = STEAM_WASHER;
            processorList[STEAM_TRANSFORMER.getGuid()]          = STEAM_TRANSFORMER;
            processorList[STEAM_FLUID_TRANSFORMER.getGuid()]    = STEAM_FLUID_TRANSFORMER;
            processorList[STEAM_TURBINE.getGuid()]              = STEAM_TURBINE;
            processorList[DIGITAL_TRANSFORMER.getGuid()]        = DIGITAL_TRANSFORMER;
            processorList[STEAM_BLENDER.getGuid()]              = STEAM_BLENDER;
        }
        return processorList;
    }


}
