package ru.DmN.AE2AO.mixin;

import appeng.api.networking.IGridHost;
import appeng.api.networking.IGridNode;
import appeng.me.pathfinding.ControllerValidator;
import appeng.tile.networking.ControllerTileEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import ru.DmN.AE2AO.AE2AOMain;

@Mixin(value = ControllerValidator.class, remap = false)
public abstract class ControllerValidatorMixin {
    @Shadow private boolean isValid;
    @Shadow private int found;
    @Shadow private int minX;
    @Shadow private int minY;
    @Shadow private int minZ;
    @Shadow private int maxX;
    @Shadow private int maxY;
    @Shadow private int maxZ;

    @Shadow public abstract int getFound();

    /**
     * @author DomamaN202
     * @reason Adding a size customization
     */
    @Overwrite public boolean visitNode(IGridNode n) {
        IGridHost h = n.getMachine();

        if (isValid && h instanceof ControllerTileEntity) {
            BlockPos pos = ((ControllerTileEntity) h).getPos();

            minX = Math.min(pos.getX(), minX);
            maxX = Math.max(pos.getX(), maxX);
            minY = Math.min(pos.getY(), minY);
            maxY = Math.max(pos.getY(), maxY);
            minZ = Math.min(pos.getZ(), minZ);
            maxZ = Math.max(pos.getZ(), maxZ);

            if (maxX - minX < AE2AOMain.config.ControllerSizeLimits[0].get() && maxY - minY < AE2AOMain.config.ControllerSizeLimits[1].get() && maxZ - minZ < AE2AOMain.config.ControllerSizeLimits[2].get()) {
                this.found++;
                return true;
            }

            isValid = false;
        }

        return false;
    }
}
