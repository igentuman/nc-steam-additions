package igentuman.ncsteamadditions.processors;

import java.util.List;

public class ProcessorsList {
    public static String[] processors = {
            "SteamTransformer"
    };

    public static AbstractProcessor[] getProcessors()
    {
        AbstractProcessor[] processors = {
                new SteamTransformer()
        };
        return processors;
    }
}
