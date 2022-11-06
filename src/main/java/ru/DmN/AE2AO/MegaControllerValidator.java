package ru.DmN.AE2AO;

import appeng.api.networking.IGridHost;
import appeng.api.networking.IGridNode;
import appeng.api.networking.IGridVisitor;
import appeng.api.networking.pathing.ControllerState;
import appeng.api.util.AEPartLocation;
import appeng.tile.networking.ControllerTileEntity;
import net.minecraft.util.math.BlockPos;

import java.util.Collection;
import java.util.HashSet;

public class MegaControllerValidator implements IGridVisitor {
    private boolean valid = true;
    private int minX;
    private int minY;
    private int minZ;
    private int maxX;
    private int maxY;
    private int maxZ;
    private final HashSet<ControllerTileEntity> block_set;

    public MegaControllerValidator(BlockPos pos, HashSet<ControllerTileEntity> block_set) {
        this.minX = pos.getX();
        this.maxX = pos.getX();
        this.minY = pos.getY();
        this.maxY = pos.getY();
        this.minZ = pos.getZ();
        this.maxZ = pos.getZ();
        this.block_set = block_set;
    }

    public boolean visitNode(IGridNode n) {
        final IGridHost host = n.getMachine();
        if (this.isValid() && host instanceof ControllerTileEntity) {
            final ControllerTileEntity c = (ControllerTileEntity) host;
            BlockPos pos = c.getPos();

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

    public static ControllerState calculateState(Collection<ControllerTileEntity> controllers) {
        if (controllers.isEmpty()) {
            return ControllerState.NO_CONTROLLER;
        }
        if (!AE2AOMain.config.ControllerLimits.get()) {
            return ControllerState.CONTROLLER_ONLINE;
        }

        HashSet<ControllerTileEntity> sad = new HashSet<>(controllers);

        while (!sad.isEmpty()) {
            ControllerTileEntity startingController = sad.iterator().next();
            IGridNode startingNode = startingController.getGridNode(AEPartLocation.INTERNAL);
            if (startingNode == null) {
                return ControllerState.CONTROLLER_CONFLICT;
            }

            sad.remove(startingController);

            MegaControllerValidator cv = new MegaControllerValidator(startingController.getPos(), sad);
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
