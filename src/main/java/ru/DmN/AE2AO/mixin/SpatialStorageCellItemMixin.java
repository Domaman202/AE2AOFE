package ru.DmN.AE2AO.mixin;

import appeng.items.AEBaseItem;
import appeng.items.storage.SpatialStorageCellItem;
import org.spongepowered.asm.mixin.Mixin;
import ru.DmN.AE2AO.Main;

@Mixin(value = SpatialStorageCellItem.class, remap = false)
public abstract class SpatialStorageCellItemMixin extends AEBaseItem {
    boolean burnable;

    public SpatialStorageCellItemMixin(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isImmuneToFire() {
        return Main.config.CellFireResistance || this.burnable;
    }
}
