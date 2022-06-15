package igentuman.ncsteamadditions.block;

import ic2.api.item.IElectricItemManager;
import ic2.api.item.ISpecialElectricItem;
import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;

import igentuman.ncsteamadditions.processors.AbstractProcessor;
import nc.item.energy.ElectricItemManager;
import nc.item.energy.IChargableItem;
import nc.item.energy.ItemEnergyCapabilityProvider;
import nc.tile.internal.energy.EnergyConnection;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.Optional;

import javax.annotation.Nullable;

@Optional.InterfaceList({@Optional.Interface(
        iface = "ic2.api.item.ISpecialElectricItem",
        modid = "ic2"
)})
public class BlockEnergyProcessor extends BlockProcessor implements ISpecialElectricItem, IChargableItem {


    public <T extends AbstractProcessor> BlockEnergyProcessor(T processor) {
        super(processor);
     }

    @Optional.Method(
            modid = "ic2"
    )
    @Override
    public IElectricItemManager getManager(ItemStack itemStack) {
        return ElectricItemManager.getElectricItemManager(this);
    }

    @Override
    public void setEnergyStored(ItemStack stack, long energy) {
        IChargableItem.super.setEnergyStored(stack, energy);
    }

    @Override
    public long getMaxEnergyStored(ItemStack itemStack) {
        return 1024;
    }

    @Override
    public int getMaxTransfer(ItemStack itemStack) {
        return NCSteamAdditionsConfig.turbineRF;
    }

    @Override
    public boolean canReceive(ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean canExtract(ItemStack itemStack) {
        return true;
    }

    @Override
    public EnergyConnection getEnergyConnection(ItemStack itemStack) {
        return EnergyConnection.OUT;
    }

    @Override
    public int getEnergyTier(ItemStack itemStack) {
        return 1;
    }

    @Override
    public void setActivity(boolean isActive, TileEntity tile) {
        super.setActivity(isActive, tile);
    }

    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new ItemEnergyCapabilityProvider(stack, 1024, NCSteamAdditionsConfig.turbineRF, this.getEnergyStored(stack), EnergyConnection.OUT, 1);
    }
}
