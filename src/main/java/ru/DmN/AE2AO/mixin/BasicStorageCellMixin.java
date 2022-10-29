package ru.DmN.AE2AO.mixin;

import appeng.items.storage.BasicStorageCell;
import org.spongepowered.asm.mixin.Mixin;
import ru.DmN.AE2AO.AE2AOMain;

@Mixin(value = BasicStorageCell.class, remap = false)
public class BasicStorageCellMixin {
    boolean isFireResistant;
    public boolean isFireResistant() {
        return AE2AOMain.config.CellFireResistance || this.isFireResistant;
    }
}
