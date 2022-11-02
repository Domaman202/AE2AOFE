package ru.DmN.AE2AO.mixin;

import appeng.api.config.Actionable;
import appeng.api.networking.GridFlags;
import appeng.api.networking.energy.IEnergyGrid;
import appeng.api.networking.events.MENetworkControllerChange;
import appeng.api.networking.events.MENetworkEventSubscribe;
import appeng.api.networking.events.MENetworkPowerStatusChange;
import appeng.api.networking.events.MENetworkPowerStorage;
import appeng.api.networking.pathing.ControllerState;
import appeng.api.util.AECableType;
import appeng.api.util.AEPartLocation;
import appeng.block.networking.ControllerBlock;
import appeng.me.GridAccessException;
import appeng.tile.grid.AENetworkPowerTileEntity;
import appeng.tile.networking.ControllerTileEntity;
import appeng.util.inv.InvOperation;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.EmptyHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import ru.DmN.AE2AO.AE2AOMain;

import java.util.EnumSet;

@Mixin(value = ControllerTileEntity.class, remap = false)
public class ControllerTileEntityMixin extends AENetworkPowerTileEntity {
    @Shadow
    private boolean isValid = false;

    public ControllerTileEntityMixin(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
        this.setInternalMaxPower(8000);
        this.setInternalPublicPowerStorage(true);
        this.getProxy().setIdlePowerUsage(3);
        this.getProxy().setFlags(GridFlags.CANNOT_CARRY, GridFlags.DENSE_CAPACITY);
    }

    @Override
    public void onReady() {
        this.onNeighborChange(true);
        super.onReady();
    }

    public void onNeighborChange(final boolean force) {
        final boolean xx = this.checkController(this.pos.offset(Direction.EAST))
                && this.checkController(this.pos.offset(Direction.WEST));
        final boolean yy = this.checkController(this.pos.offset(Direction.UP))
                && this.checkController(this.pos.offset(Direction.DOWN));
        final boolean zz = this.checkController(this.pos.offset(Direction.NORTH))
                && this.checkController(this.pos.offset(Direction.SOUTH));

        // int meta = world.getBlockMetadata( xCoord, yCoord, zCoord );
        // boolean hasPower = meta > 0;
        // boolean isConflict = meta == 2;

        final boolean oldValid = this.isValid;

        this.isValid = ((xx ? 1 : 0) + (yy ? 1 : 0) + (zz ? 1 : 0) <= 1);
        if (AE2AOMain.config.ControllerCross.get()){
            this.isValid=true;
        }

        if (oldValid != this.isValid || force) {
            if (this.isValid) {
                this.getProxy().setValidSides(EnumSet.allOf(Direction.class));
            } else {
                this.getProxy().setValidSides(EnumSet.noneOf(Direction.class));
            }

            this.updateMeta();
        }

    }

    private void updateMeta() {
        if (!this.getProxy().isReady()) {
            return;
        }

        ControllerBlock.ControllerBlockState metaState = ControllerBlock.ControllerBlockState.offline;

        try {
            if (this.getProxy().getEnergy().isNetworkPowered()) {
                metaState = ControllerBlock.ControllerBlockState.online;

                if (this.getProxy().getPath().getControllerState() == ControllerState.CONTROLLER_CONFLICT) {
                    metaState = ControllerBlock.ControllerBlockState.conflicted;
                }
            }
        } catch (final GridAccessException e) {
            metaState = ControllerBlock.ControllerBlockState.offline;
        }

        if (this.checkController(this.pos)
                && this.world.getBlockState(this.pos).get(ControllerBlock.CONTROLLER_STATE) != metaState) {
            this.world.setBlockState(this.pos,
                    this.world.getBlockState(this.pos).with(ControllerBlock.CONTROLLER_STATE, metaState));
        }

    }

    @Override
    protected double getFunnelPowerDemand(final double maxReceived) {
        try {
            final IEnergyGrid grid = this.getProxy().getEnergy();

            return grid.getEnergyDemand(maxReceived);
        } catch (final GridAccessException e) {
            // no grid? use local...
            return super.getFunnelPowerDemand(maxReceived);
        }
    }

    @Override
    protected double funnelPowerIntoStorage(final double power, final Actionable mode) {
        try {
            final IEnergyGrid grid = this.getProxy().getEnergy();

            return grid.injectPower(power, mode);
        } catch (final GridAccessException e) {
            // no grid? use local...
            return super.funnelPowerIntoStorage(power, mode);
        }
    }

    @Override
    protected void PowerEvent(final MENetworkPowerStorage.PowerEventType x) {
        try {
            this.getProxy().getGrid().postEvent(new MENetworkPowerStorage(this, x));
        } catch (final GridAccessException e) {
            // not ready!
        }
    }

    @MENetworkEventSubscribe
    public void onControllerChange(final MENetworkControllerChange status) {
        this.updateMeta();
    }

    @MENetworkEventSubscribe
    public void onPowerChange(final MENetworkPowerStatusChange status) {
        this.updateMeta();
    }

    @Override
    public IItemHandler getInternalInventory() {
        return EmptyHandler.INSTANCE;
    }

    @Override
    public void onChangeInventory(final IItemHandler inv, final int slot, final InvOperation mc,
                                  final ItemStack removed, final ItemStack added) {
    }

    /**
     * Check for a controller at this coordinates as well as is it loaded.
     *
     * @return true if there is a loaded controller
     */
    private boolean checkController(final BlockPos pos) {
        assert this.world != null;
        if (this.world.getChunkProvider().canTick(pos)) {
            return this.world.getTileEntity(pos) instanceof appeng.tile.networking.ControllerTileEntity;
        }

        return false;
    }
}
