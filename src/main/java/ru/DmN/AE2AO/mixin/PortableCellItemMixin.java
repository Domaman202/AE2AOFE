package ru.DmN.AE2AO.mixin;

import appeng.items.tools.powered.PortableCellItem;
import org.spongepowered.asm.mixin.Mixin;
import ru.DmN.AE2AO.AE2AOMain;

@Mixin(value = PortableCellItem.class, remap = false)
public class PortableCellItemMixin {
    boolean isFireResistant;
    public boolean isFireResistant() {
        return AE2AOMain.config.PortableCellFireResistance || this.isFireResistant;
    }
}
