package igentuman.ncsteamadditions.tile;

import ic2.core.block.TileEntityHeatSourceInventory;
import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import igentuman.ncsteamadditions.machine.sound.SoundHandler;
import igentuman.ncsteamadditions.network.NCSProcessorUpdatePacket;
import igentuman.ncsteamadditions.processors.ProcessorsRegistry;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import mekanism.common.tile.TileEntityFuelwoodHeater;
import nc.ModCheck;
import nc.config.NCConfig;
import nc.init.NCItems;
import nc.recipe.AbstractRecipeHandler;
import nc.recipe.BasicRecipe;
import nc.recipe.BasicRecipeHandler;
import nc.recipe.RecipeInfo;
import nc.recipe.ingredient.IFluidIngredient;
import nc.recipe.ingredient.IItemIngredient;
import nc.tile.energy.ITileEnergy;
import nc.tile.energyFluid.TileEnergyFluidSidedInventory;
import nc.tile.fluid.ITileFluid;
import nc.tile.internal.energy.EnergyConnection;
import nc.tile.internal.fluid.Tank;
import nc.tile.internal.fluid.TankOutputSetting;
import nc.tile.internal.fluid.TankSorption;
import nc.tile.internal.inventory.ItemOutputSetting;
import nc.tile.internal.inventory.ItemSorption;
import nc.tile.inventory.ITileInventory;
import nc.tile.processor.IItemFluidProcessor;
import nc.tile.processor.IProcessor;
import nc.tile.processor.ITileSideConfigGui;
import nc.tile.processor.IUpgradable;
import nc.util.StackHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static igentuman.ncsteamadditions.NCSteamAdditions.MOD_ID;

public class TileNCSProcessor extends TileEnergyFluidSidedInventory implements IItemFluidProcessor, ITileSideConfigGui<NCSProcessorUpdatePacket>, IUpgradable {
    public final double defaultProcessTime;
    public final double defaultProcessPower;
    public double baseProcessTime;
    public double baseProcessPower;
    public double baseProcessRadiation;
    protected final int itemInputSize;
    protected final int fluidInputSize;
    protected final int itemOutputSize;
    protected final int fluidOutputSize;
    public double time;
    public double resetTime;
    public boolean isProcessing;
    public boolean canProcessInputs;
    public final boolean shouldLoseProgress;
    public final boolean hasUpgrades;
    public final int processorID;
    public final int sideConfigXOffset;
    public final int sideConfigYOffset;
    public final BasicRecipeHandler recipeHandler;
    protected RecipeInfo<BasicRecipe> recipeInfo;
    protected Set<EntityPlayer> updatePacketListeners;
    protected float adjustment = 0;
    protected int ticksLastReactivityInit = 0;
    protected int adjustmentAttempts = 0;
    protected int adjustmentsLimit = 5;
    public float currentReactivity;
    public float targetReactivity;
    protected int ticksToChange;

    private SoundEvent soundEvent;
    @SideOnly(Side.CLIENT)
    private ISound activeSound;

    public TileNCSProcessor(String code, int inputItems, int inputFluids, int outputItems, int outputFluids, int GUID)
    {
        this(
                code,
                inputItems,
                inputFluids,
                outputItems,
                outputFluids,
                defaultItemSorptions(inputItems, outputItems, true),
                defaultTankCapacities(20000, inputFluids, outputFluids),
                defaultTankSorptions(inputFluids, outputFluids),
                NCSteamAdditionsRecipes.validFluids[GUID],
                NCSteamAdditionsConfig.processor_time[GUID],
                0, true, true,
                NCSteamAdditionsRecipes.processorRecipeHandlers[GUID],
                GUID+1, 0,0,0,10
        );
    }

    public TileNCSProcessor(String name, int itemInSize, int fluidInSize, int itemOutSize, int fluidOutSize, @Nonnull List<ItemSorption> itemSorptions, @Nonnull IntList fluidCapacity, @Nonnull List<TankSorption> tankSorptions, List<List<String>> allowedFluids, double time, double power, boolean shouldLoseProgress, @Nonnull BasicRecipeHandler recipeHandler, int processorID, int sideConfigXOffset, int sideConfigYOffset) {
        this(name, itemInSize, fluidInSize, itemOutSize, fluidOutSize, itemSorptions, fluidCapacity, tankSorptions, allowedFluids, time, power, shouldLoseProgress, true, recipeHandler, processorID, sideConfigXOffset, sideConfigYOffset);
    }

