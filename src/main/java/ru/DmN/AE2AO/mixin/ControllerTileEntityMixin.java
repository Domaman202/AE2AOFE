package ru.DmN.AE2AO.mixin;

import appeng.api.networking.GridFlags;
import appeng.tile.grid.AENetworkPowerTileEntity;
import appeng.tile.networking.ControllerTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import ru.DmN.AE2AO.AE2AOMain;

import java.util.EnumSet;

@Mixin(value = ControllerTileEntity.class, remap = false)
public abstract class ControllerTileEntityMixin extends AENetworkPowerTileEntity {
    @Shadow
    private boolean isValid = false;

    public ControllerTileEntityMixin(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
        this.setInternalMaxPower(8000);
        this.setInternalPublicPowerStorage(true);
        this.getProxy().setIdlePowerUsage(3);
        this.getProxy().setFlags(GridFlags.CANNOT_CARRY, GridFlags.DENSE_CAPACITY);
    }


    /**
     * @author PashkovD
     * @reason Setting ControllerCross
     */
    @Overwrite
    public void onNeighborChange(final boolean force) {
        final boolean xx = this.invokeCheckController(this.pos.offset(Direction.EAST))
                && this.invokeCheckController(this.pos.offset(Direction.WEST));
        final boolean yy = this.invokeCheckController(this.pos.offset(Direction.UP))
                && this.invokeCheckController(this.pos.offset(Direction.DOWN));
        final boolean zz = this.invokeCheckController(this.pos.offset(Direction.NORTH))
                && this.invokeCheckController(this.pos.offset(Direction.SOUTH));

        // int meta = world.getBlockMetadata( xCoord, yCoord, zCoord );
        // boolean hasPower = meta > 0;
        // boolean isConflict = meta == 2;

        final boolean oldValid = this.isValid;

        this.isValid = ((xx ? 1 : 0) + (yy ? 1 : 0) + (zz ? 1 : 0) <= 1);
        if (AE2AOMain.config.ControllerCross.get() || !AE2AOMain.config.ControllerLimits.get()) {
            this.isValid = true;
        }

        if (oldValid != this.isValid || force) {
            if (this.isValid) {
                this.getProxy().setValidSides(EnumSet.allOf(Direction.class));
            } else {
                this.getProxy().setValidSides(EnumSet.noneOf(Direction.class));
            }

            this.invokeUpdateMeta();
        }

    }

    @Invoker("updateMeta")
    abstract void invokeUpdateMeta();

    @Invoker("checkController")
    abstract boolean invokeCheckController(final BlockPos pos);
}
