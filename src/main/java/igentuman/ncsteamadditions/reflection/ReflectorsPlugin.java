package igentuman.ncsteamadditions.reflection;

import com.github.mjaroslav.reflectors.v4.Reflectors;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.SortingIndex(1001)
@IFMLLoadingPlugin.Name("ReflectorsPlugin")
public class ReflectorsPlugin extends Reflectors.FMLLoadingPluginAdapter
        implements IFMLLoadingPlugin, IClassTransformer {
    public ReflectorsPlugin() {
        Reflectors.enabledLogs = true;
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{getClass().getName()};
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {

        if (transformedName.equals("nc.multiblock.heatExchanger.tile.TileHeatExchangerTube")) {
            return Reflectors.reflectClass(basicClass, transformedName, TileHeatExchangerTubeReflection.class.getName());
        }

        if (transformedName.equals("nc.multiblock.heatExchanger.tile.TileCondenserTube")) {
            return Reflectors.reflectClass(basicClass, transformedName, TileCondenserTubeReflection.class.getName());
        }

        if (transformedName.equals("nc.multiblock.heatExchanger.HeatExchangerLogic")) {
            return Reflectors.reflectClass(basicClass, transformedName, HeatExchangerLogicReflection.class.getName());
        }

        if (transformedName.equals("nc.multiblock.heatExchanger.CondenserLogic")) {
            return Reflectors.reflectClass(basicClass, transformedName, CondenserLogicReflection.class.getName());
        }

        return basicClass;
    }
}