    public TileNCSProcessor(String name, int itemInSize, int fluidInSize, int itemOutSize, int fluidOutSize, @Nonnull List<ItemSorption> itemSorptions, @Nonnull IntList fluidCapacity, @Nonnull List<TankSorption> tankSorptions, List<List<String>> allowedFluids, double time, double power, boolean shouldLoseProgress, boolean upgrades, @Nonnull BasicRecipeHandler recipeHandler, int processorID, int sideConfigXOffset, int sideConfigYOffset) {
        super(name, itemInSize + itemOutSize + (upgrades ? 2 : 0), ITileInventory.inventoryConnectionAll(itemSorptions), (long)IProcessor.getCapacity(processorID, 1.0D, 1.0D), power != 0.0D ? ITileEnergy.energyConnectionAll(EnergyConnection.IN) : ITileEnergy.energyConnectionAll(EnergyConnection.NON), fluidCapacity, allowedFluids, ITileFluid.fluidConnectionAll(tankSorptions));
        this.itemInputSize = itemInSize;
        this.fluidInputSize = fluidInSize;
        this.itemOutputSize = itemOutSize;
        this.fluidOutputSize = fluidOutSize;
        this.defaultProcessTime = NCConfig.processor_time_multiplier * time;
        this.defaultProcessPower = NCConfig.processor_power_multiplier * power;
        this.baseProcessTime = NCConfig.processor_time_multiplier * time;
        this.baseProcessPower = NCConfig.processor_power_multiplier * power;
        this.shouldLoseProgress = shouldLoseProgress;
        this.hasUpgrades = upgrades;
        this.processorID = processorID;
        this.sideConfigXOffset = sideConfigXOffset;
        this.sideConfigYOffset = sideConfigYOffset;
        this.setInputTanksSeparated(fluidInSize > 1);
        this.recipeHandler = recipeHandler;
        this.updatePacketListeners = new ObjectOpenHashSet();
    }

    public TileNCSProcessor(String name, int itemInSize, int fluidInSize, int itemOutSize, int fluidOutSize, @Nonnull List<ItemSorption> itemSorptions,
                            @Nonnull IntList fluidCapacity, @Nonnull List<TankSorption> tankSorptions, List<List<String>> allowedFluids, double time, double power, boolean shouldLoseProgress,
                            boolean upgrades, @Nonnull BasicRecipeHandler recipeHandler, int processorID,  int sideConfigXOffset, int sideConfigYOffset,float currentReactivity, float targetReactivity) {
        super(name, itemInSize + itemOutSize + (upgrades ? 2 : 0), ITileInventory.inventoryConnectionAll(itemSorptions), (long)IProcessor.getCapacity(processorID, 1.0D, 1.0D), power != 0.0D ? ITileEnergy.energyConnectionAll(EnergyConnection.IN) : ITileEnergy.energyConnectionAll(EnergyConnection.NON), fluidCapacity, allowedFluids, ITileFluid.fluidConnectionAll(tankSorptions));
        this.itemInputSize = itemInSize;
        this.fluidInputSize = fluidInSize;
        this.itemOutputSize = itemOutSize;
        this.fluidOutputSize = fluidOutSize;
        this.defaultProcessTime = NCConfig.processor_time_multiplier * time;
        this.defaultProcessPower = NCConfig.processor_power_multiplier * power;
        this.baseProcessTime = NCConfig.processor_time_multiplier * time;
        this.baseProcessPower = NCConfig.processor_power_multiplier * power;
        this.shouldLoseProgress = shouldLoseProgress;
        this.hasUpgrades = upgrades;
        this.processorID = processorID;
        this.sideConfigXOffset = sideConfigXOffset;
        this.sideConfigYOffset = sideConfigYOffset;
        this.setInputTanksSeparated(fluidInSize > 1);
        this.recipeHandler = recipeHandler;
        this.updatePacketListeners = new ObjectOpenHashSet();
        this.currentReactivity = currentReactivity;
        this.targetReactivity = targetReactivity;
        String sound = ProcessorsRegistry.get().processors()[processorID-1].getSound();
        if(!sound.equals("")) {
            soundEvent = new SoundEvent(new ResourceLocation(MOD_ID, sound));
        }
    }


