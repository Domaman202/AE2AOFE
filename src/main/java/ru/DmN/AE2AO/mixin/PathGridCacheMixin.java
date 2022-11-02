package ru.DmN.AE2AO.mixin;

import appeng.api.networking.IGrid;
import appeng.api.networking.IGridNode;
import appeng.api.networking.events.MENetworkControllerChange;
import appeng.api.networking.pathing.ControllerState;
import appeng.api.util.AEPartLocation;
import appeng.api.util.DimensionalCoord;
import appeng.me.cache.PathGridCache;
import appeng.me.pathfinding.ControllerValidator;
import appeng.tile.networking.ControllerTileEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import ru.DmN.AE2AO.AE2AOMain;
import ru.DmN.AE2AO.MegaControllerValidator;

import java.util.Set;

@Mixin(value = PathGridCache.class, remap = false)
public class PathGridCacheMixin {
    @Final @Shadow  private Set<ControllerTileEntity>  controllers;
    @Final @Shadow  private IGrid                       myGrid;
    @Shadow         private boolean                     recalculateControllerNextTick;
    @Shadow         private ControllerState             controllerState;

    /**
     * @author DomamaN202
     * @reason Adding controller error system control
     */
    @Overwrite private void recalcController() {
        this.recalculateControllerNextTick = false;
        final ControllerState old = this.controllerState;

        this.controllerState = MegaControllerValidator.calculateState(this.controllers);

        if (old != this.controllerState) {
            this.myGrid.postEvent(new MENetworkControllerChange());
        }
    }
}
