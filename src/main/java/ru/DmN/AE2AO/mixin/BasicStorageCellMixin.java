package ru.DmN.AE2AO.mixin;

import appeng.items.AEBaseItem;
import appeng.items.storage.AbstractStorageCell;
import org.spongepowered.asm.mixin.Mixin;
import ru.DmN.AE2AO.Main;

@Mixin(value = AbstractStorageCell.class, remap = false)
public abstract class BasicStorageCellMixin extends AEBaseItem {
    boolean isFireResistant;

    public BasicStorageCellMixin(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFireResistant() {
        return Main.config.CellFireResistance || this.isFireResistant;
    }
}