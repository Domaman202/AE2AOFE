package ru.DmN.AE2AO.mixin;

import appeng.api.stacks.AEKeyType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import ru.DmN.AE2AO.AE2AOFluidKeys;
import ru.DmN.AE2AO.AE2AOItemKeys;

@Mixin(value = AEKeyType.class, remap = false)
public abstract class AEKeyTypeMixin {
    /**
     * @author PashkovD
     * @reason Redirection
     */
    @Overwrite
    public static AEKeyType items() {
        return AE2AOItemKeys.INSTANCE;
    }

    /**
     * @author PashkovD
     * @reason Redirection
     */
    @Overwrite
    public static AEKeyType fluids() {
        return AE2AOFluidKeys.INSTANCE;
    }
}
