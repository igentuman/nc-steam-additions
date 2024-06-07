package igentuman.ncsteamadditions.network;

import io.netty.buffer.ByteBuf;
import nc.network.tile.TileUpdatePacket;
import nc.network.tile.processor.*;
import nc.tile.*;
import nc.tile.internal.fluid.Tank;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

import java.util.*;

public class NCSProcessorUpdatePacket extends EnergyProcessorUpdatePacket {
    
    public float currentReactivity;
    public float targetReactivity;
    public int adjustmentAttempts;

    public NCSProcessorUpdatePacket() {
        super();
    }

    public NCSProcessorUpdatePacket(BlockPos pos, boolean isProcessing, double time, double baseProcessTime, List<Tank> tanks, double baseProcessPower, long energyStored, float currentReactivity, float targetReactivity, int adjustmentAttempts) {
        super(pos, isProcessing, time, baseProcessTime, tanks, baseProcessPower, energyStored);
        this.currentReactivity = currentReactivity;
        this.targetReactivity = targetReactivity;
        this.adjustmentAttempts = adjustmentAttempts;

    }
    
    @Override
    public SimpleNetworkWrapper getWrapper() {
        return NCSAPacketHandler.instance;
    }
    
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
        this.currentReactivity = buf.readFloat();
        this.targetReactivity = buf.readFloat();
        this.adjustmentAttempts = buf.readInt();
    }

    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
        buf.writeFloat(this.currentReactivity);
        buf.writeFloat(this.targetReactivity);
        buf.writeInt(this.adjustmentAttempts);
    }
    
    public static class Handler extends ProcessorUpdatePacket.Handler<NCSProcessorUpdatePacket, ITilePacket<NCSProcessorUpdatePacket>> {}
}
