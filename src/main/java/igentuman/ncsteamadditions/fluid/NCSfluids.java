package igentuman.ncsteamadditions.fluid;

import igentuman.ncsteamadditions.NCSteamAdditions;
import nc.block.item.NCItemBlock;
import nc.util.ColorHelper;
import nc.util.NCUtil;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;
import nc.block.fluid.NCBlockFluid;
import nc.enumm.FluidType;
import net.minecraftforge.fluids.Fluid;

import java.util.ArrayList;
import java.util.List;

public class NCSfluids {
    public static List<Pair<Fluid, NCBlockFluid>> fluidPairList = new ArrayList<Pair<Fluid, NCBlockFluid>>();


    public static void init()
    {
        try
        {
            //gases
            addFluidPair(FluidType.GAS, "uranium_oxide", 0xedd17e);
            addFluidPair(FluidType.GAS, "uranium_hexafluoride", 0xaea385);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static <T extends Fluid, V extends NCBlockFluid> void addFluidPair(FluidType fluidType, Object... fluidArgs)
    {
        try
        {
            T fluid = NCUtil.newInstance(fluidType.getFluidClass(), fluidArgs);
            V block = NCUtil.newInstance(fluidType.getBlockClass(), fluid);
            fluidPairList.add(Pair.of(fluid, block));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }




    public static void register()
    {
        for (Pair<Fluid, NCBlockFluid> fluidPair : fluidPairList)
        {
            registerBlock(fluidPair.getRight());
        }
    }

    public static void registerBlock(NCBlockFluid block)
    {
        ForgeRegistries.BLOCKS.register(withName(block));
        ForgeRegistries.ITEMS.register(new NCItemBlock(block, TextFormatting.AQUA).setRegistryName(block.getRegistryName()));
        NCSteamAdditions.proxy.registerFluidBlockRendering(block, block.name);
    }

    public static <T extends NCBlockFluid> Block withName(T block)
    {
        return block.setUnlocalizedName(NCSteamAdditions.MOD_ID + "." + block.name).setRegistryName(new ResourceLocation(NCSteamAdditions.MOD_ID, block.name));
    }


    private static int waterBlend(int soluteColor, float blendRatio)
    {
        return ColorHelper.blend(0x2F43F4, soluteColor, blendRatio);
    }

    private static int waterBlend(int soluteColor)
    {
        return waterBlend(soluteColor, 0.5F);
    }
}
