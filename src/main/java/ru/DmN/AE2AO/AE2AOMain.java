package ru.DmN.AE2AO;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(AE2AOMain.ModID)
public class AE2AOMain {
    public static final String ModID = "ae2ao";
    public static final Logger LOGGER = LogManager.getLogger(ModID);
    public static Config config = Config.Instance;

    public AE2AOMain() {
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.Instance_Spec, ModID + ".toml");
    }
}