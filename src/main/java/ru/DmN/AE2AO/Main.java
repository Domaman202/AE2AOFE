package ru.DmN.AE2AO;

import com.moandjiezana.toml.Toml;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;

@Mod("ae2ao")
public class Main {
    // Logger
    private static final Logger LOGGER = LogManager.getLogger();
    // Config
    public static Config lcc = null;
    public static Config lc = null;

    public Main() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);

        // Config init
        try {
            File conf = FMLPaths.GAMEDIR.get().resolve("config" + File.separator + "ae2ao.toml").toFile();

            if (conf.createNewFile()) {
                FileWriter writer = new FileWriter(conf);
                writer.write("DisableChannels = false\nControllerLimits = false\nMax_X = 7\nMax_Y = 7\nMax_Z = 7\nSCFD = false");
                writer.flush();
                writer.close();

                lcc = new Config();
                lc = new Config();
            } else {
                Toml res = new Toml().read(conf);
                lcc = res.to(Config.class);
                lc = lcc.clone();
            }

            System.out.println();
        } catch (Exception e) {
            LOGGER.throwing(e);
        }
    }

    private void setup(final FMLCommonSetupEvent event)
    {

    }
}
