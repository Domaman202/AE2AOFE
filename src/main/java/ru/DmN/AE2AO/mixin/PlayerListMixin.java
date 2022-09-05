package ru.DmN.AE2AO.mixin;

import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraftforge.network.NetworkDirection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.DmN.AE2AO.Main;
import ru.DmN.AE2AO.Networking;

@Mixin(PlayerList.class)
public class PlayerListMixin {
    @Inject(method = "placeNewPlayer", at = @At("HEAD"), remap = false)
    public void addPlayer(Connection p_11262_, ServerPlayer p_11263_, CallbackInfo ci) {
        Networking.INSTANCE.sendTo(Main.lcc, p_11263_.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }
}