    public static List<ItemSorption> defaultItemSorptions(int inSize, int outSize, boolean upgrades) {
        List<ItemSorption> itemSorptions = new ArrayList();

        int i;
        for(i = 0; i < inSize; ++i) {
            itemSorptions.add(ItemSorption.IN);
        }

        for(i = 0; i < outSize; ++i) {
            itemSorptions.add(ItemSorption.OUT);
        }

        if (upgrades) {
            itemSorptions.add(ItemSorption.IN);
            itemSorptions.add(ItemSorption.IN);
        }

        return itemSorptions;
    }

    public static IntList defaultTankCapacities(int capacity, int inSize, int outSize) {
        IntList tankCapacities = new IntArrayList();

        for(int i = 0; i < inSize + outSize; ++i) {
            tankCapacities.add(capacity);
        }

        return tankCapacities;
    }

    public static List<TankSorption> defaultTankSorptions(int inSize, int outSize) {
        List<TankSorption> tankSorptions = new ArrayList();

        int i;
        for(i = 0; i < inSize; ++i) {
            tankSorptions.add(TankSorption.IN);
        }

        for(i = 0; i < outSize; ++i) {
            tankSorptions.add(TankSorption.OUT);
        }

        return tankSorptions;
    }

    public void invalidate()
    {
        super.invalidate();
        if (!ProcessorsRegistry.get().processors()[processorID-1].getSound().isEmpty()) {
            stopSound();
        }
    }
    //values range -0.5 ... 950
    public float getRecipeEfficiency()
    {

        if(!this.isProcessing) {
            return 0;
        }
        float eff = (float)(1/(Math.abs(this.targetReactivity - this.currentReactivity)/20+0.05)-1)*10;
        float effCap = NCSteamAdditionsConfig.efficiencyCap > 0? NCSteamAdditionsConfig.efficiencyCap : 99999;
        return Math.min(eff,effCap);
    }

    public void onLoad() {
        super.onLoad();
        if (world.isRemote) {
            updateSound();
        }
        if (!this.world.isRemote) {
            this.refreshRecipe();
            this.refreshActivity();
            this.refreshUpgrades();
            this.isProcessing = this.isProcessing();
        }

    }
    @SideOnly(Side.CLIENT)
    protected void setSoundEvent(SoundEvent event) {
        this.soundEvent = event;
        SoundHandler.stopTileSound(getPos());
    }
    private int playSoundCooldown = 0;
    private long lastActive = -1;
    private int rapidChangeThreshold = 10;

    @SideOnly(Side.CLIENT)
    private void updateSound() {

        if(soundEvent == null) return;
        if (isProcessing()) {
            if (--playSoundCooldown > 0) {
                return;
            }

            if ((activeSound == null || !Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(activeSound))) {
                activeSound = SoundHandler.startTileSound(soundEvent.getSoundName(), (float)NCSteamAdditionsConfig.processorsSoundVolume, getPos());
            }
            playSoundCooldown = 20;
        } else {
            stopSound();
        }
    }

    @SideOnly(Side.CLIENT)
    protected void stopSound()
    {
        long downtime = world.getTotalWorldTime() - lastActive;
        if (activeSound != null && downtime > rapidChangeThreshold) {
            SoundHandler.stopTileSound(getPos());
            activeSound = null;
            playSoundCooldown = 0;
        }
    }

