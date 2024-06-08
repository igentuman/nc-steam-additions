package igentuman.ncsteamadditions.util;

import igentuman.ncsteamadditions.NCSteamAdditions;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.*;

import java.util.Random;

public class Util
{
	public static void spawnParticleOnProcessor(IBlockState state, World world, BlockPos pos, Random rand, EnumFacing side, String particleName) {
		if (!particleName.equals("")) {
			double d0 = (double)pos.getX() + 0.5D;
			double d1 = (double)pos.getY() + 0.125D + rand.nextDouble() * 0.75D;
			double d2 = (double)pos.getZ() + 0.5D;

			spawnParticleOnProcessorByPos(state, world, pos, rand, side, particleName, d0, d1, d2);
		}
	}

	public static void spawnParticleOnProcessorByPos(IBlockState state, World world, BlockPos pos, Random rand, EnumFacing side, String particleName, double x, double y, double z) {
		if (!particleName.equals("")) {

			double d3 = 0.52D;
			double d4 = rand.nextDouble() * 0.6D - 0.3D;
			switch(side) {
				case WEST:
					world.spawnParticle(EnumParticleTypes.getByName(particleName), x - d3, y, z + d4, 0.0D, 0.0D, 0.0D, new int[0]);
					break;
				case EAST:
					world.spawnParticle(EnumParticleTypes.getByName(particleName), x + d3, y, z + d4, 0.0D, 0.0D, 0.0D, new int[0]);
					break;
				case NORTH:
					world.spawnParticle(EnumParticleTypes.getByName(particleName), x + d4, y, z - d3, 0.0D, 0.0D, 0.0D, new int[0]);
					break;
				case SOUTH:
					world.spawnParticle(EnumParticleTypes.getByName(particleName), x + d4, y, z + d3, 0.0D, 0.0D, 0.0D, new int[0]);
			}

		}
	}
	private static Logger logger;
	
	public static Logger getLogger()
	{
		if (logger == null)
		{
			logger = LogManager.getFormatterLogger(NCSteamAdditions.MOD_ID);
		}
		return logger;
	}
	
	public static ResourceLocation appendPath(ResourceLocation location, String  stringToAppend)
	{
		String domain =location.getNamespace();
		String path =location.getPath();
		ResourceLocation newLocation = new ResourceLocation(domain,path+stringToAppend);
		
		return newLocation;
	}

	public static EnumFacing getAxisFacing(EnumFacing.Axis axis, boolean positive)
	{
		if(axis == EnumFacing.Axis.X)
		{
			if(positive)
			{
				return EnumFacing.EAST;
			}
			return EnumFacing.WEST;
		}
		if(axis == EnumFacing.Axis.Y)
		{
			if(positive)
			{
				return EnumFacing.UP;
			}
			return EnumFacing.DOWN;
		}
		if(axis == EnumFacing.Axis.Z)
		{
			if(positive)
			{
				return EnumFacing.SOUTH;
			}
			return EnumFacing.NORTH;
		}
	
		return null;
	}

	
	public static int getTaxiDistance(BlockPos a, BlockPos b)
	{
		int x = Math.abs(a.getX()- b.getX());
		int y = Math.abs(a.getY()- b.getY());
		int z = Math.abs(a.getZ()- b.getZ());
		return x+y+z;
	}
	
	
	
	
	
}
