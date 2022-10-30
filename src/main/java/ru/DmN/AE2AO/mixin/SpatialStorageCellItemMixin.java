package ru.DmN.AE2AO.mixin;

import appeng.items.AEBaseItem;
import appeng.items.storage.SpatialStorageCellItem;
import org.spongepowered.asm.mixin.Mixin;
import ru.DmN.AE2AO.AE2AOMain;

@Mixin(value = SpatialStorageCellItem.class, remap = false)
public abstract class SpatialStorageCellItemMixin extends AEBaseItem {
    boolean isFireResistant;

    public SpatialStorageCellItemMixin(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFireResistant() {
        return AE2AOMain.config.CellFireResistance || this.isFireResistant;
    }
}