    protected void processExternalHeaters()
    {
        TileEntity heater;
        switch (getFacingHorizontal()) {
            case SOUTH:
                heater = world.getTileEntity(pos.north());
                break;
            case NORTH:
                heater = world.getTileEntity(pos.south());
                break;
            case WEST:
                heater = world.getTileEntity(pos.east());
                break;
            default:
                heater = world.getTileEntity(pos.west());
        }
        if (heater == null) return;
        double temperature = 0;
        if(ModCheck.mekanismLoaded() && heater instanceof mekanism.common.tile.TileEntityFuelwoodHeater) {
            temperature = ((mekanism.common.tile.TileEntityFuelwoodHeater) heater).getTemp();
            if(this.getCurrentReactivity() < this.getTargetReactivity()-1) {
                this.currentReactivity += (float)temperature/50000;
                currentReactivity = Math.min(currentReactivity,targetReactivity);
                ((TileEntityFuelwoodHeater) heater).temperature -= temperature/50;
            }
        } else if( ModCheck.ic2Loaded() && heater instanceof ic2.core.block.TileEntityHeatSourceInventory) {
            if(this.getCurrentReactivity() < this.getTargetReactivity()-1) {
                int heatPerTick = ((TileEntityHeatSourceInventory) heater).getMaxHeatEmittedPerTick();
                ((TileEntityHeatSourceInventory) heater).addtoHeatBuffer(-heatPerTick);
                this.currentReactivity += (float)heatPerTick/500;
                currentReactivity = Math.min(currentReactivity,targetReactivity);
            }
        }


    }

