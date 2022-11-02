package ru.DmN.AE2AO.mixin;

import appeng.items.AEBaseItem;
import appeng.items.storage.AbstractStorageCell;
import org.spongepowered.asm.mixin.Mixin;
import ru.DmN.AE2AO.AE2AOMain;

@Mixin(AbstractStorageCell.class)
public class AbstractStorageCellMixin extends AEBaseItem {
    public AbstractStorageCellMixin(Properties properties) { super(properties); }

    @Override
    public boolean isImmuneToFire() {
        return AE2AOMain.config.CellFireResistance.get() || super.isImmuneToFire();
    }
}
