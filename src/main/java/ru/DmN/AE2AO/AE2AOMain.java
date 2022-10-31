package ru.DmN.AE2AO;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod("ae2ao")
public class AE2AOMain {
    static final Logger LOGGER = LogManager.getLogger();
    public static Config config = Config.SERVER;

    public AE2AOMain() {
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SERVER_SPEC, "ae2ao.toml");
    }
}