package ru.DmN.AE2AO.mixin;


import appeng.api.storage.IStorageChannel;
import appeng.api.storage.IStorageHelper;
import appeng.api.storage.channels.IFluidStorageChannel;
import appeng.api.storage.channels.IItemStorageChannel;
import appeng.core.api.ApiStorage;
import com.google.common.collect.ClassToInstanceMap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.DmN.AE2AO.AE2AOFluidStorageChannel;
import ru.DmN.AE2AO.AE2AOItemStorageChannel;

@Mixin(value = ApiStorage.class, remap = false)
public abstract class ApiStorageMixin implements IStorageHelper {
    @Shadow
    @Final
    private ClassToInstanceMap<IStorageChannel<?>> channels;

    @Inject(at = @At("RETURN"), method = "<init>")
    public void injectInit(CallbackInfo ci) {
        this.channels.putInstance(IItemStorageChannel.class, new AE2AOItemStorageChannel());
        this.channels.putInstance(IFluidStorageChannel.class, new AE2AOFluidStorageChannel());
    }

}
