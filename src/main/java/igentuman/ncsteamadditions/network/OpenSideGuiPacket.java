package igentuman.ncsteamadditions.network;

import igentuman.ncsteamadditions.NCSteamAdditions;
import igentuman.ncsteamadditions.tile.TileNCSProcessor;
import io.netty.buffer.ByteBuf;
import nc.tile.ITileGui;
import nclegacy.tile.ITileGuiLegacy;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraftforge.fml.relauncher.Side;

public class OpenSideGuiPacket implements IMessage {
    private BlockPos pos;
    boolean messageValid;
    public OpenSideGuiPacket() {
        messageValid = false;
    }

    public OpenSideGuiPacket(ITileGuiLegacy machine) {
        this.pos = machine.getTilePos();
        messageValid = true;
    }

    public void fromBytes(ByteBuf buf) {
        try
        {
            pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        }
        catch (IndexOutOfBoundsException e)
        {
            e.printStackTrace();
            return;
        }
        messageValid = true;
    }

    public void toBytes(ByteBuf buf) {
        if (!messageValid)
            return;
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
    }
    public static class Handler implements IMessageHandler<OpenSideGuiPacket, IMessage> {
        public Handler() {
        }

        public IMessage onMessage(OpenSideGuiPacket message, MessageContext ctx) {
            if (!message.messageValid && ctx.side != Side.SERVER)
                return null;
                FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> {
                    this.processMessage(message, ctx);
                });
            return null;
        }

        void processMessage(OpenSideGuiPacket message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            World world = player.getServerWorld();
            if (world.isBlockLoaded(message.pos) && world.isBlockModifiable(player, message.pos)) {
                TileEntity tile = world.getTileEntity(message.pos);
                if (tile instanceof TileNCSProcessor) {
                    FMLNetworkHandler.openGui(player, NCSteamAdditions.instance, ((TileNCSProcessor)tile).getGuiID() + 1000, player.getServerWorld(), message.pos.getX(), message.pos.getY(), message.pos.getZ());
                    ((TileNCSProcessor)tile).addTileUpdatePacketListener(player);
                }
            }
        }
    }
}
