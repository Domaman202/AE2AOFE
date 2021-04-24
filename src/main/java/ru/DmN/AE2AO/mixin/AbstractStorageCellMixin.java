package ru.DmN.AE2AO.mixin;

import appeng.items.AEBaseItem;
import appeng.items.storage.AbstractStorageCell;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AbstractStorageCell.class)
public abstract class AbstractStorageCellMixin extends AEBaseItem {
    public AbstractStorageCellMixin(Properties properties) { super(properties); }
}
