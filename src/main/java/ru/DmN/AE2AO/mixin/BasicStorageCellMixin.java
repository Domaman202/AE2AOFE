package ru.DmN.AE2AO.mixin;

import appeng.items.AEBaseItem;
import appeng.items.storage.BasicStorageCell;
import org.spongepowered.asm.mixin.Mixin;
import ru.DmN.AE2AO.AE2AOMain;

@Mixin(value = BasicStorageCell.class, remap = false)
public abstract class BasicStorageCellMixin extends AEBaseItem {
    public BasicStorageCellMixin(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFireResistant() {
        return AE2AOMain.config.CellFireResistance.get() || super.isFireResistant();
    }
}
