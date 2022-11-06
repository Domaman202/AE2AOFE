package ru.DmN.AE2AO;

import appeng.api.storage.channels.IItemStorageChannel;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IItemList;
import appeng.util.item.AEItemStack;
import appeng.util.item.ItemList;
import com.google.common.base.Preconditions;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nonnull;

public class AE2AOItemStorageChannel implements IItemStorageChannel {
    @Override
    public int getUnitsPerByte() {
        return AE2AOMain.config.ItemsPerByte.get();
    }

    @Nonnull
    @Override
    public IItemList<IAEItemStack> createList() {
        return new ItemList();
    }

    @Override
    public IAEItemStack createStack(@Nonnull Object input) {
        Preconditions.checkNotNull(input);

        if (input instanceof ItemStack) {
            return AEItemStack.fromItemStack((ItemStack) input);
        }

        return null;
    }

    @Override
    public IAEItemStack createFromNBT(@Nonnull CompoundNBT nbt) {
        Preconditions.checkNotNull(nbt);
        return AEItemStack.fromNBT(nbt);
    }

    @Override
    public IAEItemStack readFromPacket(@Nonnull PacketBuffer input) {
        Preconditions.checkNotNull(input);

        return AEItemStack.fromPacket(input);
    }
}
