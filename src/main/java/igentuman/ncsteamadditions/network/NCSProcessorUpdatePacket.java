package igentuman.ncsteamadditions.network;

import io.netty.buffer.ByteBuf;
import nc.network.tile.ProcessorUpdatePacket;
import nc.network.tile.TileUpdatePacket;
import nc.tile.ITileGui;
import nc.tile.internal.fluid.Tank;
import net.minecraft.util.math.BlockPos;

import java.util.Iterator;
import java.util.List;

public class NCSProcessorUpdatePacket extends TileUpdatePacket {
    public boolean isProcessing;
    public double time;
    public int energyStored;
    public double baseProcessTime;
    public double baseProcessPower;
    public List<Tank.TankInfo> tanksInfo;
    public float currentReactivity;
    public float targetReactivity;

    public NCSProcessorUpdatePacket() {
    }

    public NCSProcessorUpdatePacket(BlockPos pos, boolean isProcessing, double time, int energyStored, double baseProcessTime, double baseProcessPower, List<Tank> tanks, float currentReactivity, float targetReactivity) {
        this.pos = pos;
        this.isProcessing = isProcessing;
        this.time = time;
        this.energyStored = energyStored;
        this.baseProcessTime = baseProcessTime;
        this.baseProcessPower = baseProcessPower;
        this.tanksInfo = Tank.TankInfo.infoList(tanks);
        this.currentReactivity = currentReactivity;
        this.targetReactivity = targetReactivity;
    }

    public void fromBytes(ByteBuf buf) {
        this.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        this.isProcessing = buf.readBoolean();
        this.time = buf.readDouble();
        this.energyStored = buf.readInt();
        this.baseProcessTime = buf.readDouble();
        this.baseProcessPower = buf.readDouble();
        byte numberOfTanks = buf.readByte();
        this.tanksInfo = Tank.TankInfo.readBuf(buf, numberOfTanks);
        this.currentReactivity = buf.readFloat();
        this.targetReactivity = buf.readFloat();
    }

    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.pos.getX());
        buf.writeInt(this.pos.getY());
        buf.writeInt(this.pos.getZ());
        buf.writeBoolean(this.isProcessing);
        buf.writeDouble(this.time);
        buf.writeInt(this.energyStored);
        buf.writeDouble(this.baseProcessTime);
        buf.writeDouble(this.baseProcessPower);
        buf.writeByte(this.tanksInfo.size());
        Iterator var2 = this.tanksInfo.iterator();

        while(var2.hasNext()) {
            Tank.TankInfo info = (Tank.TankInfo)var2.next();
            info.writeBuf(buf);
        }
        buf.writeFloat(this.currentReactivity);
        buf.writeFloat(this.targetReactivity);
    }

    public static class Handler extends nc.network.tile.TileUpdatePacket.Handler<NCSProcessorUpdatePacket, ITileGui> {
        public Handler() {
        }

        protected void onPacket(NCSProcessorUpdatePacket message, ITileGui processor) {
            processor.onGuiPacket(message);
        }
    }
}
