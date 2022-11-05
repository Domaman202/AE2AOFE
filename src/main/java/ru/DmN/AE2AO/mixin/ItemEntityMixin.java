package ru.DmN.AE2AO.mixin;

import appeng.items.storage.AbstractStorageCell;
import appeng.items.storage.SpatialStorageCellItem;
import appeng.items.tools.powered.PortableCellItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import ru.DmN.AE2AO.AE2AOMain;

@Mixin(value = ItemEntity.class, remap = false)
public abstract class ItemEntityMixin extends Entity {
    public ItemEntityMixin(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (source.isFire()) {
            if (AE2AOMain.config.CellFireResistance.get() &&
                    invokeGetItem().getItem() instanceof AbstractStorageCell
            ) {
                return true;
            }
            if (AE2AOMain.config.CellFireResistance.get() &&
                    invokeGetItem().getItem() instanceof SpatialStorageCellItem
            ) {
                return true;
            }
            if (AE2AOMain.config.PortableCellFireResistance.get() &&
                    invokeGetItem().getItem() instanceof PortableCellItem
            ) {
                return true;
            }
        }
        return super.isInvulnerableTo(source);
    }

    @Invoker("getItem")
    public abstract ItemStack invokeGetItem();
}
