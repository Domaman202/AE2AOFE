package ru.DmN.AE2AO.mixin;

import appeng.api.networking.IGridServiceProvider;
import appeng.api.networking.events.GridControllerChange;
import appeng.api.networking.pathing.ControllerState;
import appeng.api.networking.pathing.IPathingService;
import appeng.blockentity.networking.ControllerBlockEntity;
import appeng.me.Grid;
import appeng.me.service.PathingService;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import ru.DmN.AE2AO.MegaControllerValidator;

import java.util.Set;

@Mixin(value = PathingService.class, remap = false)
public abstract class PathingServiceMixin implements IPathingService, IGridServiceProvider {
    @Shadow
    @Final
    private Set<ControllerBlockEntity> controllers;
    @Shadow
    @Final
    private Grid grid;
    @Shadow
    private boolean recalculateControllerNextTick;
    @Shadow
    private ControllerState controllerState;

    /**
     * @author PashkovD
     * @reason Redirection to MegaControllerValidator
     */
    @Overwrite
    private void updateControllerState() {
        this.recalculateControllerNextTick = false;
        final ControllerState old = this.controllerState;

        this.controllerState = MegaControllerValidator.calculateState(controllers);

        if (old != this.controllerState) {
            this.grid.postEvent(new GridControllerChange());
        }
    }
}
