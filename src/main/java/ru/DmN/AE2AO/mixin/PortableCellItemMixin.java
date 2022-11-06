package ru.DmN.AE2AO.mixin;

import appeng.api.storage.cells.IBasicCellItem;
import appeng.items.tools.powered.PortableCellItem;
import appeng.items.tools.powered.powersink.AEBasePoweredItem;
import org.spongepowered.asm.mixin.Mixin;
import ru.DmN.AE2AO.AE2AOMain;
import ru.DmN.AE2AO.ICanHasExplosionResistance;

import java.util.function.DoubleSupplier;

@Mixin(value = PortableCellItem.class, remap = false)
public abstract class PortableCellItemMixin extends AEBasePoweredItem implements IBasicCellItem, ICanHasExplosionResistance {
    public PortableCellItemMixin(DoubleSupplier powerCapacity, Properties props) {
        super(powerCapacity, props);
    }

    @Override
    public boolean isFireResistant() {
        return AE2AOMain.config.PortableCellFireResistance.get() || super.isFireResistant();
    }

    @Override
    public boolean isExplosionResistant() {
        return AE2AOMain.config.PortableCellExplosionResistance.get();
    }
}
