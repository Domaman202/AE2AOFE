package ru.DmN.AE2AO;

import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(AE2AOMain.ModID)
public class AE2AOMain {
    public static final String ModID = "ae2ao";
    public static final Logger LOGGER = LogManager.getLogger(ModID);
    public static Config config = Config.Instance;

    public AE2AOMain() {
//        ModLoadingContext.get().getActiveContainer().getEventBus().register(this);
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, Config.Instance_Spec, ModID + ".toml");
    }
}