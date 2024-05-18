package igentuman.ncsteamadditions.tile;

import igentuman.ncsteamadditions.block.BlockPipe;
import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.*;

import javax.annotation.Nullable;
import java.util.*;


public class TilePipe extends PersistantSyncableTileEntity implements ITickable, IFluidHandler {

    private Fluid fluid;
    private int amount;
    private boolean extraction;
    private int transferRate = NCSteamAdditionsConfig.pipeTransfer;
    private ItemStack cachedBlockPipe;
    private int ticksToRefresh = 1000;

    @Override
    public void update() {

        for (EnumFacing facing : EnumFacing.values()) {
            BlockPos pos = this.getPos().offset(facing);
            TileEntity tileEntity = this.getWorld().getTileEntity(pos);
            if(ticksToRefresh <= 0) {
                clearConnectionCache();
                ticksToRefresh = 1000;
            }
            ticksToRefresh--;
            if (!this.canConnectTo(tileEntity, facing, false)) {
                continue;
            }

            IFluidHandler fluidHandler = tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite());
            if (fluidHandler == null) {
                continue;
            }

            boolean isPipe = tileEntity instanceof TilePipe;

            if (this.extraction && !isPipe) {

                int freeSpace = NCSteamAdditionsConfig.pipeCapacity - this.amount;
                if (freeSpace <= 0) {
                    continue;
                }

                FluidStack drainableFluid = this.fluid == null
                        ? fluidHandler.drain(Math.min(transferRate, freeSpace), true)
                        : fluidHandler.drain(new FluidStack(this.fluid, Math.min(transferRate, freeSpace)), true);

                if (drainableFluid == null || drainableFluid.amount <= 0) {
                    continue;
                }

                this.amount += drainableFluid.amount;

                if (this.fluid == null) {
                    this.fluid = drainableFluid.getFluid();
                }
            } else {
                if (this.fluid == null || this.amount <= 0) {
                    continue;
                }

                if (isPipe && ((TilePipe) tileEntity).amount > this.amount) {
                    continue;
                }

                this.amount -= fluidHandler.fill(new FluidStack(this.fluid, Math.min(transferRate, this.amount)), true);
            }
        }
    }
    protected Map<String, Boolean> connectionsCache = new HashMap<>();
    public void clearConnectionCache()
    {
        connectionsCache = new HashMap<>();
    }

    private FluidStack getFluidStack() {
        if (this.amount <= 0) {
            return null;
        }

        return new FluidStack(this.fluid, this.amount);
    }

    public boolean isExtractionEnabled() {
        return this.extraction;
    }

    public void setExtractionEnabled(boolean extraction) {
        this.extraction = extraction;
        this.triggerUpdate();
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return (T) this;
        }

        return null;
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return new IFluidTankProperties[] { new FluidTankProperties(this.getFluidStack(), NCSteamAdditionsConfig.pipeCapacity) };
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if (resource == null || resource.amount <= 0) {
            return 0;
        }

        FluidStack current = this.getFluidStack();

        int maxFill = current == null
                ? Math.min(transferRate, resource.amount)
                : current.isFluidEqual(resource) ? Math.min(transferRate - current.amount, resource.amount) : 0;

        if (!doFill) {
            return maxFill;
        }

        if (current == null) {
            this.fluid = resource.getFluid();
            this.amount = resource.amount;
            this.triggerUpdate();
            return resource.amount;
        }

        if (current.isFluidEqual(resource)) {
            this.amount += maxFill;
            this.triggerUpdate();
            return maxFill;
        }

        return 0;
    }

    @Nullable
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        if (maxDrain <= 0) {
            return null;
        }

        FluidStack current = this.getFluidStack();
        if (current == null) {
            return null;
        }

        return this.drain(new FluidStack(current.getFluid(), maxDrain), doDrain);
    }

    @Nullable
    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        if (resource == null || resource.amount <= 0) {
            return null;
        }

        FluidStack current = this.getFluidStack();
        if (current == null) {
            return null;
        }

        int maxDrain = Math.min(10, Math.min(current.amount, resource.amount));

        if (!doDrain) {
            return new FluidStack(current.getFluid(), maxDrain);
        }

        this.amount -= maxDrain;
        this.triggerUpdate();
        return new FluidStack(current.getFluid(), maxDrain);
    }

    @Override
    protected void writeData(NBTTagCompound tagCompound) {
        FluidStack current = this.getFluidStack();
        if (current != null) {
            tagCompound.setString("FluidName", FluidRegistry.getFluidName(current.getFluid()));
            tagCompound.setInteger("FluidAmount", current.amount);
        }

        tagCompound.setBoolean("CanExtract", this.extraction);
    }

    @Override
    protected void readData(NBTTagCompound tagCompound) {
        if (tagCompound.hasKey("FluidName") && tagCompound.hasKey("FluidAmount")) {
            String fluidName = tagCompound.getString("FluidName");
            int fluidAmount = tagCompound.getInteger("FluidAmount");

            Fluid fluid = FluidRegistry.getFluid(fluidName);
            if (fluid == null) {
                return;
            }

            this.fluid = fluid;
            this.amount = fluidAmount;
        }

        this.extraction = tagCompound.getBoolean("CanExtract");
    }

    public ItemStack getBlockPipe() {
        if (this.cachedBlockPipe == null) {
            if (!this.getTileData().hasKey("BlockPipe")) {
                return new ItemStack(BlockPipe.PIPE);
            }

            NBTTagCompound baseBlock = this.getTileData().getCompoundTag("BlockPipe");
            this.cachedBlockPipe = new ItemStack(baseBlock);
        }

        return this.cachedBlockPipe;
    }

    public boolean canConnectTo(EnumFacing direction, boolean excludePipe) {
        return this.canConnectTo(this.getWorld().getTileEntity(this.getPos().offset(direction)), direction, excludePipe);
    }

    public boolean canConnectTo(TileEntity tileEntity, EnumFacing direction, boolean excludePipe) {
        if (tileEntity == null) {
            return false;
        }
        BlockPos pos = tileEntity.getPos();
        String cacheKey  = pos.getX() + "_" + pos.getY() + "_" + pos.getZ() +"_"+ direction.getIndex() +"_"+ excludePipe;
        if( !connectionsCache.containsKey(cacheKey)) {


            if (tileEntity instanceof TilePipe) {
                if (excludePipe) {
                    return false;
                }

                TilePipe otherPipe = (TilePipe) tileEntity;

                return otherPipe.getBlockPipe().isItemEqual(this.getBlockPipe());
            }

            connectionsCache.put(cacheKey,tileEntity.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction.getOpposite()));
        }
        return connectionsCache.get(cacheKey);
    }

}
