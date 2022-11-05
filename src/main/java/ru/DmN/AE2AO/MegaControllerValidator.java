package ru.DmN.AE2AO;

import appeng.api.networking.IGridNode;
import appeng.api.networking.IGridVisitor;
import appeng.api.networking.pathing.ControllerState;
import appeng.blockentity.networking.ControllerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MegaControllerValidator implements IGridVisitor {
    private boolean valid = true;
    private int minX;
    private int minY;
    private int minZ;
    private int maxX;
    private int maxY;
    private int maxZ;
    private final HashSet<ControllerBlockEntity> block_set;

    public MegaControllerValidator(BlockPos pos, HashSet<ControllerBlockEntity> block_set) {
        this.minX = pos.getX();
        this.maxX = pos.getX();
        this.minY = pos.getY();
        this.maxY = pos.getY();
        this.minZ = pos.getZ();
        this.maxZ = pos.getZ();
        this.block_set = block_set;
    }

    @Override
    public boolean visitNode(IGridNode n) {
        if (valid && n.getOwner() instanceof ControllerBlockEntity c) {
            BlockPos pos = c.getBlockPos();

            minX = Math.min(pos.getX(), minX);
            maxX = Math.max(pos.getX(), maxX);
            minY = Math.min(pos.getY(), minY);
            maxY = Math.max(pos.getY(), maxY);
            minZ = Math.min(pos.getZ(), minZ);
            maxZ = Math.max(pos.getZ(), maxZ);

            if (maxX - minX < AE2AOMain.config.ControllerSizeLimits.Max_X.get() &&
                    maxY - minY < AE2AOMain.config.ControllerSizeLimits.Max_Y.get() &&
                    maxZ - minZ < AE2AOMain.config.ControllerSizeLimits.Max_Z.get()
            ) {
                block_set.remove(c);
                return true;
            }

            valid = false;
        }

        return false;
    }

    public boolean isValid() {
        return this.valid;
    }

    private static boolean hasControllerCross(Collection<ControllerBlockEntity> controllers) {
        Set<BlockPos> posSet = new HashSet<>(controllers.size());
        for (var controller : controllers) {
            posSet.add(controller.getBlockPos().immutable());
        }

        for (var pos : posSet) {
            boolean northSouth = posSet.contains(pos.relative(Direction.NORTH))
                    && posSet.contains(pos.relative(Direction.SOUTH));
            boolean eastWest = posSet.contains(pos.relative(Direction.EAST))
                    && posSet.contains(pos.relative(Direction.WEST));
            boolean upDown = posSet.contains(pos.relative(Direction.UP))
                    && posSet.contains(pos.relative(Direction.DOWN));

            if ((northSouth ? 1 : 0) + (eastWest ? 1 : 0) + (upDown ? 1 : 0) > 1) {
                return true;
            }
        }

        return false;
    }

    public static ControllerState calculateState(Collection<ControllerBlockEntity> controllers) {
        if (controllers.isEmpty()) {
            return ControllerState.NO_CONTROLLER;
        }
        if (!AE2AOMain.config.ControllerLimits.get()) {
            return ControllerState.CONTROLLER_ONLINE;
        }

        if (!AE2AOMain.config.ControllerCross.get() && hasControllerCross(controllers))
            return ControllerState.CONTROLLER_CONFLICT;

        HashSet<ControllerBlockEntity> sad = new HashSet<>(controllers);

        while (!sad.isEmpty()) {
            ControllerBlockEntity startingController = sad.iterator().next();
            var startingNode = startingController.getGridNode();
            if (startingNode == null) {
                return ControllerState.CONTROLLER_CONFLICT;
            }

            sad.remove(startingController);

            var cv = new MegaControllerValidator(startingController.getBlockPos(), sad);
            startingNode.beginVisit(cv);

            if (!cv.isValid())
                return ControllerState.CONTROLLER_CONFLICT;

            if (!AE2AOMain.config.MultipleControllers.get() && !sad.isEmpty()) {
                return ControllerState.CONTROLLER_CONFLICT;
            }
        }

        return ControllerState.CONTROLLER_ONLINE;
    }
}
