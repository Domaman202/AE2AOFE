package ru.DmN.AE2AO.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import ru.DmN.AE2AO.ICanHasBlastResistance;

import javax.annotation.Nonnull;

@Mixin(value = ItemEntity.class, remap = false)
public abstract class ItemEntityMixin extends Entity {
    public ItemEntityMixin(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }
    public boolean isInvulnerableTo(@Nonnull DamageSource source) {
        if (source.isExplosion()){
            if (invokeGetItem().getItem() instanceof ICanHasBlastResistance item){
                if (item.isBlastResistant()){
                    return true;
                }
            }
        }
        return super.isInvulnerableTo(source);
    }

    @Invoker("getItem")
    public abstract ItemStack invokeGetItem();
}
