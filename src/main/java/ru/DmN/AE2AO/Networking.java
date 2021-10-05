package ru.DmN.AE2AO;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

public class Networking {
    public static SimpleChannel INSTANCE;
    public static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    public static void registerMessages() {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation("ae2ao:test"), () -> "1.0", (x) -> true, (x) -> true);

        INSTANCE.registerMessage(nextID(), Config.class, Config::toBytes, Config::new, Config::handle);
    }
}
