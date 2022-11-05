package ru.DmN.AE2AO.mixin;

import appeng.api.networking.IGridNode;
import appeng.me.GridNode;
import appeng.me.pathfinding.IPathItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import ru.DmN.AE2AO.AE2AOMain;

@Mixin(value = GridNode.class, remap = false)
public abstract class GridNodeMixin implements IGridNode, IPathItem {
    @Shadow
    private int usedChannels;

    @Override
    public int getUsedChannels() {
        return AE2AOMain.config.DisableChannels.get() ? 1 : usedChannels;
    }
}