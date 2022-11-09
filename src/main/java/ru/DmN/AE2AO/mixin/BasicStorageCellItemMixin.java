package ru.DmN.AE2AO.mixin;

import appeng.api.storage.data.IAEFluidStack;
import appeng.items.materials.MaterialType;
import appeng.items.storage.AbstractStorageCell;
import appeng.items.storage.BasicStorageCellItem;
import org.spongepowered.asm.mixin.Mixin;
import ru.DmN.AE2AO.AE2AOMain;
import ru.DmN.AE2AO.ICanHasExplosionResistance;

@Mixin(value = BasicStorageCellItem.class, remap = false)
public abstract class BasicStorageCellItemMixin extends AbstractStorageCell<IAEFluidStack> implements ICanHasExplosionResistance {
    public BasicStorageCellItemMixin(Properties properties, MaterialType whichCell, int kilobytes) {
        super(properties, whichCell, kilobytes);
    }

    @Override
    public boolean isFireResistant() {
        return AE2AOMain.config.CellFireResistance.get() || super.isFireResistant();
    }

    @Override
    public boolean isExplosionResistant() {
        return AE2AOMain.config.CellExplosionResistance.get();
    }
}
