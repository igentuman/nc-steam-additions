package igentuman.ncsteamadditions.tile;

import gregtech.api.capability.GregtechCapabilities;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import igentuman.ncsteamadditions.processors.ProcessorsRegistry;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import nc.ModCheck;
import nc.config.NCConfig;
import nc.tile.energy.ITileEnergy;
import nc.tile.internal.energy.EnergyConnection;
import nc.tile.internal.energy.EnergyStorage;
import nc.tile.internal.energy.EnergyTileWrapper;
import nc.tile.internal.energy.EnergyTileWrapperGT;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Optional.InterfaceList({@Optional.Interface(
        iface = "ic2.api.energy.tile.IEnergySink",
        modid = "ic2"
), @Optional.Interface(
        iface = "ic2.api.energy.tile.IEnergySource",
        modid = "ic2"
)})
public class TileSteamTurbine extends TileNCSProcessor implements ITileEnergy, IEnergySink, IEnergySource {

    @Nonnull
    private final EnergyStorage storage;
    @Nonnull
    private EnergyConnection[] energyConnections;
    @Nonnull
    private final EnergyTileWrapper[] energySides;
    @Nonnull
    private final EnergyTileWrapperGT[] energySidesGT;
    private boolean ic2reg;
    private int maxRf = NCSteamAdditionsConfig.turbineRF;

    public TileSteamTurbine()
    {
        super(
                ProcessorsRegistry.get().STEAM_TURBINE.code,
                ProcessorsRegistry.get().STEAM_TURBINE.inputItems,
                ProcessorsRegistry.get().STEAM_TURBINE.inputFluids,
                ProcessorsRegistry.get().STEAM_TURBINE.outputItems,
                ProcessorsRegistry.get().STEAM_TURBINE.outputFluids,
                defaultItemSorptions(ProcessorsRegistry.get().STEAM_TURBINE.inputItems, ProcessorsRegistry.get().STEAM_TURBINE.outputItems, true),
                defaultTankCapacities(5000, ProcessorsRegistry.get().STEAM_TURBINE.inputFluids, ProcessorsRegistry.get().STEAM_TURBINE.outputFluids),
                defaultTankSorptions(ProcessorsRegistry.get().STEAM_TURBINE.inputFluids, ProcessorsRegistry.get().STEAM_TURBINE.outputFluids),
                NCSteamAdditionsRecipes.validFluids[ProcessorsRegistry.get().STEAM_TURBINE.GUID],
                NCSteamAdditionsConfig.processor_time[ProcessorsRegistry.get().STEAM_TURBINE.GUID],
                0, true, false,
                NCSteamAdditionsRecipes.processorRecipeHandlers[ProcessorsRegistry.get().STEAM_TURBINE.GUID],
                ProcessorsRegistry.get().STEAM_TURBINE.GUID+1, 0,0,0,10
        );
        this.ic2reg = false;
        this.storage = new EnergyStorage(1024, maxRf);
        this.setEnergyConnectionAll(EnergyConnection.OUT);
        this.energySides = ITileEnergy.getDefaultEnergySides(this);
        this.energySidesGT = ITileEnergy.getDefaultEnergySidesGT(this);
    }
    public void onLoad() {
        super.onLoad();
        if (ModCheck.ic2Loaded()) {
            this.addTileToENet();
        }
    }

    public ItemStack getStackInSlot(int slot) {
        return ItemStack.EMPTY;
    }

    public boolean hasUpgrades()
    {
        return false;
    }

    public void update() {
        super.update();
        if (!getWorld().isRemote) {
            if (this.isProcessing) {
                int stored = this.storage.getEnergyStored();
                this.storage.setEnergyStored(stored + maxRf);
            }
            for (EnumFacing facing : EnumFacing.VALUES) {
                BlockPos pos = getPos().offset(facing);
                TileEntity te = getWorld().getTileEntity(pos);
                EnumFacing opposite = facing.getOpposite();
                if(te == null) return;
                IEnergyStorage cap = te.getCapability(CapabilityEnergy.ENERGY, opposite);
                if (cap != null && cap.canReceive()) {
                    storage.extractEnergy(maxRf, false);
                }
            }
        }
    }

