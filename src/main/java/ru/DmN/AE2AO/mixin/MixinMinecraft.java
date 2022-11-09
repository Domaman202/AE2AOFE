package ru.DmN.AE2AO.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    /**
     * @author das
     * @reason dsa
     */
    @Overwrite
    private String createTitle() {
        return "test";
    }
}
