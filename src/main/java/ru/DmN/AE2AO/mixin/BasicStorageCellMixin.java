package ru.DmN.AE2AO.mixin;

import appeng.api.storage.cells.IBasicCellItem;
import appeng.hooks.AEToolItem;
import appeng.items.AEBaseItem;
import appeng.items.storage.BasicStorageCell;
import org.spongepowered.asm.mixin.Mixin;
import ru.DmN.AE2AO.AE2AOMain;
import ru.DmN.AE2AO.ICanHasExplosionResistance;

@Mixin(value = BasicStorageCell.class, remap = false)
public abstract class BasicStorageCellMixin extends AEBaseItem implements IBasicCellItem, AEToolItem, ICanHasExplosionResistance {
    public BasicStorageCellMixin(Properties properties) {
        super(properties);
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
