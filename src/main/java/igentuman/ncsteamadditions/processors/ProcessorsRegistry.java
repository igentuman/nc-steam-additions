package igentuman.ncsteamadditions.processors;

public class ProcessorsRegistry {

    private static ProcessorsRegistry instance;

    protected AbstractProcessor[] processorList;

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
        if(processorList.length == 0) {
            processorList = new AbstractProcessor[]{
                    new SteamTransformer()
            };
        }
        return processorList;
    }
}
