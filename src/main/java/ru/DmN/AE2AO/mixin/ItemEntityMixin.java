package ru.DmN.AE2AO.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import ru.DmN.AE2AO.ICanHasExplosionResistance;

import javax.annotation.Nonnull;

@Mixin(value = ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {

    @Shadow
    public abstract ItemStack getItem();

    public ItemEntityMixin(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    @Override
    public boolean isInvulnerableTo(@Nonnull DamageSource source) {
        if (source.isExplosion()) {
            if (getItem().getItem() instanceof ICanHasExplosionResistance) {
                ICanHasExplosionResistance item = (ICanHasExplosionResistance) getItem().getItem();
                if (item.isExplosionResistant()) {
                    return true;
                }
            }
        }
        return super.isInvulnerableTo(source);
    }

}
