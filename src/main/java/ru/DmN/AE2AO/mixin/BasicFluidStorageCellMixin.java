package ru.DmN.AE2AO.mixin;

import appeng.api.storage.data.IAEFluidStack;
import appeng.fluids.items.BasicFluidStorageCell;
import appeng.items.materials.MaterialType;
import appeng.items.storage.AbstractStorageCell;
import org.spongepowered.asm.mixin.Mixin;
import ru.DmN.AE2AO.AE2AOMain;
import ru.DmN.AE2AO.ICanHasExplosionResistance;

@Mixin(value = BasicFluidStorageCell.class, remap = false)
public abstract class BasicFluidStorageCellMixin extends AbstractStorageCell<IAEFluidStack> implements ICanHasExplosionResistance {
    public BasicFluidStorageCellMixin(Properties properties, MaterialType whichCell, int kilobytes) {
        super(properties, whichCell, kilobytes);
    }

    @Override
    public boolean isImmuneToFire() {
        return AE2AOMain.config.CellFireResistance.get() || super.isImmuneToFire();
    }

    @Override
    public boolean isExplosionResistant() {
        return AE2AOMain.config.CellExplosionResistance.get();
    }
}
