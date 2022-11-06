package ru.DmN.AE2AO;

import appeng.api.storage.channels.IFluidStorageChannel;
import appeng.api.storage.data.IAEFluidStack;
import appeng.api.storage.data.IItemList;
import appeng.fluids.items.FluidDummyItem;
import appeng.fluids.util.AEFluidStack;
import appeng.fluids.util.FluidList;
import com.google.common.base.Preconditions;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

import javax.annotation.Nonnull;

public class AE2AOFluidStorageChannel implements IFluidStorageChannel {

    @Override
    public int transferFactor() {
        return 125;
    }

    @Override
    public int getUnitsPerByte() {
        return AE2AOMain.config.MbPerByte.get();
    }

    @Nonnull
    @Override
    public IItemList<IAEFluidStack> createList() {
        return new FluidList();
    }

    @Override
    public IAEFluidStack createStack(@Nonnull Object input) {
        Preconditions.checkNotNull(input);

        if (input instanceof FluidStack) {
            return AEFluidStack.fromFluidStack((FluidStack) input);
        }
        if (input instanceof ItemStack) {
            final ItemStack is = (ItemStack) input;
            if (is.getItem() instanceof FluidDummyItem) {
                return AEFluidStack.fromFluidStack(((FluidDummyItem) is.getItem()).getFluidStack(is));
            } else {
                return AEFluidStack.fromFluidStack(FluidUtil.getFluidContained(is).orElse(null));
            }
        }

        return null;
    }

    @Override
    public IAEFluidStack readFromPacket(@Nonnull PacketBuffer input) {
        Preconditions.checkNotNull(input);

        return AEFluidStack.fromPacket(input);
    }

    @Override
    public IAEFluidStack createFromNBT(@Nonnull CompoundNBT nbt) {
        Preconditions.checkNotNull(nbt);
        return AEFluidStack.fromNBT(nbt);
    }
}
