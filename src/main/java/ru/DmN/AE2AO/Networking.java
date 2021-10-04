package ru.DmN.AE2AO;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class Networking {
    public static SimpleChannel INSTANCE;
    public static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    public static void registerMessages() {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation("ae2ao", "test"), () -> "1.0", s -> true, s -> true);

        INSTANCE.registerMessage(nextID(), Config.class, Config::toBytes, Config::new, Config::handle);
    }
}