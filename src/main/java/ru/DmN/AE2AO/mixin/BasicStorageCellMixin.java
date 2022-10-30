package ru.DmN.AE2AO.mixin;

import appeng.items.AEBaseItem;
import appeng.items.storage.AbstractStorageCell;
import org.spongepowered.asm.mixin.Mixin;
import ru.DmN.AE2AO.Main;

@Mixin(value = AbstractStorageCell.class, remap = false)
public abstract class BasicStorageCellMixin extends AEBaseItem {
    boolean burnable;

    public BasicStorageCellMixin(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isImmuneToFire() {
        return Main.config.CellFireResistance || this.burnable;
    }
}