package ru.DmN.AE2AO.mixin;

import appeng.api.networking.pathing.ControllerState;
import appeng.blockentity.networking.ControllerBlockEntity;
import appeng.me.pathfinding.ControllerValidator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import ru.DmN.AE2AO.MegaControllerValidator;

import java.util.Collection;

@Mixin(value = ControllerValidator.class, remap = false)
public class ControllerValidatorMixin {
    /**
     * @author PashkovD
     * @reason Redirect to MegaControllerValidator
     */
    @Overwrite
    public static ControllerState calculateState(Collection<ControllerBlockEntity> controllers) {
        return MegaControllerValidator.calculateState(controllers);
    }
}