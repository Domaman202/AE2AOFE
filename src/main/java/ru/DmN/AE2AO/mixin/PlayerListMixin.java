package ru.DmN.AE2AO.mixin;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.management.PlayerList;
import net.minecraftforge.fml.network.NetworkDirection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.DmN.AE2AO.Main;
import ru.DmN.AE2AO.Networking;

@Mixin(PlayerList.class)
public class PlayerListMixin {
    @Inject(method = "addPlayer", at = @At("HEAD"), remap = false)
    public void addPlayer(ServerPlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        Networking.INSTANCE.sendTo(Main.lcc, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
    }
}
