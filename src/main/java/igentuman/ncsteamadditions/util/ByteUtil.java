package igentuman.ncsteamadditions.util;

import io.netty.buffer.ByteBuf;
import nc.tile.internal.energy.EnergyStorage;
import nc.tile.internal.heat.HeatBuffer;

public class ByteUtil
{

	public static void writeBufHeat(HeatBuffer heatBuffer,ByteBuf buf) 
	{
		buf.writeLong(heatBuffer.getHeatCapacity());	
		buf.writeLong(heatBuffer.getHeatStored());
	}
	
	public static HeatBuffer readBufHeat(ByteBuf buf)
	{
		
		long heatCapacity = buf.readLong();
		long heatStored = buf.readLong();
		HeatBuffer buffer = new HeatBuffer(heatCapacity);
		buffer.setHeatStored(heatStored);
		return buffer;
	}
	
	public static void writeBufEnergy(EnergyStorage energyStorage,ByteBuf buf) 
	{
		
		buf.writeInt(energyStorage.getMaxEnergyStored());
		buf.writeInt(energyStorage.getEnergyStored());
	}
	
	public static EnergyStorage readBufEnergy(ByteBuf buf)
	{
		int  maxEnergy = buf.readInt();
		int energy = buf.readInt();
		EnergyStorage buffer = new EnergyStorage(maxEnergy);
		buffer.setEnergyStored(energy);
		return buffer;
	}
	

	
	
}