    public void process() {
        if(currentReactivity < this.targetReactivity && this.getRecipeEfficiency() <= 100) {
            this.currentReactivity += (this.targetReactivity - this.currentReactivity) * (float)NCSteamAdditionsConfig.efficiencyChangeSpeed/50000;
        }
        float efficiency = 1;
        if(NCSteamAdditionsConfig.efficiencyCap > 0) {
            efficiency = Math.min(this.getRecipeEfficiency() / 100, (float) NCSteamAdditionsConfig.efficiencyCap / 100);
        }
        this.time += this.getSpeedMultiplier() * efficiency;
        this.getEnergyStorage().changeEnergyStored((long)(-this.getProcessPower()));

        while(this.time >= this.baseProcessTime) {
            this.finishProcess();
        }
    }

    public void invalidate() {
        super.invalidate();
        if (ModCheck.ic2Loaded()) {
            this.removeTileFromENet();
        }
    }

    public void onChunkUnload() {
        super.onChunkUnload();
        if (ModCheck.ic2Loaded()) {
            this.removeTileFromENet();
        }
    }

    public EnergyStorage getEnergyStorage() {
        return this.storage;
    }

    public EnergyConnection[] getEnergyConnections() {
        return this.energyConnections;
    }

    @Nonnull
    public EnergyTileWrapper[] getEnergySides() {
        return this.energySides;
    }

    @Nonnull
    public EnergyTileWrapperGT[] getEnergySidesGT() {
        return this.energySidesGT;
    }

    public boolean getIC2Reg() {
        return this.ic2reg;
    }

    public void setIC2Reg(boolean ic2reg) {
        this.ic2reg = ic2reg;
    }

    @Optional.Method(
            modid = "ic2"
    )
    public boolean acceptsEnergyFrom(IEnergyEmitter emitter, EnumFacing side) {
        return false;
    }

    @Optional.Method(
            modid = "ic2"
    )
    public double getDemandedEnergy() {
        return super.getDemandedEnergy();
    }

    @Optional.Method(
            modid = "ic2"
    )
    public double injectEnergy(EnumFacing directionFrom, double amount, double voltage) {
        return super.injectEnergy(directionFrom, amount, voltage);
    }

    @Optional.Method(
            modid = "ic2"
    )
    public boolean emitsEnergyTo(IEnergyAcceptor receiver, EnumFacing side) {
        return super.emitsEnergyTo(receiver, side);
    }

    @Optional.Method(
            modid = "ic2"
    )
    public double getOfferedEnergy() {
        return super.getOfferedEnergy();
    }

    @Optional.Method(
            modid = "ic2"
    )
    public void drawEnergy(double amount) {
        super.drawEnergy(amount);
    }

    public NBTTagCompound writeAll(NBTTagCompound nbt) {
        super.writeAll(nbt);
        this.writeEnergy(nbt);
        this.writeEnergyConnections(nbt);
        return nbt;
    }

    public void readAll(NBTTagCompound nbt) {
        super.readAll(nbt);
        this.readEnergy(nbt);
        this.readEnergyConnections(nbt);
    }

    public void setEnergyConnectionAll(EnergyConnection energyConnection) {
        this.energyConnections = ITileEnergy.energyConnectionAll(energyConnection);
    }

    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing side) {
        if(capability == CapabilityEnergy.ENERGY) {
            return true;
        }
        return capability != CapabilityEnergy.ENERGY && (!ModCheck.gregtechLoaded() || !NCConfig.enable_gtce_eu || capability != GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER) ? super.hasCapability(capability, side) : this.hasEnergySideCapability(side);
    }

    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing side) {
        if (capability == CapabilityEnergy.ENERGY) {
            return  (T) this.getEnergyStorage();
        } else if (ModCheck.gregtechLoaded() && capability == GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER) {
            return NCConfig.enable_gtce_eu && this.hasEnergySideCapability(side) ? (T) this.getEnergySideGT(this.nonNullSide(side)) : null;
        } else {
            return super.getCapability(capability, side);
        }
    }
}
