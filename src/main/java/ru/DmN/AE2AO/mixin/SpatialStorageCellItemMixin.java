package ru.DmN.AE2AO.mixin;

import appeng.api.implementations.items.ISpatialStorageCell;
import appeng.items.AEBaseItem;
import appeng.items.storage.SpatialStorageCellItem;
import org.spongepowered.asm.mixin.Mixin;
import ru.DmN.AE2AO.AE2AOMain;
import ru.DmN.AE2AO.ICanHasExplosionResistance;
import ru.DmN.AE2AO.ICanHasFireResistance;

@Mixin(value = SpatialStorageCellItem.class, remap = false)
public abstract class SpatialStorageCellItemMixin extends AEBaseItem implements ISpatialStorageCell, ICanHasExplosionResistance, ICanHasFireResistance {
    public SpatialStorageCellItemMixin(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFireResistant() {
        return AE2AOMain.config.CellFireResistance.get();
    }

    @Override
    public boolean isExplosionResistant() {
        return AE2AOMain.config.CellExplosionResistance.get();
    }
}
