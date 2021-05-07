package ru.DmN.AE2AO;

import com.moandjiezana.toml.Toml;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

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
                FileOutputStream stream = new FileOutputStream(conf);
                stream.write("DisableChannels = false\nControllerLimits = false\nMax_X = 7\nMax_Y = 7\nMax_Z = 7\nSCFD = false".getBytes(StandardCharsets.UTF_8));
                stream.flush();
                stream.close();

                lcc = new Config();
                lc = new Config();
            } else {
                lcc = new Toml().to(Config.class);
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