    public void update() {
        if (world.isRemote) {
            updateSound();
        }
        if (!this.world.isRemote) {
            boolean wasProcessing = this.isProcessing;
            this.isProcessing = this.isProcessing();
            boolean shouldUpdate = false;
            if(ModCheck.mekanismLoaded() || ModCheck.ic2Loaded()) {
                processExternalHeaters();
            }
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

    public void refreshRecipe() {
        this.recipeInfo = this.recipeHandler.getRecipeInfoFromInputs(this.getItemInputs(), this.getFluidInputs());
    }

    public void refreshActivity() {
        this.canProcessInputs = this.canProcessInputs();
    }

    public void refreshActivityOnProduction() {
        this.canProcessInputs = this.canProcessInputs();
    }

    public int getProcessTime() {
        return Math.max(1, (int)Math.round(Math.ceil(this.baseProcessTime / this.getSpeedMultiplier())));
    }

    public int getProcessPower() {
        return Math.min(2147483647, (int)(this.baseProcessPower * this.getPowerMultiplier()));
    }

    public int getProcessEnergy() {
        return this.getProcessTime() * this.getProcessPower();
    }

    public boolean setRecipeStats() {
        if (this.recipeInfo == null) {
            this.baseProcessTime = this.defaultProcessTime;
            this.baseProcessPower = this.defaultProcessPower;
            this.baseProcessRadiation = 0.0D;
            return false;
        } else {
            BasicRecipe recipe = (BasicRecipe)this.recipeInfo.getRecipe();
            this.baseProcessTime = recipe.getBaseProcessTime(this.defaultProcessTime);
            this.baseProcessPower = recipe.getBaseProcessPower(this.defaultProcessPower);
            this.baseProcessRadiation = recipe.getBaseProcessRadiation();
            return true;
        }
    }

    public void setCapacityFromSpeed() {
        int capacity = IProcessor.getCapacity(this.processorID, this.getSpeedMultiplier(), this.getPowerMultiplier());
        this.getEnergyStorage().setStorageCapacity((long)capacity);
        this.getEnergyStorage().setMaxTransfer(capacity);
    }

    private int getMaxEnergyModified() {
        return ModCheck.galacticraftLoaded() ? Math.max(0, this.getMaxEnergyStored() - 16) : this.getMaxEnergyStored();
    }

    public boolean isProcessing() {
        return this.readyToProcess() && !this.isHaltedByRedstone();
    }

    public boolean isHaltedByRedstone() {
        return this.getRedstoneControl() && this.getIsRedstonePowered();
    }

    public boolean readyToProcess() {
        return this.canProcessInputs && this.hasSufficientEnergy();
    }

    public boolean canProcessInputs() {
        boolean validRecipe = this.setRecipeStats();
        boolean canProcess = validRecipe && this.canProduceProducts();
        if (!canProcess) {
            this.time = MathHelper.clamp(this.time, 0.0D, this.baseProcessTime - 1.0D);
        }

        return canProcess;
    }

    public boolean hasSufficientEnergy() {
        return this.time <= this.resetTime && (this.getProcessEnergy() >= this.getMaxEnergyModified() && this.getEnergyStored() >= this.getMaxEnergyModified() || this.getProcessEnergy() <= this.getEnergyStored()) || this.time > this.resetTime && this.getEnergyStored() >= this.getProcessPower();
    }

    public boolean canProduceProducts() {
        int j;
        for(j = 0; j < this.itemOutputSize; ++j) {
            if (this.getItemOutputSetting(j + this.itemInputSize) == ItemOutputSetting.VOID) {
                this.getInventoryStacks().set(j + this.itemInputSize, ItemStack.EMPTY);
            } else {
                IItemIngredient itemProduct = (IItemIngredient)this.getItemProducts().get(j);
                if (itemProduct.getMaxStackSize(0) > 0) {
                    if (itemProduct.getStack() == null || ((ItemStack)itemProduct.getStack()).isEmpty()) {
                        return false;
                    }

                    if (!((ItemStack)this.getInventoryStacks().get(j + this.itemInputSize)).isEmpty()) {
                        if (!((ItemStack)this.getInventoryStacks().get(j + this.itemInputSize)).isItemEqual((ItemStack)itemProduct.getStack())) {
                            return false;
                        }

                        if (this.getItemOutputSetting(j + this.itemInputSize) == ItemOutputSetting.DEFAULT && ((ItemStack)this.getInventoryStacks().get(j + this.itemInputSize)).getCount() + itemProduct.getMaxStackSize(0) > ((ItemStack)this.getInventoryStacks().get(j + this.itemInputSize)).getMaxStackSize()) {
                            return false;
                        }
                    }
                }
            }
        }

        for(j = 0; j < this.fluidOutputSize; ++j) {
            if (this.getTankOutputSetting(j + this.fluidInputSize) == TankOutputSetting.VOID) {
                this.clearTank(j + this.fluidInputSize);
            } else {
                IFluidIngredient fluidProduct = (IFluidIngredient)this.getFluidProducts().get(j);
                if (fluidProduct.getMaxStackSize(0) > 0) {
                    if (fluidProduct.getStack() == null) {
                        return false;
                    }

                    if (!((Tank)this.getTanks().get(j + this.fluidInputSize)).isEmpty()) {
                        if (!((Tank)this.getTanks().get(j + this.fluidInputSize)).getFluid().isFluidEqual((FluidStack)fluidProduct.getStack())) {
                            return false;
                        }

                        if (this.getTankOutputSetting(j + this.fluidInputSize) == TankOutputSetting.DEFAULT && ((Tank)this.getTanks().get(j + this.fluidInputSize)).getFluidAmount() + fluidProduct.getMaxStackSize(0) > ((Tank)this.getTanks().get(j + this.fluidInputSize)).getCapacity()) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    public void process() {
        if(currentReactivity < this.targetReactivity) {
            this.currentReactivity += (this.targetReactivity - this.currentReactivity) * (float)NCSteamAdditionsConfig.efficiencyChangeSpeed/50000;
        }
        float efficiency = 1;
        if(NCSteamAdditionsConfig.efficiencyCap > 0) {
            efficiency = Math.min(this.getRecipeEfficiency(), (float) NCSteamAdditionsConfig.efficiencyCap);
        }
        this.time += this.getSpeedMultiplier() * efficiency/100;
        this.getEnergyStorage().changeEnergyStored((long)(-this.getProcessPower()));
        this.getRadiationSource().setRadiationLevel(this.baseProcessRadiation * this.getSpeedMultiplier());

        while(this.time >= this.baseProcessTime) {
            this.finishProcess();
        }

    }

    public void finishProcess() {
        double oldProcessTime = this.baseProcessTime;
        this.produceProducts();
        this.refreshRecipe();
        this.time = this.resetTime = Math.max(0.0D, this.time - oldProcessTime);
        this.refreshActivityOnProduction();
        if (!this.canProcessInputs) {
            this.time = this.resetTime = 0.0D;

            for(int i = 0; i < this.fluidInputSize; ++i) {
                if (this.getVoidUnusableFluidInput(i)) {
                    ((Tank)this.getTanks().get(i)).setFluid((FluidStack)null);
                }
            }
        }

    }

    public void produceProducts() {
        if (this.recipeInfo != null) {
            IntList itemInputOrder = this.recipeInfo.getItemInputOrder();
            IntList fluidInputOrder = this.recipeInfo.getFluidInputOrder();
            if (itemInputOrder != AbstractRecipeHandler.INVALID && fluidInputOrder != AbstractRecipeHandler.INVALID) {
                int j;
                for(j = 0; j < this.itemInputSize; ++j) {
                    int itemIngredientStackSize = ((IItemIngredient)this.getItemIngredients().get((Integer)itemInputOrder.get(j))).getMaxStackSize((Integer)this.recipeInfo.getItemIngredientNumbers().get(j));
                    if (itemIngredientStackSize > 0) {
                        ((ItemStack)this.getInventoryStacks().get(j)).shrink(itemIngredientStackSize);
                    }

                    if (((ItemStack)this.getInventoryStacks().get(j)).getCount() <= 0) {
                        this.getInventoryStacks().set(j, ItemStack.EMPTY);
                    }
                }

                int count;
                for(j = 0; j < this.fluidInputSize; ++j) {
                    Tank tank = (Tank)this.getTanks().get(j);
                    count = ((IFluidIngredient)this.getFluidIngredients().get((Integer)fluidInputOrder.get(j))).getMaxStackSize((Integer)this.recipeInfo.getFluidIngredientNumbers().get(j));
                    if (count > 0) {
                        tank.changeFluidAmount(-count);
                    }

                    if (tank.getFluidAmount() <= 0) {
                        tank.setFluidStored((FluidStack)null);
                    }
                }

                for(j = 0; j < this.itemOutputSize; ++j) {
                    if (this.getItemOutputSetting(j + this.itemInputSize) == ItemOutputSetting.VOID) {
                        this.getInventoryStacks().set(j + this.itemInputSize, ItemStack.EMPTY);
                    } else {
                        IItemIngredient itemProduct = (IItemIngredient)this.getItemProducts().get(j);
                        if (itemProduct.getMaxStackSize(0) > 0) {
                            if (((ItemStack)this.getInventoryStacks().get(j + this.itemInputSize)).isEmpty()) {
                                this.getInventoryStacks().set(j + this.itemInputSize, itemProduct.getNextStack(0));
                            } else if (((ItemStack)this.getInventoryStacks().get(j + this.itemInputSize)).isItemEqual((ItemStack)itemProduct.getStack())) {
                                count = Math.min(this.getInventoryStackLimit(), ((ItemStack)this.getInventoryStacks().get(j + this.itemInputSize)).getCount() + itemProduct.getNextStackSize(0));
                                ((ItemStack)this.getInventoryStacks().get(j + this.itemInputSize)).setCount(count);
                            }
                        }
                    }
                }

                for(j = 0; j < this.fluidOutputSize; ++j) {
                    if (this.getTankOutputSetting(j + this.fluidInputSize) == TankOutputSetting.VOID) {
                        this.clearTank(j + this.fluidInputSize);
                    } else {
                        IFluidIngredient fluidProduct = (IFluidIngredient)this.getFluidProducts().get(j);
                        if (fluidProduct.getMaxStackSize(0) > 0) {
                            if (((Tank)this.getTanks().get(j + this.fluidInputSize)).isEmpty()) {
                                ((Tank)this.getTanks().get(j + this.fluidInputSize)).setFluidStored(fluidProduct.getNextStack(0));
                            } else if (((Tank)this.getTanks().get(j + this.fluidInputSize)).getFluid().isFluidEqual((FluidStack)fluidProduct.getStack())) {
                                ((Tank)this.getTanks().get(j + this.fluidInputSize)).changeFluidAmount(fluidProduct.getNextStackSize(0));
                            }
                        }
                    }
                }

            }
        }
    }

    public void loseProgress() {
        this.time = MathHelper.clamp(this.time - 1.5D * this.getSpeedMultiplier(), 0.0D, this.baseProcessTime);
        if (this.time < this.resetTime) {
            this.resetTime = this.time;
        }
        this.currentReactivity -= (this.targetReactivity-this.currentReactivity)*(float)NCSteamAdditionsConfig.efficiencyChangeSpeed/50000;

    }
    public float getCurrentReactivity() {
        if(!this.isProcessing) {
            return 0;
        }
        return  this.currentReactivity;
    }

    public float getTargetReactivity() {
        if(!this.isProcessing) {
            return 0;
        }
        return  this.targetReactivity;
    }

    public void tickReactivity()
    {
        if(world.isRemote) return;
        this.ticksLastReactivityInit--;
        if(this.ticksToChange < 1 || this.currentReactivity >= 20 || this.currentReactivity <= 0) {
            this.ticksToChange = 0;
            this. adjustment = 0;
            return;
        }

        this.currentReactivity += (this.adjustment/this.ticksToChange);
        this.currentReactivity = Math.min(20,Math.max(0,this.currentReactivity));
        this.ticksToChange--;
        this.adjustment-=(this.adjustment/this.ticksToChange);
        this.sendTileUpdatePacketToAll();
        this.sendTileUpdatePacketToListeners();
    }

    public int getItemInputSize() {
        return this.itemInputSize;
    }

    public int getItemOutputSize() {
        return this.itemOutputSize;
    }

    public int getFluidInputSize() {
        return this.fluidInputSize;
    }

    public int getFluidOutputputSize() {
        return this.fluidOutputSize;
    }

    public List<ItemStack> getItemInputs() {
        return this.getInventoryStacks().subList(0, this.itemInputSize);
    }

    public List<Tank> getFluidInputs() {
        return this.getTanks().subList(0, this.fluidInputSize);
    }

    public List<IItemIngredient> getItemIngredients() {
        return ((BasicRecipe)this.recipeInfo.getRecipe()).getItemIngredients();
    }

    public List<IFluidIngredient> getFluidIngredients() {
        return ((BasicRecipe)this.recipeInfo.getRecipe()).getFluidIngredients();
    }

    public List<IItemIngredient> getItemProducts() {
        return ((BasicRecipe)this.recipeInfo.getRecipe()).getItemProducts();
    }

    public List<IFluidIngredient> getFluidProducts() {
        return ((BasicRecipe)this.recipeInfo.getRecipe()).getFluidProducts();
    }

    public boolean hasUpgrades() {
        return this.hasUpgrades;
    }

    public int getSpeedUpgradeSlot() {
        return this.itemInputSize + this.itemOutputSize;
    }

    public int getEnergyUpgradeSlot() {
        return this.itemInputSize + this.itemOutputSize + 1;
    }

    public int getSpeedCount() {
        return this.hasUpgrades ? ((ItemStack)this.getInventoryStacks().get(this.getSpeedUpgradeSlot())).getCount() + 1 : 1;
    }

    public int getEnergyCount() {
        return this.hasUpgrades ? Math.min(this.getSpeedCount(), ((ItemStack)this.getInventoryStacks().get(this.getEnergyUpgradeSlot())).getCount() + 1) : 1;
    }

    public void refreshUpgrades() {
        this.setCapacityFromSpeed();
    }

    public int getSinkTier() {
        return 10;
    }

    public int getSourceTier() {
        return 1;
    }

    public ItemStack decrStackSize(int slot, int amount) {
        if(getItemInputSize() == 0) return ItemStack.EMPTY;
        ItemStack stack = super.decrStackSize(slot, amount);
        if (!this.world.isRemote) {
            if (slot < this.itemInputSize) {
                this.refreshRecipe();
                this.refreshActivity();
            } else if (slot < this.itemInputSize + this.itemOutputSize) {
                this.refreshActivity();
            } else if (slot == this.getSpeedUpgradeSlot() || slot == this.getEnergyUpgradeSlot()) {
                this.refreshUpgrades();
            }
        }

        return stack;
    }

    public void setInventorySlotContents(int slot, ItemStack stack) {
        if(getItemInputSize() == 0) return;
         super.setInventorySlotContents(slot, stack);
        if (!this.world.isRemote) {
            if (slot < this.itemInputSize) {
                this.refreshRecipe();
                this.refreshActivity();
            } else if (slot < this.itemInputSize + this.itemOutputSize) {
                this.refreshActivity();
            } else if (slot == this.getSpeedUpgradeSlot() || slot == this.getEnergyUpgradeSlot()) {
                this.refreshUpgrades();
            }
        }

    }

    public void markDirty() {
        this.refreshRecipe();
        this.refreshActivity();
        this.refreshUpgrades();
        super.markDirty();
    }

    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        } else {
            if (this.hasUpgrades && stack.getItem() == NCItems.upgrade) {
                if (slot == this.getSpeedUpgradeSlot()) {
                    return StackHelper.getMetadata(stack) == 0;
                }

                if (slot == this.getEnergyUpgradeSlot()) {
                    return StackHelper.getMetadata(stack) == 1;
                }
            }

            if (slot >= this.itemInputSize) {
                return false;
            } else {
                return NCConfig.smart_processor_input ? this.recipeHandler.isValidItemInput(slot, stack, this.recipeInfo, this.getItemInputs(), this.inputItemStacksExcludingSlot(slot)) : this.recipeHandler.isValidItemInput(slot, stack);
            }
        }
    }

    public List<ItemStack> inputItemStacksExcludingSlot(int slot) {
        List<ItemStack> inputItemsExcludingSlot = new ArrayList(this.getItemInputs());
        inputItemsExcludingSlot.remove(slot);
        return inputItemsExcludingSlot;
    }

    public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side) {
        return super.canInsertItem(slot, stack, side) && this.isItemValidForSlot(slot, stack);
    }

    public boolean hasConfigurableInventoryConnections() {
        return true;
    }

    public boolean hasConfigurableFluidConnections() {
        return true;
    }

    public NBTTagCompound writeAll(NBTTagCompound nbt) {
        super.writeAll(nbt);
        nbt.setFloat("currentReactivity", this.currentReactivity);
        nbt.setFloat("targetReactivity", this.targetReactivity);
        nbt.setInteger("adjustmentsAttempts", this.adjustmentAttempts);
        nbt.setDouble("time", this.time);
        nbt.setDouble("resetTime", this.resetTime);
        nbt.setBoolean("isProcessing", this.isProcessing);
        nbt.setBoolean("canProcessInputs", this.canProcessInputs);
        return nbt;
    }

    public void readAll(NBTTagCompound nbt) {
        super.readAll(nbt);
        this.currentReactivity = nbt.getFloat("currentReactivity");
        this.targetReactivity = nbt.getFloat("targetReactivity");
        this.adjustmentAttempts = nbt.getInteger("adjustmentsAttempts");
        this.time = nbt.getDouble("time");
        this.resetTime = nbt.getDouble("resetTime");
        this.isProcessing = nbt.getBoolean("isProcessing");
        this.canProcessInputs = nbt.getBoolean("canProcessInputs");
        if (nbt.hasKey("redstoneControl")) {
            this.setRedstoneControl(nbt.getBoolean("redstoneControl"));
        } else {
            this.setRedstoneControl(true);
        }

    }

    public int getGuiID() {
        return this.processorID;
    }

    public Set<EntityPlayer> getTileUpdatePacketListeners() {
        return this.updatePacketListeners;
    }

    public NCSProcessorUpdatePacket getTileUpdatePacket() {
        return new NCSProcessorUpdatePacket(
                this.pos,
                this.isProcessing,
                this.time,
                this.getEnergyStored(),
                this.baseProcessTime,
                this.baseProcessPower,
                this.getTanks(),
                this.currentReactivity,
                this.targetReactivity,
                this.adjustmentAttempts
        );
    }

    public void onTileUpdatePacket(NCSProcessorUpdatePacket message) {
        this.isProcessing = message.isProcessing;
        this.time = message.time;
        this.getEnergyStorage().setEnergyStored((long)message.energyStored);
        this.baseProcessTime = message.baseProcessTime;
        this.baseProcessPower = message.baseProcessPower;

        for(int i = 0; i < this.getTanks().size(); ++i) {
            ((Tank)this.getTanks().get(i)).readInfo((Tank.TankInfo)message.tanksInfo.get(i));
        }
        this.currentReactivity = message.currentReactivity;
        this.targetReactivity = message.targetReactivity;
        this.adjustmentAttempts = message.adjustmentAttempts;
    }

    public int getSideConfigXOffset() {
        return this.sideConfigXOffset;
    }

    public int getSideConfigYOffset() {
        return this.sideConfigYOffset;
    }
}
