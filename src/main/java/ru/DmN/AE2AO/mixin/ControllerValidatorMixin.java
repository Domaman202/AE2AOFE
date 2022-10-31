package ru.DmN.AE2AO.mixin;

import appeng.api.networking.IGridNode;
import appeng.api.networking.pathing.ControllerState;
import appeng.blockentity.networking.ControllerBlockEntity;
import appeng.me.pathfinding.ControllerValidator;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import ru.DmN.AE2AO.AE2AOMain;

import java.util.Collection;

@Mixin(value = ControllerValidator.class, remap = false)
public class ControllerValidatorMixin {
    @Shadow
    private boolean valid;
    @Shadow
    private int found;
    @Shadow
    private int minX;
    @Shadow
    private int minY;
    @Shadow
    private int minZ;
    @Shadow
    private int maxX;
    @Shadow
    private int maxY;
    @Shadow
    private int maxZ;

    @Shadow
    private static boolean hasControllerCross(Collection<ControllerBlockEntity> controllers) {
        return false;
    }

    /**
     * @author DomamaN202
     * @reason Adding a size customization
     */
    @Overwrite
    public boolean visitNode(IGridNode n) {
        if (valid && n.getOwner() instanceof ControllerBlockEntity c) {
            BlockPos pos = c.getBlockPos();

            minX = Math.min(pos.getX(), minX);
            maxX = Math.max(pos.getX(), maxX);
            minY = Math.min(pos.getY(), minY);
            maxY = Math.max(pos.getY(), maxY);
            minZ = Math.min(pos.getZ(), minZ);
            maxZ = Math.max(pos.getZ(), maxZ);

            if (maxX - minX < AE2AOMain.config.ControllerSizeLimits[0].get() &&
                    maxY - minY < AE2AOMain.config.ControllerSizeLimits[1].get() &&
                    maxZ - minZ < AE2AOMain.config.ControllerSizeLimits[2].get()
            ) {
                this.found++;
                return true;
            }

            valid = false;
        }

        return false;
    }

    /**
     * @author DomamaN202
     * @reason Adding setting "ControllerLimits"
     */
    @Overwrite
    public static ControllerState calculateState(Collection<ControllerBlockEntity> controllers) throws Throwable {
        if (controllers.isEmpty()) {
            return ControllerState.NO_CONTROLLER;
        }

        var startingController = controllers.iterator().next();
        var startingNode = startingController.getGridNode();
        if (startingNode == null) {
            return ControllerState.CONTROLLER_CONFLICT;
        }

        var constructor = ControllerValidator.class.getDeclaredConstructor(BlockPos.class);
        constructor.setAccessible(true);
        var cv = constructor.newInstance(startingController.getBlockPos());
        startingNode.beginVisit(cv);

        if (!cv.isValid())
            return ControllerState.CONTROLLER_CONFLICT;

        if (cv.getFound() != controllers.size() && AE2AOMain.config.ControllerLimits.get())
            return ControllerState.CONTROLLER_CONFLICT;

        if (hasControllerCross(controllers))
            return ControllerState.CONTROLLER_CONFLICT;

        return ControllerState.CONTROLLER_ONLINE;
    }
}