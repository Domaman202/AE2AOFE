package ru.DmN.AE2AO.mixin;

import appeng.me.GridNode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import ru.DmN.AE2AO.AE2AOMain;

@Mixin(value = GridNode.class, remap = false)
public class GridNodeMixin {
    @Shadow
    private int usedChannels;

    /**
     * @author DomamaN202
     * @reason Add system turn off channels
     */
    @Overwrite
    public int getUsedChannels() {
        return AE2AOMain.config.DisableChannels ? 1 : usedChannels;
    }
}