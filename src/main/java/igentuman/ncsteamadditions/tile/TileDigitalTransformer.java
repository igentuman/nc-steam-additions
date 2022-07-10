package igentuman.ncsteamadditions.tile;

import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import igentuman.ncsteamadditions.processors.ProcessorsRegistry;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import nc.ModCheck;
import net.minecraftforge.fml.common.Optional;

import java.util.Random;

@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "opencomputers")
public class TileDigitalTransformer extends TileNCSProcessor implements SimpleComponent
{
    public TileDigitalTransformer()
    {
        super(
                ProcessorsRegistry.get().DIGITAL_TRANSFORMER.code,
                ProcessorsRegistry.get().DIGITAL_TRANSFORMER.inputItems,
                ProcessorsRegistry.get().DIGITAL_TRANSFORMER.inputFluids,
                ProcessorsRegistry.get().DIGITAL_TRANSFORMER.outputItems,
                ProcessorsRegistry.get().DIGITAL_TRANSFORMER.outputFluids,
                defaultItemSorptions(ProcessorsRegistry.get().DIGITAL_TRANSFORMER.inputItems, ProcessorsRegistry.get().DIGITAL_TRANSFORMER.outputItems, true),
                defaultTankCapacities(40000, ProcessorsRegistry.get().DIGITAL_TRANSFORMER.inputFluids, ProcessorsRegistry.get().DIGITAL_TRANSFORMER.outputFluids),
                defaultTankSorptions(ProcessorsRegistry.get().DIGITAL_TRANSFORMER.inputFluids, ProcessorsRegistry.get().DIGITAL_TRANSFORMER.outputFluids),
                NCSteamAdditionsRecipes.validFluids[ProcessorsRegistry.get().DIGITAL_TRANSFORMER.GUID],
                NCSteamAdditionsConfig.processor_time[ProcessorsRegistry.get().DIGITAL_TRANSFORMER.GUID],
                NCSteamAdditionsConfig.digitalTransformerRF, true, true,
                NCSteamAdditionsRecipes.processorRecipeHandlers[ProcessorsRegistry.get().DIGITAL_TRANSFORMER.GUID],
                ProcessorsRegistry.get().DIGITAL_TRANSFORMER.GUID+1, 0,0,0,0
        );
        this.getEnergyStorage().setMaxTransfer(2048000);
        this.getEnergyStorage().setStorageCapacity(2048000);
    }

    public boolean setRecipeStats() {
        boolean result = super.setRecipeStats();
        if(this.ticksLastReactivityInit <= 0) {
            this.initReactivity();
        }
        return result;
    }

    public void update() {

        if (!this.world.isRemote) {
            boolean wasProcessing = this.isProcessing;
            this.isProcessing = this.isProcessing();
            boolean shouldUpdate = false;
            if (this.isProcessing) {
                this.process();
            } else {
                this.getRadiationSource().setRadiationLevel(0.0D);
                if (this.time > 0.0D && !this.isHaltedByRedstone() && (this.shouldLoseProgress || !this.canProcessInputs)) {
                    this.loseProgress();
                }
            }

            if (wasProcessing != this.isProcessing) {
                shouldUpdate = true;
                this.setActivity(this.isProcessing);
                this.sendTileUpdatePacketToAll();
            }

            this.sendTileUpdatePacketToListeners();
            if (shouldUpdate) {
                this.markDirty();
            }
        }

    }

    public int getEfficiencyCap()
    {
        return (int)Math.min(2300,NCSteamAdditionsConfig.digitalTransformerEfficienctCap);
    }

    public void process() {
        if(this.currentReactivity == 0 && this.targetReactivity == 0) {
            this.initReactivity();
        }
        this.tickReactivity();
        this.time += this.getSpeedMultiplier() * getRecipeEfficiency()/50;
        this.getEnergyStorage().changeEnergyStored((long)(-this.getProcessPower()));
        this.getRadiationSource().setRadiationLevel(this.baseProcessRadiation * this.getSpeedMultiplier());

        while(this.time >= this.baseProcessTime) {
            this.finishProcess();
        }
    }

    public boolean hasUpgrades()
    {
        return false;
    }

    @Optional.Method(modid = "opencomputers")
    public String getComponentName()
    {
        return ProcessorsRegistry.get().DIGITAL_TRANSFORMER.code;
    }

    protected int baseTicksToChange = 200;
    public void adjust(float step)
    {
        if(this.adjustmentAttempts > this.adjustmentsLimit) {
            return;
        }
        this.adjustmentAttempts++;
        this.adjustment = step;
        this.ticksToChange = this.baseTicksToChange;
    }

    //each time machine gets a new recipe it initialize parameters
    public void initReactivity()
    {
        if(world.isRemote) return;
        adjustmentAttempts = 0;
        float low = 0F;
        float high = 2.0F;
        this.targetReactivity = (low + new Random().nextFloat() * (high - low))*10;
        low = 0F;
        high = 1.5F;
        this.currentReactivity = (low + new Random().nextFloat() * (high - low))*10;
        ticksLastReactivityInit = NCSteamAdditionsConfig.digitalTransformerResetTime * 20;
    }

    //values range -103,846 ... 2300
    public float getRecipeEfficiency()
    {
        if(!this.isProcessing) {
            return 0;
        }
        float eff = (float)(1/(Math.abs(this.targetReactivity - this.currentReactivity)/20+0.04)-1.2)*100-80;

        return Math.min(eff,(float)NCSteamAdditionsConfig.digitalTransformerEfficienctCap);
    }

    @Callback(doc = "--function():float Value betwean -103.846 and 2300")
    @Optional.Method(modid = "opencomputers")
    public Object[] getRecipeEfficiency(Context context, Arguments args)
    {
        return new Object[] {this.getRecipeEfficiency()};
    }

    @Callback(doc = "--function(): %")
    @Optional.Method(modid = "opencomputers")
    public Object[] getRecipeProgress(Context context, Arguments args)
    {
        float progress = (float)(this.time/this.baseProcessTime)*100;
        return new Object[] {progress};
    }

    @Callback(doc = "--function(float adjustment): ")
    @Optional.Method(modid = "opencomputers")
    public Object[] adjustEfficiency(Context context, Arguments args)
    {
        float adjustment = (float)args.checkDouble(0);
        this.adjust(adjustment);
        return new Object[] {};
    }
}
