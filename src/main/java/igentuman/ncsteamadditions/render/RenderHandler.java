package igentuman.ncsteamadditions.render;

import igentuman.ncsteamadditions.block.Blocks;
import igentuman.ncsteamadditions.item.Items;

public class RenderHandler {
    public static void init()
    {
        Items.registerRenders();
        Blocks.registerRenders();
    }
}
