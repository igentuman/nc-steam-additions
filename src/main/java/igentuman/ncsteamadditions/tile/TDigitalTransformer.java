package igentuman.ncsteamadditions.tile;

import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import igentuman.ncsteamadditions.processors.DigitalTransformer;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraftforge.fml.common.Optional;

import java.util.Random;

@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "opencomputers")
public class TDigitalTransformer extends TileNCSProcessor implements SimpleComponent
{
    public TDigitalTransformer()
    {
        super(
                DigitalTransformer.code,
                DigitalTransformer.inputItems,
                DigitalTransformer.inputFluids,
                DigitalTransformer.outputItems,
                DigitalTransformer.outputFluids,
                defaultItemSorptions(DigitalTransformer.inputItems, DigitalTransformer.outputItems, true),
                defaultTankCapacities(40000, DigitalTransformer.inputFluids, DigitalTransformer.outputFluids),
                defaultTankSorptions(DigitalTransformer.inputFluids, DigitalTransformer.outputFluids),
                NCSteamAdditionsRecipes.validFluids[DigitalTransformer.GUID],
                NCSteamAdditionsConfig.processor_time[DigitalTransformer.GUID],
                NCSteamAdditionsConfig.digitalTransformerRF, true,
                NCSteamAdditionsRecipes.processorRecipeHandlers[DigitalTransformer.GUID],
                DigitalTransformer.GUID+1, 0,0,0
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

    public void process() {
        if(this.currentReactivity == 0 && this.targetReactivity == 0) {
            this.initReactivity();
        }
        this.tickReactivity();
        this.time += this.getSpeedMultiplier() * getRecipeEfficiency();
        this.getEnergyStorage().changeEnergyStored((long)(-this.getProcessPower()));
        this.getRadiationSource().setRadiationLevel(this.baseProcessRadiation * this.getSpeedMultiplier());

        while(this.time >= this.baseProcessTime) {
            this.finishProcess();
        }
    }

    @Optional.Method(modid = "opencomputers")
    public String getComponentName()
    {
        return DigitalTransformer.code;
    }

    protected int baseTicksToChange = 200;
    public void adjust(float step)
    {
        this.adjustment = step;
        this.ticksToChange = this.baseTicksToChange;
    }

    //each time machine gets a new recipe it initialize parameters
    public void initReactivity()
    {
        if(world.isRemote) return;
        float low = 0F;
        float high = 2.0F;
        this.targetReactivity = (low + new Random().nextFloat() * (high - low))*10;
        low = 0F;
        high = 1.5F;
        this.currentReactivity = (low + new Random().nextFloat() * (high - low))*10;
        ticksLastReactivityInit = 8000;
    }

    @Callback(doc = "--function():float Value betwean -0.48 and 10")
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
